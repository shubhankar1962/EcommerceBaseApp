package com.example.ecommerceapp.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecommerceapp.activity.ProductDetailActivity
import com.example.ecommerceapp.databinding.ItemCategoryLayoutActivityBinding
import com.example.ecommerceapp.models.AddProductModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CategoryProductAdapter (val context:Context,val list:ArrayList<AddProductModel>):RecyclerView.Adapter<CategoryProductAdapter.CategoryProductViewHolder>(){

    inner class CategoryProductViewHolder(val binding:ItemCategoryLayoutActivityBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryProductViewHolder {
        val binding = ItemCategoryLayoutActivityBinding.inflate(LayoutInflater.from(context),parent,false)
        return CategoryProductViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: CategoryProductViewHolder, position: Int) {
        MainScope().launch {
            withContext(Dispatchers.Main) {
                Log.e("Tag",list[position].productCoverImg.toString())
                Glide.with(context).load(list[position].productCoverImg).into(holder.binding.imgView)

                holder.binding.textView.text = list[position].productName
                holder.binding.textView2.text = list[position].productSp

                holder.itemView.setOnClickListener{

                    val intent = Intent(context,ProductDetailActivity::class.java)
                    intent.putExtra("id",list[position].productId)
                    context.startActivity(intent)
                }
            }
        }
    }
}