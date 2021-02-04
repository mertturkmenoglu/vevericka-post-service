package io.github.vevericka.veverickapostservice.repositories

import io.github.vevericka.veverickapostservice.model.Bookmark
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface BookmarkRepository : MongoRepository<Bookmark, String>