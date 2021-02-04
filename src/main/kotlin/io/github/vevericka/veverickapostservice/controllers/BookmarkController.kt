package io.github.vevericka.veverickapostservice.controllers

import io.github.vevericka.veverickapostservice.model.Bookmark
import io.github.vevericka.veverickapostservice.repositories.BookmarkRepository
import io.github.vevericka.veverickapostservice.responses.ApiResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin
@RequestMapping("/bookmark")
class BookmarkController {
    @Autowired
    private lateinit var bookmarkRepository: BookmarkRepository

    @GetMapping("/{id}")
    fun getBookmarkById(@PathVariable("id") id: String): ApiResponse.Response<Bookmark?> {
        return ApiResponse.Response(bookmarkRepository.findByIdOrNull(id))
    }

    @GetMapping("/user/{username}")
    fun getUserBookmarks(@PathVariable("username") username: String): ApiResponse.Response<List<Bookmark>> {
        val bookmarks = bookmarkRepository.getUserBookmarksDesc(username, Sort.by(Sort.Direction.DESC, "date"))
        return ApiResponse.Response(bookmarks)
    }

    @PostMapping("/")
    fun createBookmark(@RequestBody bookmark: Bookmark): ApiResponse.Response<ApiResponse> {
        val list = bookmarkRepository.findBookmarksByPostIdAndUsername(bookmark.postId, bookmark.username)

        if (list.isNotEmpty()) {
            return ApiResponse.Response(ApiResponse.ErrorResponse(message = "Bookmark exists", statusCode = 400))
        }

        bookmarkRepository.save(bookmark)
        return ApiResponse.Response(ApiResponse.MessageResponse(message = "Bookmark saved"))
    }

    @DeleteMapping("/{id}")
    fun deleteBookmark(@PathVariable("id") id: String) {
        bookmarkRepository.deleteById(id)
    }
}