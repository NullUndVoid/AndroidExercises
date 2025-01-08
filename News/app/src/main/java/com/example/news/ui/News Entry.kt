package com.example.news.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.news.R
import com.example.news.ui.models.Article
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

//Non essential, could be used instead as such: date = SimpleDateFormat("dd-MM-yyyy'T'HH:mm:ss'Z'", Locale.getDefault()).parse(json.getString)
fun Date.dateParser() : String {
    val pattern = "dd-MM-yyyy" //Invert date because I said so
    return SimpleDateFormat(pattern, Locale.getDefault()) //Creates Format
        .format(this)  //Parses date object to string
}

@Composable
fun NewsEntry(modifier: Modifier = Modifier, article: Article){
    Row(modifier.padding(8.dp)) {
        article.imageurl?.let {
            AsyncImage(model = it, contentDescription = "", modifier = Modifier.height(120.dp).width(120.dp).clip(RoundedCornerShape(8.dp, 8.dp, 8.dp, 8.dp)), contentScale = ContentScale.Crop)
        } ?: run {
            Image(modifier = Modifier.height(120.dp).width(120.dp).clip(RoundedCornerShape(8.dp, 8.dp, 8.dp, 8.dp)), painter = painterResource(id = R.mipmap.img_place_holder), contentDescription = "", contentScale = ContentScale.Crop)
        }

        Column(modifier = Modifier.fillMaxWidth().padding(12.dp, 0.dp, 0.dp, 0.dp)) {
            Text(text = article.title?: "", style = MaterialTheme.typography.titleLarge, maxLines = 2, overflow = TextOverflow.Ellipsis)
            Text(text = article.description?: "", maxLines = 4, overflow = TextOverflow.Ellipsis)
            Text(text = article.date?.dateParser()?: "")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NewsEntryTest(){
    NewsEntry(article = Article(
        "Title",
        "description",
        null,
        "url",
        Date()))
}