package com.example.material

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class FruitAdapter(val mContext: Context,val mList:List<Fruit>):RecyclerView.Adapter<FruitAdapter.ViewHolder>() {

    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val iv:ImageView = view.findViewById(R.id.iv)
        val tv:TextView = view.findViewById(R.id.tv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FruitAdapter.ViewHolder {
        val view =LayoutInflater.from(mContext).inflate(R.layout.item_cardview,parent,false)
        val holder = ViewHolder(view)
        holder.itemView.setOnClickListener{
            val position = holder.adapterPosition
            val fruit = mList[position]
            val intent = Intent(mContext,FruitActivity::class.java).apply {
                putExtra(FruitActivity.FRUIT_NAME,fruit.name)
                putExtra(FruitActivity.FRUIT_IMAGE_ID,fruit.imageId)
            }
            mContext.startActivity(intent)
        }
        return holder
    }

    override fun getItemCount() = mList.size

    override fun onBindViewHolder(holder: FruitAdapter.ViewHolder, position: Int) {
        val fruit = mList[position]
        holder.tv.text = fruit.name
        Glide.with(mContext).load(fruit.imageId).into(holder.iv)
    }
}