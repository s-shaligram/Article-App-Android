package com.example.project1

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var articleAdapter: ArticleAdapter
    private lateinit var nyTimesViewModel: NYTimesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerViewArticles: RecyclerView = findViewById(R.id.recyclerViewArticles)
        recyclerViewArticles.layoutManager = LinearLayoutManager(this)
        articleAdapter = ArticleAdapter()
        recyclerViewArticles.adapter = articleAdapter

        nyTimesViewModel = ViewModelProvider(this)[NYTimesViewModel::class.java]

        val searchBtn = findViewById<Button>(R.id.buttonSearch)

        val searchTxt = findViewById<TextView>(R.id.editTextSearch)

        loadTopArticles()

        var searchResult = findViewById<TextView>(R.id.searchRresults)
        searchResult.text = "Search Results: \"Top Articles\""


        searchBtn.setOnClickListener {
            val query = searchTxt.text.toString()
            observeArticle(query)
        }

    }

    private fun observeArticle(query: String){
        nyTimesViewModel.searchArticles(query).observe(this, Observer { articles ->
            articleAdapter.setArticles(articles)
        })

//        val searchTxt = findViewById<TextView>(R.id.editTextSearch)
//        searchTxt.text = ""

        var searchResult = findViewById<TextView>(R.id.searchRresults)
        searchResult.text = "Search Results: \"$query\""
    }

    private fun loadTopArticles(){
        nyTimesViewModel.searchArticles("").observe(this, Observer { articles ->
            articleAdapter.setArticles(articles)
        })
    }

    fun toProjectDetails(view: View){

        val intent = Intent(this,ProjectDetails::class.java)

        this.startActivity(intent)
    }

    fun clearButton(view: View){

        loadTopArticles()

        val searchTxt = findViewById<TextView>(R.id.editTextSearch)
        searchTxt.text = ""

        var searchResult = findViewById<TextView>(R.id.searchRresults)
        searchResult.text = "Search Results: \"Top Articles\""
    }
}

