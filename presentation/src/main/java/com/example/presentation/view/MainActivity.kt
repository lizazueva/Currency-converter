package com.example.presentation.view

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.presentation.databinding.ActivityMainBinding
import com.example.presentation.viewModel.CurrencyViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val viewModel: CurrencyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.convertResult.observe(this) { result ->
            binding.tvResult.text = result
        }

        viewModel.loading.observe(this) { isLoading ->
            binding.prBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        binding.btnConvert.setOnClickListener {
            viewModel.convert(
                binding.etFrom.text.toString(),
                binding.spFromCurrency.selectedItem.toString(),
                binding.spToCurrency.selectedItem.toString(),
            )
        }
    }
}