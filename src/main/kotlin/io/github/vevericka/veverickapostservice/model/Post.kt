package io.github.vevericka.veverickapostservice.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document(collection = "Posts")
data class Post(
        @Id var id: String?,
        var content: String,
        var comments: List<String>,
        var username: String,
        var date: Date,
        var countdown: Int,
        var hashtags: List<String>,
        var mentions: List<String>
) {
    // Keep it for serialization
    @Suppress("unused")
    constructor() : this("", "", emptyList(), "", Date(), 0, emptyList(), emptyList())
}
