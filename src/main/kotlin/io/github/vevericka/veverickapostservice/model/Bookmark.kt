package io.github.vevericka.veverickapostservice.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document(collection = "Bookmarks")
data class Bookmark(
    @Id
    var id: String?,
    var postId: String,
    var username: String,
    var date: Date = Date()
) {
    // Keep it for serialization
    @Suppress("unused")
    constructor() : this("", "", "")
}