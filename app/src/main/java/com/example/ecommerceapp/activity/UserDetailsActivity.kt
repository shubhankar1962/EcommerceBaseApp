package com.example.ecommerceapp.activity

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.ecommerceapp.databinding.ActivityCheckOutBinding
import com.example.ecommerceapp.databinding.ActivityUserDetailBinding
import com.google.firebase.database.FirebaseDatabase

class UserDetailsActivity : AppCompatActivity() {

    private lateinit var binding:ActivityUserDetailBinding
    lateinit var pref:SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadUserInfo()

        pref = this.getSharedPreferences("user", MODE_PRIVATE)
        binding.proceedTocheckoutBtn.setOnClickListener {
            validateData(binding.nameEt.text.toString(),
                binding.phnNoEt.text.toString(),
                binding.plotEt.text.toString(),
                binding.streetEt.text.toString(),
                binding.cityEt.text.toString(),
                binding.stateEt.text.toString()
                )
        }

    }

    private fun validateData(
        name: String,
        phnNo: String,
        plot: String,
        street: String,
        city: String,
        state: String
    ) {

        if(name.isEmpty() || phnNo.isEmpty() || plot.isEmpty() || street.isEmpty() || city.isEmpty() || state.isEmpty())
        {
            Toast.makeText(this,"All fields are required ",Toast.LENGTH_SHORT).show()
        }else{
            storeData(name,phnNo,plot,street,city,state)
        }
    }

    private fun storeData(name: String, phnNo: String, plot: String, street: String, city: String, state: String) {
        val dbref = FirebaseDatabase.getInstance().getReference("users").child(pref.getString("number",phnNo)!!)

        val userkey =dbref.push().key

        val map = HashMap<String,Any>()
        map["name"] = name
        map["phone"] = phnNo
        map["plot"] = plot
        map["street"] = street
        map["city"] = city
        map["state"] = state

        userkey?.let {
            dbref.child(it).setValue(map).addOnCompleteListener{task->
                if(task.isSuccessful)
                {
                    Toast.makeText(this,"user data saved to db",Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this,CheckOutActivity::class.java))

                }else{
                    Toast.makeText(this,"something went wrong",Toast.LENGTH_SHORT).show()
                }
            }
        }

    }


    private fun loadUserInfo() {
        val pref = getSharedPreferences("user", MODE_PRIVATE)


    }
}