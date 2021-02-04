package io.github.vevericka.veverickapostservice.responses

sealed class ApiResponse {
    class Response<T>(val data: T): ApiResponse()
    class MessageResponse(val message: String): ApiResponse()
    class ErrorResponse(val message: String, val statusCode: Int): ApiResponse()
}