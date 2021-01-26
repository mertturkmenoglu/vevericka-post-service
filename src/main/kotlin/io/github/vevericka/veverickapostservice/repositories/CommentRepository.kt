package io.github.vevericka.veverickapostservice.repositories

import io.github.vevericka.veverickapostservice.model.Comment
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface CommentRepository : MongoRepository<Comment, String>