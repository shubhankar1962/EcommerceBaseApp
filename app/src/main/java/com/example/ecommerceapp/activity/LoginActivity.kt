package com.example.ecommerceapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.ecommerceapp.R
import com.example.ecommerceapp.databinding.ActivityLoginBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class LoginActivity : AppCompatActivity() {

    private lateinit var binding:ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.LoginsignUPBtn.setOnClickListener {
            startActivity(Intent(this,SignUpActivity::class.java))
            finish()
        }

        binding.loginBtn.setOnClickListener {
            if(binding.loginPhoneNotv.text.isEmpty()){
                Toast.makeText(this,"field can't be empty",Toast.LENGTH_SHORT).show()
            }
            else{
                sendOtp(binding.loginPhoneNotv.text.toString())
            }
        }
    }

    private lateinit var builder : AlertDialog
    private fun sendOtp(pno: String) {
        builder = AlertDialog.Builder(this)
            .setTitle("Loading...")
            .setMessage("Please wait")
            .setCancelable(false)
            .create()
        builder.show()

        val options = PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
            .setPhoneNumber("+91$pno")
            .setTimeout(60L,TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(callbacks)
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)

    }

    val callbacks = object :PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
        override fun onVerificationCompleted(p0: PhoneAuthCredential) {

        }

        override fun onVerificationFailed(p0: FirebaseException) {

        }

        override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
            super.onCodeSent(verificationId,token)

            builder.dismiss()
            val intent = Intent(this@LoginActivity,OtpActivity::class.java)
            intent.putExtra("varificationId",verificationId)
            intent.putExtra("number",binding.loginPhoneNotv.text.toString())
            startActivity(intent)
        }

    }
}