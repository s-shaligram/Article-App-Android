package com.example.project1

data class NYTimesResponse(
    val status: String,
    val copyright: String,
    val response: ResponseData
)

data class ResponseData(
    val docs: List<Article>
)
