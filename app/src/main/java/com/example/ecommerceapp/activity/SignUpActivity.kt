package com.example.ecommerceapp.activity

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.ecommerceapp.R
import com.example.ecommerceapp.databinding.ActivitySignUpBinding
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding:ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginBtn.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }

        binding.signUPBtn.setOnClickListener {
            validateUser()
        }
    }

    private fun validateUser() {
        if(binding.userNametv.text.isEmpty() || binding.phoneNotv.text.isEmpty())
        {
            Toast.makeText(this,"fields shound not be empty",Toast.LENGTH_SHORT).show()
        }else{
            storeData()
        }
    }

    private fun storeData() {
        val builder =AlertDialog.Builder(this)
            .setTitle("Loading...")
            .setMessage("Please wait")
            .setCancelable(false)
            .create()
        builder.show()

        val pref = this.getSharedPreferences("user", MODE_PRIVATE)
        val editor = pref.edit()

        editor.putString("number",binding.phoneNotv.text.toString())
        editor.putString("name",binding.userNametv.text.toString())
        editor.apply()

        val dbref = FirebaseDatabase.getInstance().getReference("userData").child("item")
        val key = dbref.push().key
        val map = HashMap<String,Any>()

        map["userName"] = binding.userNametv.text.toString()
        map["userNo"] = binding.phoneNotv.text.toString()

        key?.let {
            dbref.child(it).setValue(map).addOnCompleteListener {dbtask->
                if(dbtask.isSuccessful) {
                    builder.dismiss()
                    Toast.makeText(this, "user is saved successfully", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this,"user not saved in db",Toast.LENGTH_SHORT).show()
                }

            }
        }
    }
}