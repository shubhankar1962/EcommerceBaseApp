package com.example.ecommerceapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecommerceapp.R
import com.example.ecommerceapp.activity.CategoryActivity
//import com.example.ecommerceapp.activity.CategoryActivity
import com.example.ecommerceapp.databinding.LayoutCategoryItemBinding

import com.example.ecommerceapp.models.CategoryModel

class CategoryAdapter(var context: Context,var list:ArrayList<CategoryModel>): RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    inner class CategoryViewHolder(view:View):RecyclerView.ViewHolder(view){
        var binding = LayoutCategoryItemBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_category_item,parent,false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.binding.categoryItemTv.text = list[position].cat
        Glide.with(context).load(list[position].image).into(holder.binding.categoryItemImg)

        holder.itemView.setOnClickListener{
            val intent = Intent(context, CategoryActivity::class.java)
            intent.putExtra("cat",list[position].cat)
            context.startActivity(intent)
        }
    }
}