package com.rega.expertsubmission.presentation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.rega.core.data.repository.Results
import com.rega.core.domain.model.News
import com.rega.expertsubmission.R
import com.rega.expertsubmission.databinding.ActivityDetailBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private var newsItem: News? = null

    private val detailViewModel: DetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        newsItem = intent.getParcelableExtra("extra_news")
        newsItem?.let { news ->
            detailViewModel.setNews(news)
            binding.tvDetailTitle.text = news.title
            binding.tvDetailContent.text = news.content
            Glide.with(this).load(news.imageUrl).into(binding.imgDetailNews)

            binding.btnReadMore.setOnClickListener {
                news.url?.let { url ->
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    startActivity(intent)
                }
            }

            detailViewModel.results.observe(this) { result ->
                when (result) {
                    is Results.Loading -> {
                        // disini saya tidak memasukkan fitur loading karena tidak wajib
                    }
                    is Results.Success -> {
                        newsItem = result.data
                        updateBookmarkIcon(result.data.isFavorite)
                    }
                    is Results.Error -> {
                        Toast.makeText(this, "Gagal update bookmark", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            binding.btnBookmark.setOnClickListener {
                val currentNews = detailViewModel.results.value
                if (currentNews is Results.Success) {
                    val news = currentNews.data
                    val newState = !news.isFavorite
                    detailViewModel.setFavoriteAnime(news, newState)
                }
            }
        }
    }


    private fun updateBookmarkIcon(isFavorite: Boolean) {
        if (isFavorite) {
            binding.btnBookmark.setImageResource(R.drawable.baseline_bookmark_24)
        } else {
            binding.btnBookmark.setImageResource(R.drawable.baseline_bookmark_border_24)
        }
    }
}
