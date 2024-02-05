package com.example.ecommerceapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerceapp.R
import com.example.ecommerceapp.adapter.CategoryProductAdapter
import com.example.ecommerceapp.adapter.ProductAdapter
import com.example.ecommerceapp.databinding.ActivityCategoryBinding
import com.example.ecommerceapp.models.AddProductModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CategoryActivity : AppCompatActivity() {

    private lateinit var binding:ActivityCategoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        GlobalScope.launch {
            getProducts(intent.getStringExtra("cat"))
        }
    }

    private  fun getProducts(category: String?) {
        var productData: AddProductModel?

        Log.e("Tag",category.toString())
        val list = ArrayList<AddProductModel>()
        val databaseref = FirebaseDatabase.getInstance().getReference("products")
        databaseref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                for(data in snapshot.children)
                {
                    productData = data.child("productModel").getValue(AddProductModel::class.java)

                    if(productData?.productCategory.toString() == category.toString())
                    {
                        productData?.let { list.add(it)
                            Log.e("Tag:data",it.toString())
                        }
                    }

                }


                binding.catRecyclerView.adapter = CategoryProductAdapter(this@CategoryActivity,list)
                binding.catRecyclerView.layoutManager = LinearLayoutManager(this@CategoryActivity)

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

}