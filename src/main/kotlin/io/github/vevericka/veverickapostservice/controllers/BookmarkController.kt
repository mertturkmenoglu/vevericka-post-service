package io.github.vevericka.veverickapostservice.controllers

import io.github.vevericka.veverickapostservice.model.Bookmark
import io.github.vevericka.veverickapostservice.repositories.BookmarkRepository
import io.github.vevericka.veverickapostservice.responses.ApiResponse
import org.springframework.beans.factory.annotation.Autowired
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
        val bookmarks = bookmarkRepository.findAll()
            .filter { it.username == username }
            .sortedByDescending { it.date }

        return ApiResponse.Response(bookmarks)
    }

    @PostMapping("/")
    fun createBookmark(@RequestBody bookmark: Bookmark) {
        bookmarkRepository.save(bookmark)
    }

    @DeleteMapping("/{id}")
    fun deleteBookmark(@PathVariable("id") id: String) {
        bookmarkRepository.deleteById(id)
    }
}