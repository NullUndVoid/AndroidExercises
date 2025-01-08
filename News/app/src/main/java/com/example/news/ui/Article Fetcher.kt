package com.example.news.ui

import androidx.lifecycle.ViewModel
import com.example.news.ui.models.Article
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

data class ArticleStatus(
    val articles: ArrayList<Article> = arrayListOf(),
    val status: String? = ""
)

class NewsList : ViewModel() { //ViewModel saves data when not visible, so no need to redraw
    private val _uiMode = MutableStateFlow(ArticleStatus()) //Broadcaster for all getters
    val uiMode: StateFlow<ArticleStatus> = _uiMode.asStateFlow() //Read only buffer

    fun articleFetcher(category: String) {
        _uiMode.value = ArticleStatus(status = "Loading Articles...")

        val client = OkHttpClient()
        val request = Request.Builder().url("https://newsapi.org/v2/top-headlines?country=us&category=${category}&apiKey=1765f87e4ebc40229e80fd0f75b6416c").build()

        client.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace() //Print to stderr?
                _uiMode.value = ArticleStatus(status = "Error: ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) throw IOException("Response Unsuccessful: $response")
                    val listofarticles = arrayListOf<Article>()
                    val jsondata = JSONObject(response.body!!.string()) //!! means we are sure it's not null
                    val status = jsondata.getString("status")
                    if(status == "ok") {
                        val jsonarticles =jsondata.getJSONArray("articles")
                        for(index in 0 until jsonarticles.length()) {
                            val singularjsonarticle = jsonarticles.getJSONObject(index)
                            val singlearticle = Article.jsonParser(singularjsonarticle)
                            listofarticles.add(singlearticle)
                        }
                    }

                    if(listofarticles.isEmpty())
                        _uiMode.value = ArticleStatus(articles = listofarticles, status = "No Articles Found!")

                    else
                        _uiMode.value = ArticleStatus(articles = listofarticles, status = "Ready")
                }
            }
        })
    }
}