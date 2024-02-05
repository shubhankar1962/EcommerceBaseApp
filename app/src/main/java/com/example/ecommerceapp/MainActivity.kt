package com.example.ecommerceapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.PopupMenu
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.ecommerceapp.activity.LoginActivity
import com.example.ecommerceapp.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(FirebaseAuth.getInstance().currentUser == null){
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView)
        val navController = navHostFragment!!.findNavController()

        val popupMenu = PopupMenu(this,null)
        popupMenu.inflate(R.menu.bottom_nav_menu)
        binding.bottomNav.setupWithNavController(navController)

        binding.bottomNav.setOnItemSelectedListener {
            when(it.itemId)
            {
                R.id.homeFragment->{
                    navHostFragment.findNavController().navigate(R.id.homeFragment)
                    return@setOnItemSelectedListener true
                }
                R.id.cartFragment->{
                    navHostFragment.findNavController().navigate(R.id.cartFragment)
                    return@setOnItemSelectedListener true
                }
                R.id.moreFragment->{
                    navHostFragment.findNavController().navigate(R.id.moreFragment)
                    return@setOnItemSelectedListener true
                }else->{
                    return@setOnItemSelectedListener false
                }

            }
        }


        navController.addOnDestinationChangedListener(object :NavController.OnDestinationChangedListener{
            override fun onDestinationChanged(
                controller: NavController,
                destination: NavDestination,
                arguments: Bundle?
            ) {
                title = when(destination.id)
                {
                    R.id.cartFragment-> "My Cart"
                    R.id.moreFragment-> "more items"
                    else-> "E-cart"
                }
            }

        })
    }
}