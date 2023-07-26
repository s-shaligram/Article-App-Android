package com.example.project1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ArticleAdapter : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    private val articles: MutableList<Article> = mutableListOf()

    fun setArticles(articles: List<Article>) {
        this.articles.clear()
        this.articles.addAll(articles)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = articles[position]
        holder.bind(article)
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val textViewSnippet: TextView = itemView.findViewById(R.id.textViewSnippet)
        private val textViewWebUrl: TextView = itemView.findViewById(R.id.textViewWebUrl)
        private val textViewSource: TextView = itemView.findViewById(R.id.textViewSource)

        fun bind(article: Article) {
            textViewSnippet.text = article.snippet
            textViewWebUrl.text = article.web_url
            textViewSource.text = article.source
        }
    }
}
