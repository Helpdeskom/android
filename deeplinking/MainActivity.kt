package com.orange.flowchecklist.ui.activity.main

import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.orange.flowchecklist.databinding.ActivityMainBinding
import com.orange.flowchecklist.utils.constant.AppConstants
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    var hasInternet: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val data: Uri? = intent.data

        val productId =  data?.lastPathSegment?.toIntOrNull()
        if(productId!=-1){
            AppConstants.PRODUCT_ID = productId?:-1
        }
        setContentView(binding.root)
    }


}