package com.example.news.ui.models

import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Article(var title: String? = null,
              var description: String? = null,
              var imageurl: String? = null,
              var url: String? = null,
              var date: Date? = null) {

    companion object {
        //Non essential, could be used instead as such: date = SimpleDateFormat("dd-MM-yyyy'T'HH:mm:ss'Z'", Locale.getDefault()).parse(json.getString)
        private fun String.parseDate(): Date? {
            return SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault()) //Creates Format
                .parse(this) //Parses string to date object
        }

        fun jsonParser(json: JSONObject): Article {
            return Article(title = json.getString("title"),
                           description = json.getString("description"),
                           imageurl = json.getString("urlToImage"),
                           url = json.getString("url"),

                            //SimpleDateFormat creates a format. parse parses a string containing a duration into the specified format
                           date = json.getString("publishedAt").parseDate()
            )
        }
    }
}