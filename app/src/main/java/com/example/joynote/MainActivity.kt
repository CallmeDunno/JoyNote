package com.example.joynote

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.joynote.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()

    }

    private fun initView() {
        val size = intent.getIntExtra("notes", 10)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController

//        if (size == 0){
//            navController.popBackStack()
//            navController.navigate(R.id.starterFragment)
//        }
    }

//    override fun onBackPressed() {
//        super.onBackPressed()
//    }

//    override fun onSupportNavigateUp(): Boolean {
//        return super.onSupportNavigateUp()
//    }
}