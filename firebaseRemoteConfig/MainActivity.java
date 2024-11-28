package com.example.remoteconfig

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.Firebase
import com.google.firebase.remoteconfig.ConfigUpdate
import com.google.firebase.remoteconfig.ConfigUpdateListener
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigException
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.google.firebase.remoteconfig.remoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings

class MainActivity : AppCompatActivity() {

    private lateinit var txt:TextView
    private lateinit var imageView:ImageView
    private lateinit var remoteConfig: FirebaseRemoteConfig
    private lateinit var configSettings: FirebaseRemoteConfigSettings
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        txt = findViewById(R.id.tv)
        imageView = findViewById(R.id.image)
         remoteConfig = Firebase.remoteConfig
         configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 10
        }
        remoteConfig.setConfigSettingsAsync(configSettings)

        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)

        fetchRemoteConfigData()


        //Listen for updates in real time

        remoteConfig.addOnConfigUpdateListener(object : ConfigUpdateListener {
            override fun onUpdate(configUpdate : ConfigUpdate) {
                Log.d("TAG", "Updated keys: " + configUpdate.updatedKeys);

                if (configUpdate.updatedKeys.contains("WISH")|| configUpdate.updatedKeys.contains("IMAGE_URL")) {
                    remoteConfig.activate().addOnCompleteListener {
                        fetchRemoteConfigData()
                    }
                }

            }

            override fun onError(error : FirebaseRemoteConfigException) {
                Log.w("TAG", "Config update error with code: " + error.code, error)
            }
        })

    }

    private fun fetchRemoteConfigData() {
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
        remoteConfig.fetchAndActivate()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val updated = task.result
                    Log.d("TAG", "Config params updated: $updated")
                    val wishMsg = remoteConfig.getString("WISH")
                    val imageUrl = remoteConfig.getString("IMAGE_URL")
                    txt.text = wishMsg
                    Glide.with(this).load(imageUrl).into(imageView)


                } else {
                    Toast.makeText(
                        this,
                        "Fetch failed",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }


}