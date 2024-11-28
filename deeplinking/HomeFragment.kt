package com.orange.flowchecklist.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.orange.flowchecklist.R
import com.orange.flowchecklist.databinding.FragmentHomeBinding
import com.orange.flowchecklist.utils.constant.AppConstants


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        val products = listOf(
            Product(1, "iPhone 13 Pro", "The iPhone 13 Pro features a stunning Super Retina XDR display, powerful A15 Bionic chip, and advanced camera system.", R.drawable.iphone13),
            Product(2, "Samsung Galaxy S21 Ultra", "The Samsung Galaxy S21 Ultra is a powerhouse smartphone with a massive 6.8-inch Dynamic AMOLED 2X display and versatile camera setup.", R.drawable.samsung_galaxy),
            Product(3, "Google Pixel 6 Pro", "The Google Pixel 6 Pro boasts a brilliant 6.7-inch OLED display and delivers exceptional camera performance with its advanced computational photography features.", R.drawable.google_pixel),
            Product(4, "OnePlus 9 Pro", "The OnePlus 9 Pro offers a fluid 120Hz AMOLED display, blazing-fast performance, and Hasselblad-tuned camera system for stunning photos.", R.drawable.oneplus9),
            Product(5, "Xiaomi Mi 11 Ultra", "The Xiaomi Mi 11 Ultra is a flagship smartphone with a large 6.81-inch AMOLED display, Snapdragon 888 chipset, and impressive triple camera setup.", R.drawable.mm1),
            Product(6, "Sony Xperia 1 III", "The Sony Xperia 1 III features a 4K OLED display, Snapdragon 888 processor, and a versatile camera system with ZEISS optics.", R.drawable.sonyexperia1),
            Product(7, "Apple Watch Series 7", "The Apple Watch Series 7 offers an always-on Retina display, advanced health tracking features, and support for fast charging.", R.drawable.applewatch7),
            Product(8, "Samsung Galaxy Watch 4", "The Samsung Galaxy Watch 4 combines style with function, featuring a sleek design, advanced health monitoring capabilities, and seamless integration with Galaxy devices.", R.drawable.samsunggalaxywatch4),
            Product(9, "Fitbit Charge 5", "The Fitbit Charge 5 is a feature-packed fitness tracker with built-in GPS, heart rate monitoring, and up to 7 days of battery life.", R.drawable.fitbitcharge5),
            Product(10, "Garmin Fenix 7", "The Garmin Fenix 7 is a rugged multisport GPS watch with advanced training features, offline maps, and up to 12 days of battery life.", R.drawable.garmin)
        )
        if(AppConstants.PRODUCT_ID!=-1){
            val bundle = Bundle()
            bundle.putInt("productId", AppConstants.PRODUCT_ID)
            findNavController().navigate(R.id.action_homeFragment_to_productDetailFragment, bundle)
            AppConstants.PRODUCT_ID  =-1
        }


        val recyclerView: RecyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = ProductAdapter(products) { product ->
            val bundle = Bundle()
            bundle.putInt("productId", product.id)
            findNavController().navigate(R.id.action_homeFragment_to_productDetailFragment, bundle)

        }
        return binding.root
    }

}