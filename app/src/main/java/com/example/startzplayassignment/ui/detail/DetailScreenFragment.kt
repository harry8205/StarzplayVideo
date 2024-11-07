package com.example.startzplayassignment.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.startzplayassignment.R
import com.example.startzplayassignment.databinding.FragmentDetailScreenBinding
import com.example.startzplayassignment.ui.player.FullScreenVideoActivity
import com.squareup.picasso.Picasso

class DetailScreenFragment : Fragment() {

    private var _binding: FragmentDetailScreenBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailScreenBinding.inflate(inflater, container, false)
        binding.btnPlayMovie.setOnClickListener {
            val intent = Intent(requireContext(), FullScreenVideoActivity::class.java)
            startActivity(intent)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel.selectedMediaItem.observe(viewLifecycleOwner) { mediaItem ->
            binding.mediaTitle.text = mediaItem.displayTitle
            binding.mediaOverview.text = mediaItem.overview
            val imageUrl = if (!mediaItem.posterPath.isNullOrEmpty()) {
                "https://image.tmdb.org/t/p/w500" + mediaItem.posterPath
            } else {
                "https://via.placeholder.com/500x750.png?text=No+Image"
            }
           Picasso.get().load(imageUrl).into(binding.mediaImage)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
