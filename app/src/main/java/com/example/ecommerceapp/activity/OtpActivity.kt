package com.example.ecommerceapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.ecommerceapp.MainActivity
import com.example.ecommerceapp.R
import com.example.ecommerceapp.databinding.ActivityOtpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

class OtpActivity : AppCompatActivity() {

    private lateinit var binding:ActivityOtpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.verifyBtn.setOnClickListener {
            if(binding.otp.text.isEmpty())
            {
                Toast.makeText(this,"otp field should not be empty",Toast.LENGTH_SHORT).show()
            }
            else{
                verifyOtp(binding.otp.text.toString())
            }
        }
    }

    private fun verifyOtp(otp: String) {

        val credential = PhoneAuthProvider.getCredential(intent.getStringExtra("varificationId")!!,otp)

        signInPhoneAuthCredential(credential)
    }

    private fun signInPhoneAuthCredential(credential: PhoneAuthCredential) {
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener(this){task->
                if(task.isSuccessful){

                    val pref = this.getSharedPreferences("user", MODE_PRIVATE)
                    val editor = pref.edit()

                    editor.putString("number",intent.getStringExtra("number")!!)
                    editor.apply()

                    startActivity(Intent(this,MainActivity::class.java))
                    finish()
                }else{
                    Toast.makeText(this,"Something went wrong",Toast.LENGTH_SHORT).show()
                }
            }
    }
}