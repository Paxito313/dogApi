package com.example.dogapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.dogapp.R
import com.example.dogapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configura Toolbar como ActionBar
        setSupportActionBar(binding.toolbar)

        // Configura NavController con ActionBar
        val navController = findNavController(R.id.fragment_container)
        setupActionBarWithNavController(navController)

        // Botón de favoritos
        binding.fabFavorites.setOnClickListener {
            navController.navigate(R.id.action_dogListFragment_to_favoritesFragment)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragment_container)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
