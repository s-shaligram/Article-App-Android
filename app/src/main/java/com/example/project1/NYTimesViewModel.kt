package com.example.project1

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class NYTimesViewModel : ViewModel() {
    private val apiClient: NYTimesApiClient = NYTimesApiClient()

    fun searchArticles(query: String): LiveData<List<Article>> {
        return apiClient.searchArticles(query)
    }
}
