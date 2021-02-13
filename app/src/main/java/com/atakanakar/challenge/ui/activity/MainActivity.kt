package com.atakanakar.challenge.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.atakanakar.challenge.R
import com.atakanakar.challenge.databinding.ActivityMainBinding
import com.atakanakar.challenge.network.OmbdApi
import com.atakanakar.challenge.ui.dialog.InfoDialog
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.Dispatcher
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainVM by viewModels()
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
    }

    fun showLoading(isLoad: Boolean) {
        binding.containerProgress.visibility = if (isLoad) View.VISIBLE else View.GONE
    }

}