package com.example.youthcareproject

import android.content.res.Resources
import android.os.Build
import android.text.Html
import android.text.Spanned
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.youthcareproject.database.Post

fun formatPosts(posts: List<Post>, resources: Resources): Spanned {
    val sb = StringBuilder()
    sb.apply {
        posts.forEach {
            append("<br>")
            append("test")
            append(it.postId)
        }
    }
    return Html.fromHtml(sb.toString(), Html.FROM_HTML_MODE_LEGACY)
}

class TextItemViewHolder(val textView: TextView): RecyclerView.ViewHolder(textView)
