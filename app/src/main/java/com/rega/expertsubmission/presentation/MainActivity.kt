package com.rega.expertsubmission.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.rega.core.data.repository.Results
import com.rega.expertsubmission.R
import com.rega.expertsubmission.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val vm: MainViewModel by viewModel()
    private lateinit var adapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = NewsAdapter()
        setupRecycler()
        observeViewModel()
        vm.getNews()
    }

    private fun setupRecycler() {
        binding.rvNews.layoutManager = LinearLayoutManager(this)
        binding.rvNews.adapter = adapter
    }

    private fun observeViewModel() {
        vm.results.observe(this) { result ->
            binding.progressBar.isVisible = result is Results.Loading
            when (result) {
                is Results.Success -> {
                    Log.d("MainActivity", "Data masuk: ${result.data}")
                    adapter.submitList(result.data)
                }
                is Results.Error -> {
                    Log.e("MainActivity", "Error: ${result.errorMessage}")
                    Toast.makeText(this, result.errorMessage, Toast.LENGTH_SHORT).show()
                }
                else -> Unit
            }
        }
        }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_favorite -> {
                try {
                    installFavoriteModule()
                } catch (e: ClassNotFoundException) {
                    Toast.makeText(this, "Fitur Favorite belum tersedia", Toast.LENGTH_SHORT).show()
                    Log.e("MainActivity", "FavoriteActivity not found", e)
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun installFavoriteModule() {
        val splitInstallManager = SplitInstallManagerFactory.create(this)
        val moduleFavorite = "favorite"
        if (splitInstallManager.installedModules.contains(moduleFavorite)) {
            moveToFavoriteActivity()
            Toast.makeText(this, "Open module", Toast.LENGTH_SHORT).show()
        } else {
            val request = SplitInstallRequest.newBuilder()
                .addModule(moduleFavorite)
                .build()
            splitInstallManager.startInstall(request)
                .addOnSuccessListener {
                    Toast.makeText(this, "Success installing module", Toast.LENGTH_SHORT).show()
                    moveToFavoriteActivity()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Error installing module", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun moveToFavoriteActivity() {
        startActivity(Intent(this, Class.forName("com.rega.expertsubmission.favorite.presentation.FavoriteActivity")))
    }
}



