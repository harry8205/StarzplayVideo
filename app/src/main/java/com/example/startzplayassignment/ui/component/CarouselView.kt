package com.example.startzplayassignment.ui.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.startzplayassignment.R
import com.example.startzplayassignment.data.model.MediaItem

class CarouselView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val titleView: TextView
    private val recyclerView: RecyclerView
    private val adapter = MediaItemAdapter()

    init {
        LayoutInflater.from(context).inflate(R.layout.view_carousel, this, true)
        orientation = VERTICAL
        titleView = findViewById(R.id.carouselTitle)
        recyclerView = findViewById(R.id.carouselRecyclerView)

        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = adapter
    }

    fun setTitle(title: String) {
        titleView.text = title
    }

    fun setItems(items: List<MediaItem>) {
        adapter.submitList(items)
    }
}
