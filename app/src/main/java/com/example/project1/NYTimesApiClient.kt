package com.example.project1

import android.util.Log
import android.widget.Button
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException
import com.example.project1.Article


class NYTimesApiClient {

    private companion object {

//        https://api.nytimes.com/svc/search/v2/articlesearch.json?q=election&api-key=yourkey
        const val API_BASE_URL = "https://api.nytimes.com/svc/search/v2/articlesearch.json"
        const val API_KEY = ""
    }
    private val client: OkHttpClient = OkHttpClient()
    private val gson: Gson = GsonBuilder().create()

    fun searchArticles(query: String): LiveData<List<Article>> {

        val articlesLiveData = MutableLiveData<List<Article>>()

        val url = "$API_BASE_URL?q=$query&api-key=$API_KEY"
        val request = Request.Builder()
            .url(url)
            .build()

        val call = client.newCall(request)
        call.enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val jsonResponse = parseResponse(response)
                Log.d("NYTimesApiClient", "JSON Response: $jsonResponse")
                jsonResponse?.let {
                    val nyTimesResponse = gson.fromJson(it, NYTimesResponse::class.java)
                    nyTimesResponse?.response?.docs?.let { articles ->
                        val articleList = articles.map { doc ->
                            Article(doc.snippet, doc. web_url,doc.source)
                        }
                        Log.d("NYTimesApiClient", "JSON Response: $articleList") // Add this line
                        articlesLiveData.postValue(articleList)
                    }
                }
            }


            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                // Handle the failure case here
            }
        })

        return articlesLiveData
    }

    private fun parseResponse(response: Response): String? {
        return try {
            response.body?.string()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

}
