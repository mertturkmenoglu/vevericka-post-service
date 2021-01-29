package io.github.vevericka.veverickapostservice.model.userinfoservice

import java.io.Serializable

data class UserInfoServiceUserModel(val username: String, val following: List<String>) : Serializable
