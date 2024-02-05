package com.example.ecommerceapp.fragment

import android.app.Dialog
import android.app.ProgressDialog
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecommerceapp.R

import com.example.ecommerceapp.adapter.CategoryAdapter
//import com.example.ecommerceapp.adapter.CategoryAdapter
import com.example.ecommerceapp.adapter.ProductAdapter
import com.example.ecommerceapp.databinding.FragmentHomeBinding
import com.example.ecommerceapp.models.AddProductModel
import com.example.ecommerceapp.models.CategoryModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

//
//class HomeFragment : Fragment() {
//
//    // ... existing code ...
//    private lateinit var binding:FragmentHomeBinding
//        private lateinit var dialog:Dialog
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        binding = FragmentHomeBinding.inflate(layoutInflater)
//        dialog = Dialog(requireContext())
//        dialog.setContentView(R.layout.dialog_layout)
//        dialog.setCancelable(false)
//
//        CoroutineScope(Dispatchers.Main).launch {
//            withContext(Dispatchers.IO) {
//                getCategories()
//                delay(4000)
//                getSliderImage()
//                delay(4000)
//                getProducts()
//            }
//        }
//
//        return binding.root
//    }
//
//    // ... existing code ...
//
//    private suspend fun getSliderImage() = withContext(Dispatchers.IO) {
//        val dbref = FirebaseDatabase.getInstance().getReference("slider").child("items")
//        dbref.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                for(data in snapshot.children) {
//                    val img = data.child("pics").getValue(String::class.java)
//                    Glide.with(requireContext()).load(img).into(binding.SliderImg)
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                // Handle error if needed
//            }
//        })
//    }
//
//    private suspend fun getProducts() = withContext(Dispatchers.IO) {
//        var productData: AddProductModel?
//        val list = ArrayList<AddProductModel>()
//        val databaseref = FirebaseDatabase.getInstance().getReference("products")
//        databaseref.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//
//                for (data in snapshot.children) {
//                    productData = data.child("productModel").getValue(AddProductModel::class.java)
//                    Log.e("TAG",productData?.productName.toString())
//                    list.add(productData!!)
//                }
//
//                CoroutineScope(Dispatchers.Main).launch {
//                    binding.productRecyclerView.adapter = ProductAdapter(requireContext(), list)
//                    binding.productRecyclerView.layoutManager = LinearLayoutManager(requireContext())
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                // Handle error if needed
//            }
//        })
//    }
//
//    private suspend fun getCategories() = withContext(Dispatchers.IO) {
//        var imgLink: String?
//        var catLink: String?
//        val list = ArrayList<CategoryModel>()
//        val databaseref = FirebaseDatabase.getInstance().getReference("Categories").child("items")
//        databaseref.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                list.clear()
//                for(data in snapshot.children) {
//                    dialog.dismiss()
//                    imgLink = data.child("pics").getValue(String::class.java)
//                    catLink = data.child("cat").getValue(String::class.java)
//                    list.add(CategoryModel(catLink, imgLink))
//                }
//
//                CoroutineScope(Dispatchers.Main).launch(){
//                    binding.categoryRecyclerView.adapter = CategoryAdapter(requireContext(), list)
//                    binding.categoryRecyclerView.layoutManager = LinearLayoutManager(
//                        requireContext(),
//                        RecyclerView.HORIZONTAL, false
//                    )
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                // Handle error if needed
//            }
//        })
//    }
//}

class HomeFragment : Fragment() {

    private lateinit var binding:FragmentHomeBinding
    private lateinit var dialog:Dialog
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentHomeBinding.inflate(layoutInflater)

        dialog = Dialog(requireContext())

        dialog.setContentView(R.layout.dialog_layout)
        dialog.setCancelable(false)

        val pref = requireContext().getSharedPreferences("info",AppCompatActivity.MODE_PRIVATE)

        if(pref.getBoolean("isCart",true))
        {
            findNavController().navigate(R.id.action_homeFragment_to_cartFragment)
        }

        getCategories()
        getSliderImage()
        getProducts()

        return binding.root
    }

    private fun getSliderImage() {

        val dbref = FirebaseDatabase.getInstance().getReference("slider").child("items")
        dbref.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(data in snapshot.children)
                {

                    val img = data.child("pics").getValue(String::class.java)
                    Glide.with(requireContext()).load(img).into(binding.SliderImg)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }

    private fun getProducts() {

            var productData: AddProductModel?

            val list = ArrayList<AddProductModel>()
            val databaseref = FirebaseDatabase.getInstance().getReference("products")
            databaseref.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    list.clear()
                    for (data in snapshot.children) {
                        productData =
                            data.child("productModel").getValue(AddProductModel::class.java)
                        Log.e("Tag",productData?.productName.toString())
                        list.add(productData!!)
                    }

                    binding.productRecyclerView.adapter = ProductAdapter(requireContext(), list)
                    binding.productRecyclerView.layoutManager =
                        LinearLayoutManager(requireContext())

                }

                override fun onCancelled(error: DatabaseError) {

                }

            })

    }

    private fun getCategories() {

        var imgLink:String?
        var catLink:String?
        var list = ArrayList<CategoryModel>()
        val databaseref = FirebaseDatabase.getInstance().getReference("Categories").child("items")
        databaseref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                list.clear()
                for(data in snapshot.children)
                {
                    dialog.dismiss()
                    imgLink = data.child("pics").getValue(String::class.java)
                    catLink = data.child("cat").getValue(String::class.java)
                    list.add(CategoryModel(catLink,imgLink))
                }
                binding.categoryRecyclerView.adapter = CategoryAdapter(requireContext(),list)
                binding.categoryRecyclerView.layoutManager = LinearLayoutManager(requireContext(),
                    RecyclerView.HORIZONTAL,false)

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }


}