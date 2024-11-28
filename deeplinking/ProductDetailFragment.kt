package com.orange.flowchecklist.ui.product_detail

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import com.bumptech.glide.Glide
import com.orange.flowchecklist.R
import com.orange.flowchecklist.databinding.FragmentProductDetailBinding
import com.orange.flowchecklist.ui.home.Product


class ProductDetailFragment : Fragment() {
    private lateinit var binding:FragmentProductDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
         binding = FragmentProductDetailBinding.inflate(layoutInflater,container,false)
        init()
        return binding.root
    }

    private fun init() {
        // Get the data from the intent
//        val data: Uri? = arguments.getInt("productId")

        val productId = arguments?.getInt("productId")?:-1
        val product = getProductById(productId)

        // Check if the data is not null
        if (productId != -1) {
                // Retrieve product details by ID
                val product = getProductById(productId)

                if (product != null) {
                    // Populate product details UI
                    populateProductDetails(product)
                    return
                }

        }




        if (product != null) {
            val productImageView: ImageView = binding.productImageView
            val productNameTextView: TextView = binding.productNameTextView
            val productDescriptionTextView: TextView =binding.productDescriptionTextView
            val shareButton: Button = binding.shareButton

            // Load image from URL using Glide
            Glide.with(requireActivity())
                .load(product.imageUrl)
                .placeholder(R.drawable.product) // Placeholder image while loading
                .error(R.drawable.product) // Error image if loading fails
                .into(productImageView)
            productNameTextView.text = product.name
            productDescriptionTextView.text = product.description

            shareButton.setOnClickListener {
                val shareIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, "https://example.com/products/$productId")
                    type = "text/plain"
                }
                startActivity(shareIntent)
            }
        } else {
            // Handle invalid product ID
            showError()
        }
    }
    private fun showError() {
        // Show an error message or handle the error case as needed
        Toast.makeText(requireContext(), "Error: Product not found", Toast.LENGTH_SHORT).show()
//        finish() // Close the activity if there's an error
    }
    private fun getProductById(productId: Int): Product? {
        // Implement logic to retrieve product by ID from local data
        // Here, you would typically fetch it from a database or some other data source
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

        return products.find { it.id == productId }
    }

    private fun populateProductDetails(product: Product) {
        // Populate UI with product details
        val productImageView: ImageView =binding.productImageView
        val productNameTextView: TextView =binding.productDescriptionTextView
        val productDescriptionTextView: TextView =binding.productDescriptionTextView
        val shareButton: Button =binding.shareButton

        productImageView.setImageDrawable(getDrawable(requireContext(),product.imageUrl))
        productNameTextView.text = product.name
        productDescriptionTextView.text = product.description

        shareButton.setOnClickListener {
            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "https://example.com/products/${product.id}")
                type = "text/plain"
            }
            startActivity(shareIntent)
        }
    }

}