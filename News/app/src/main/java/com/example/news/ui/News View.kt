package com.example.news.ui

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.news.ui.models.Article
import java.net.URLEncoder
import java.util.Date

@Composable
fun NewsViewStart(modifier: Modifier = Modifier, navController: NavController = rememberNavController()) {
    val viewModel : NewsList = viewModel()
    val uiMode by viewModel.uiMode.collectAsState()

    NewsView(modifier, navController, uiMode, viewModel)

    LaunchedEffect(Unit) {
        viewModel.articleFetcher("General")
    }
}

@Composable
fun NewsView(modifier: Modifier = Modifier, navController: NavController = rememberNavController(), uiMode: ArticleStatus, viewModel: NewsList?) {
    val categories = arrayListOf("General", "Entertainment", "Technology", "Science", "Health", "Business", "Sports")
    var selectedcategory by remember { mutableStateOf("General") }

    if (uiMode.status == "Ready") {
        Column(modifier = modifier.fillMaxSize()) {
            LazyRow(modifier = Modifier.fillMaxWidth().padding(20.dp), horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                itemsIndexed(items = categories) { _, category ->
                    Text(text = category, fontSize = 24.sp, color = if (category == selectedcategory) { Color.Blue } else { Color.Black },
                        modifier = Modifier.clickable {
                            selectedcategory = category
                            viewModel!!.articleFetcher(selectedcategory)
                        })
                }
            }

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                itemsIndexed(items = uiMode.articles) { _, article ->
                    NewsEntry(modifier = Modifier.clickable {
                        Log.d("News", article.url ?: "none")
                        navController.navigate(
                            "articleReader/{articleUrl}".replace(
                                "{articleUrl}",
                                URLEncoder.encode(article.url, "UTF-8") ?: ""
                            )
                        )
                    }, article = article)
                }
            }
        }
    }

    else {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(uiMode.status!!)
        }
    }


}

@Preview(showBackground = true)
@Composable
fun NewsViewTest(){
    val articleexamples = arrayListOf(
        Article("Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
          "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin lobortis augue in erat scelerisque, vitae fringilla nisi tempus. Sed finibus tellus porttitor dignissim eleifend. Etiam sed neque libero. Integer auctor turpis est. Nunc ac auctor velit. Nunc et mi sollicitudin, iaculis nunc et, congue neque. Suspendisse potenti. Vestibulum finibus justo sed eleifend commodo. Phasellus vestibulum ligula nisi, convallis rhoncus quam placerat id. Donec eu lobortis lacus, quis porta tortor. Suspendisse quis dolor sapien. Maecenas finibus purus at orci aliquam eleifend. Nam venenatis sapien ac enim efficitur pretium. Praesent sagittis risus vitae feugiat blandit. Etiam non neque arcu. Cras a mauris eu erat sodales iaculis non a lorem.",
                imageurl = "https://media.istockphoto.com/id/1166633394/pt/foto/victorian-british-army-gymnastic-team-aldershot-19th-century.jpg?s=1024x1024&w=is&k=20&c=fIfqysdzOinu8hNJG6ZXOhl8ghQHA7ySl8BZZYWrxyQ=",
            date = Date()),

        Article("Lorem Ipsum is simply dummy text of the printing", "description"))

    NewsView(uiMode = ArticleStatus(
             articles = articleexamples,
             status = "ready"),
             viewModel = null)
}