package io.github.vevericka.veverickapostservice.repositories

import io.github.vevericka.veverickapostservice.model.Post
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface PostRepository : MongoRepository<Post, String>