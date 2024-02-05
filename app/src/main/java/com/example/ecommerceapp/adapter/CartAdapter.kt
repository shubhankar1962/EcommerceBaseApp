package com.example.ecommerceapp.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecommerceapp.activity.ProductDetailActivity
import com.example.ecommerceapp.databinding.LayoutCartItemBinding
import com.example.ecommerceapp.roomDB.ProductDatabase
import com.example.ecommerceapp.roomDB.ProductModelEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class CartAdapter(val context: Context,val list:List<ProductModelEntity>):RecyclerView.Adapter<CartAdapter.CartViewHolder>(){

    inner class CartViewHolder(val binding: LayoutCartItemBinding):RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = LayoutCartItemBinding.inflate(LayoutInflater.from(context),parent,false)
        return CartViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        Glide.with(context).load(list[position].productImage).into(holder.binding.cartprodImgView)
        holder.binding.cartProductName.text = list[position].productName
        holder.binding.cartProdSp.text = list[position].productSp

        holder.itemView.setOnClickListener{

            val intent = Intent(context,ProductDetailActivity::class.java)
            intent.putExtra("id",list[position].productId)
            context.startActivity(intent)
        }

        holder.binding.deletImg.setOnClickListener {

            GlobalScope.launch(Dispatchers.IO) {

                ProductDatabase.getDbInstance(context).productDao().deleteProduct(
                    ProductModelEntity(
                        list[position].productId,
                        list[position].productName,
                        list[position].productImage,
                        list[position].productSp
                    )
                )
            }
        }
    }


}