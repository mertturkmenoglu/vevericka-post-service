package io.github.vevericka.veverickapostservice.controllers

import io.github.vevericka.veverickapostservice.model.Comment
import io.github.vevericka.veverickapostservice.responses.ApiResponse.Response
import io.github.vevericka.veverickapostservice.model.Post
import io.github.vevericka.veverickapostservice.model.userinfoservice.UserInfoServiceUserResponse
import io.github.vevericka.veverickapostservice.repositories.CommentRepository
import io.github.vevericka.veverickapostservice.repositories.PostRepository
import io.github.vevericka.veverickapostservice.responses.ApiResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.RestTemplate

@RestController
@CrossOrigin
@RequestMapping("/post")
class PostController {
    @Autowired
    private lateinit var postRepository: PostRepository

    @Autowired
    private lateinit var commentRepository: CommentRepository

    private val restTemplate = RestTemplate()

    @GetMapping("/{id}")
    fun getPostById(@PathVariable("id") id: String): Response<Post?> {
        return Response(postRepository.findByIdOrNull(id))
    }

    @GetMapping("/user/{username}")
    fun getUserPosts(@PathVariable("username") username: String): Response<List<Post>> {
        val posts = postRepository.findAll()
            .filter { it.username == username }
            .sortedByDescending { it.date }

        return Response(posts)
    }

    @GetMapping("/feed/{username}")
    fun getUserFeed(@PathVariable("username") username: String): Response<List<Post>> {
        val url = "https://user-info-service.herokuapp.com/user/username/$username"
        val userResponse = restTemplate.getForObject(url, UserInfoServiceUserResponse::class.java)
        val following = userResponse?.user?.first()?.following ?: emptyList()
        val posts = postRepository.findAll()
            .filter { it.username == username || it.username in following }
            .sortedByDescending { it.date }

        return Response(posts)
    }

    @PostMapping("/")
    fun createPost(@RequestBody post: Post) {
        val usernameRegex = "@[A-Za-z0-9_]+".toRegex()
        val hashtagRegex = "#[A-Za-z0-9_]+".toRegex()

        post.mentions = usernameRegex.findAll(post.content).map { it.value }.toList()
        post.hashtags = hashtagRegex.findAll(post.content).map { it.value }.toList()

        postRepository.save(post)
    }

    @PutMapping("/{id}")
    fun updatePost(@RequestBody post: Post, @PathVariable("id") id: String): Response<Post> {
        return Response(postRepository.findById(id)
            .map {
                post.id = id
                postRepository.save(post)
            }
            .orElseGet {
                post.id = id
                postRepository.save(post)
            }
        )
    }

    @DeleteMapping("/{id}")
    fun deletePost(@PathVariable("id") id: String) {
        postRepository.findById(id).ifPresent {
            for (comment in it.comments) {
                commentRepository.deleteById(comment)
            }
        }

        postRepository.deleteById(id)
    }

    @DeleteMapping("/comment/{id}")
    fun deleteComment(@PathVariable("id") id: String) {
        commentRepository.findById(id).ifPresent { comment ->
            commentRepository.deleteById(id)
            val postId = comment.postId
            postRepository.findById(postId).map { post ->
                post.comments = post.comments.filter { it != id }
                postRepository.save(post)
            }
        }
    }

    @GetMapping("/comment/{id}")
    fun getCommentById(@PathVariable("id") id: String): Response<Comment?> {
        return Response(commentRepository.findByIdOrNull(id))
    }

    @PostMapping("/comment/")
    fun createComment(@RequestBody comment: Comment): ApiResponse {
        val result = postRepository.findById(comment.postId).map { post ->
            val savedCommentId = commentRepository.save(comment).id ?: throw IllegalStateException()
            post.comments += savedCommentId
            postRepository.save(post)
        }

        return if (result.isPresent) {
            Response(result.get())
        } else {
            Response(ApiResponse.MessageResponse("Invalid postId"))
        }
    }
}