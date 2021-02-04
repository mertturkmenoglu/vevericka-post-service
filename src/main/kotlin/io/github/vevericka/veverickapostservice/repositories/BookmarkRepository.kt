package io.github.vevericka.veverickapostservice.repositories

import io.github.vevericka.veverickapostservice.model.Bookmark
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface BookmarkRepository : MongoRepository<Bookmark, String> {

    @Query("{ 'postId' : ?0, 'username': ?1 }")
    fun findBookmarksByPostIdAndUsername(postId: String, username: String): List<Bookmark>

    @Query("{ 'username': ?0 }")
    fun getUserBookmarksDesc(username: String, sort: Sort): List<Bookmark>
}