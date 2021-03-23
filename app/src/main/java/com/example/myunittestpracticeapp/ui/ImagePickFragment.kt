package com.example.myunittestpracticeapp.ui

import android.os.Bundle
import android.view.View
import android.widget.GridLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myunittestpracticeapp.R
import com.example.myunittestpracticeapp.adapters.ImageAdapter
import com.example.myunittestpracticeapp.util.Constants.GRID_SPAN_COUNT
import kotlinx.android.synthetic.main.fragment_image_pick.*
import javax.inject.Inject

class ImagePickFragment @Inject constructor(
    private val imageAdapter: ImageAdapter
) : Fragment(R.layout.fragment_image_pick) {

    lateinit var viewModel: ShoppingViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(ShoppingViewModel::class.java)

        setupRecyclerView()

        imageAdapter.setOnItemClickListener {
            findNavController().popBackStack()
            viewModel.setCurrentImageUrl(it)
        }

    }

    private fun setupRecyclerView() {

        rvImages.apply {
            adapter = imageAdapter
            layoutManager = GridLayoutManager(requireContext(), GRID_SPAN_COUNT)
        }

    }

}