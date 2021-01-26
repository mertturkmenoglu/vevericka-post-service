package io.github.vevericka.veverickapostservice.controllers

import io.github.vevericka.veverickapostservice.responses.ApiResponse.MessageResponse
import io.github.vevericka.veverickapostservice.responses.ApiResponse.Response
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin
@RequestMapping("/")
class ApiController {
    @GetMapping("")
    fun homeResponse(): Response<MessageResponse> {
        return Response(MessageResponse(message = "Vevericka Post Service Home"))
    }

    @GetMapping("api")
    fun apiResponse(): Response<MessageResponse> {
        return Response(MessageResponse(message = "Vevericka Post Service API"))
    }
}