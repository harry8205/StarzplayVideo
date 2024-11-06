package com.example.startzplayassignment.ui.main


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.startzplayassignment.data.network.NetworkModule
import com.example.startzplayassignment.data.network.RemoteDataSource
import com.example.startzplayassignment.databinding.FragmentMainScreenBinding
import com.example.startzplayassignment.ui.component.CarouselView
import kotlinx.coroutines.launch

class MainScreenFragment : Fragment() {

    private var _binding: FragmentMainScreenBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory(RemoteDataSource(NetworkModule.provideApiService()))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Observe data and display carousels
        mainViewModel.mediaItems.observe(viewLifecycleOwner) { mediaItems ->
            mediaItems.forEach { (mediaType, items) ->
                //Toast.makeText(requireContext(), "success", Toast.LENGTH_SHORT).show()

                val carouselView = CarouselView(requireContext())
                carouselView.setTitle(mediaType)
                carouselView.setItems(items)
                binding.mainContainer.addView(carouselView)
            }
        }


        mainViewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                showError(it)
            }

        }

        mainViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        searchMedia("example")  // Example call to search
    }

    private fun searchMedia(query: String) {
        mainViewModel.searchMedia(query)
    }

    private fun showError(message: String) {
        // Show error message to user
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
