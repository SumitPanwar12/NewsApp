package com.example.newsapp

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_news.view.*


class NewsListAdapter( private val listner: NewItemClicked ) : androidx.recyclerview.widget.RecyclerView.Adapter<NewsViewHolder>() {



    private val items: ArrayList<News> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent,false)
        val viewHolder = NewsViewHolder(view)
        view.setOnClickListener{

            listner.onItemClicked(items[viewHolder.adapterPosition])
        }



        return viewHolder
    }

    override fun getItemCount(): Int {

        return items.count()

    }

    override fun onBindViewHolder(p0: NewsViewHolder, p1: Int) {
        val currentItem = items[p1]
        p0.titleView.text = currentItem.title
        p0.author.text = currentItem.author
        Glide.with(p0.itemView.context).load(currentItem.imageUrl).into(p0.image)

    }

    fun updatedNews(updatedNews : ArrayList<News>){
        items.clear()
        items.addAll(updatedNews)

        notifyDataSetChanged()

    }

}



class NewsViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView){
            val titleView: TextView = itemView.findViewById(R.id.title)
            val image:ImageView = itemView.findViewById((R.id.image))
            val author: TextView =  itemView.findViewById(R.id.author)
}

interface NewItemClicked{

    fun onItemClicked(item: News)
}