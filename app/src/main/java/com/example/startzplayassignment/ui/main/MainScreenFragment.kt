package com.example.startzplayassignment.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.startzplayassignment.R
import com.example.startzplayassignment.data.model.MediaItem
import com.example.startzplayassignment.data.network.NetworkModule
import com.example.startzplayassignment.data.network.RemoteDataSource
import com.example.startzplayassignment.databinding.FragmentMainScreenBinding
import com.example.startzplayassignment.ui.activity.BaseActivity
import com.example.startzplayassignment.ui.component.CarouselView
import com.example.startzplayassignment.ui.detail.SharedViewModel

class MainScreenFragment : Fragment() {

    private var _binding: FragmentMainScreenBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory(RemoteDataSource(NetworkModule.provideApiService()))
    }
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val searchView = binding.searchView
        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { searchMedia(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean = false
        })

        mainViewModel.mediaItems.observe(viewLifecycleOwner) { mediaItems ->
            binding.mainContainer.removeAllViews()
            mediaItems.forEach { (mediaType, items) ->
                if (items.isNotEmpty()) {
                    val carouselView = CarouselView(requireContext()) { mediaItem ->
                        sharedViewModel.selectMediaItem(mediaItem)
                        findNavController().navigate(R.id.action_mainScreenFragment_to_detailScreenFragment)
                    }
                    carouselView.setTitle(mediaType)
                    carouselView.setItems(items)
                    binding.mainContainer.addView(carouselView)
                }
            }
        }

        mainViewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let { showError(it) }
        }

        mainViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) (requireActivity() as BaseActivity).showProgressDialog(requireContext())
            else (requireActivity() as BaseActivity).dismissProgressDialog()
        }
    }

    private fun searchMedia(query: String) {
        mainViewModel.searchMedia(query)
    }

    private fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
