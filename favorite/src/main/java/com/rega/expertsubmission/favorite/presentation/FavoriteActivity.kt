package com.rega.expertsubmission.favorite.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.rega.core.data.repository.Results
import com.rega.expertsubmission.favorite.databinding.ActivityFavoriteBinding
import com.rega.expertsubmission.presentation.NewsAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules


class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private val favoriteViewModel: FavoriteViewModel by viewModel()
    private val adapter = NewsAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadKoinModules(favoriteViewModelModule)

        binding.rvFavorite.layoutManager = LinearLayoutManager(this)
        binding.rvFavorite.adapter = adapter

        observeFavoriteNews()
        favoriteViewModel.getFavoriteNews()
    }


    private fun observeFavoriteNews() {
        favoriteViewModel.results.observe(this) { result ->
            when (result) {
                is Results.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Results.Success -> {
                    binding.progressBar.visibility = View.GONE
                    adapter.submitList(result.data)
                    binding.tvEmpty.visibility = if (result.data.isEmpty()) View.VISIBLE else View.GONE
                }
                is Results.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, "Terjadi kesalahan: ${result.errorMessage}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
