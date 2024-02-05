package com.example.ecommerceapp.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerceapp.activity.UserDetailsActivity
import com.example.ecommerceapp.adapter.CartAdapter
import com.example.ecommerceapp.databinding.FragmentCartBinding
import com.example.ecommerceapp.roomDB.ProductDatabase
import com.example.ecommerceapp.roomDB.ProductModelEntity

class CartFragment : Fragment() {

    private lateinit var binding:FragmentCartBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCartBinding.inflate(layoutInflater)

        val pref = requireContext().getSharedPreferences("info", AppCompatActivity.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putBoolean("isCart",false)
        editor.apply()

        val daoinstance = ProductDatabase.getDbInstance(requireContext()).productDao()

        daoinstance.getAllProducts().observe(requireActivity()){
            binding.cartRecyclerView.adapter = CartAdapter(requireContext(),it)
            binding.cartRecyclerView.layoutManager = LinearLayoutManager(requireContext())

            totalCost(it)
        }


        return binding.root
    }

    private fun totalCost(Data: List<ProductModelEntity>?){
        var total= 0
         for(item in Data!!)
         {
             total += item.productSp!!.toInt()
         }
        binding.itemCountTv.text = "Total item is ${Data.size}"
        binding.TotalcostTv.text = "Total item is ${total}"

        binding.checkoutBtn.setOnClickListener{
            val intent = Intent(context,UserDetailsActivity::class.java)
            intent.putExtra("cost",total)
            startActivity(intent)
        }
    }

}