package com.example.ecommerceapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecommerceapp.activity.ProductDetailActivity
import com.example.ecommerceapp.databinding.FragmentHomeBinding
import com.example.ecommerceapp.databinding.LayoutProductItemBinding
import com.example.ecommerceapp.models.AddProductModel

class ProductAdapter(val context:Context,val productList:ArrayList<AddProductModel>):RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(val binding: LayoutProductItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = LayoutProductItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ProductViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {

        val data = productList[position]

        Glide.with(context).load(data.productCoverImg).into(holder.binding.ProductImgView)
        holder.binding.ProductName.text = data.productName
        holder.binding.productMRP.text = data.productMrp
        holder.binding.productCatgory.text = data.productCategory

        holder.binding.buyBtn.text = data.productSp

        holder.itemView.setOnClickListener{

            val intent = Intent(context, ProductDetailActivity::class.java)
            intent.putExtra("id",productList[position].productId)
            context.startActivity(intent)
        }
    }


}