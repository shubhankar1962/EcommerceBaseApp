package com.example.ecommerceapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.ecommerceapp.MainActivity
import com.example.ecommerceapp.R
import com.example.ecommerceapp.adapter.CategoryProductAdapter
import com.example.ecommerceapp.databinding.ActivityProductDetailBinding
import com.example.ecommerceapp.models.AddProductModel
import com.example.ecommerceapp.roomDB.ProductDao
import com.example.ecommerceapp.roomDB.ProductDatabase
import com.example.ecommerceapp.roomDB.ProductModelEntity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductDetailActivity : AppCompatActivity() {

    private lateinit var binding:ActivityProductDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        getProductsDetail(intent.getStringExtra("id"))


    }

    private fun getProductsDetail(pId: String?) {
        var productData: AddProductModel?
        var imgData:String?


        var imgList = ArrayList<String>()
        val databaseref = FirebaseDatabase.getInstance().getReference("products")
        databaseref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                for(data in snapshot.children) {
                    productData = data.child("productModel").getValue(AddProductModel::class.java)

                    if (productData?.productId.toString() == pId.toString()) {

                        productData?.let {
                            binding.prodctTitle.text = it.productName
                            binding.prodctDescription.text = it.productDescription
                            binding.productsp.text = it.productSp

                            val list = it.productImage as ArrayList<String>
                            val slideList = ArrayList<SlideModel>()
                            for(data in list)
                            {
                                slideList.add(SlideModel(data,scaleType = ScaleTypes.CENTER_CROP))
                            }

                            cartAction(pId.toString(),it.productName,it.productCoverImg,it.productSp)
                            binding.imageSlider.setImageList(slideList)
                        }

                    }
                }


            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    private fun cartAction(productId: String, productName: String?, productImage: String?, productSp: String?) {

        val productDao = ProductDatabase.getDbInstance(applicationContext).productDao()

        if(productDao.isExist(productId) != null)
        {
            binding.addToCartBtn.text = "Go To Cart"
        }else{
            binding.addToCartBtn.text = "Add To Cart"
        }

        binding.addToCartBtn.setOnClickListener{

            if(productDao.isExist(productId) != null)
            {
                openCart()
            }else{
                addToCart(productId,productName,productImage,productSp,productDao)
            }
        }
    }

    private fun addToCart(productId: String, productName: String?, productImage: String?, productSp: String?,productDao: ProductDao) {
        val data = ProductModelEntity(productId,productName,productImage,productSp)

        lifecycleScope.launch(Dispatchers.IO) {
            productDao.insertProduct(data)
            binding.addToCartBtn.text = "Go To Cart"
        }

    }

    private fun openCart() {
        val pref = this.getSharedPreferences("info", MODE_PRIVATE)
        val editor = pref.edit()
        editor.putBoolean("isCart",true)
        editor.apply()

        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }
}