package io.github.vevericka.veverickapostservice.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document(collection = "Comments")
data class Comment(
        @Id var id: String?,
        var postId: String,
        var content: String,
        var username: String,
        var date: Date
) {
    @Suppress("unused")
    constructor() : this("", "", "", "", Date())
}