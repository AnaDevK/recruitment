package com.app.mediasearchapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.app.mediasearchapp.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment() {
    private lateinit var viewModel: SharedViewModel
    private lateinit var binding: FragmentDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity?)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        val position = viewModel.getPosition()
        viewModel.results.observe(viewLifecycleOwner, { data ->
            if (data != null) {
                binding.txtArtistName.text = "Artist: ${data[position].artistName}"
                binding.txtCollectionName.text =
                    "Collection name: ${data[position].collectionName}"
                binding.txtGenre.text = "Genre: ${data[position].primaryGenreName}"
                binding.txtTrackName.text = "Track name: ${data[position].trackName}"
            }
        })
    }
}