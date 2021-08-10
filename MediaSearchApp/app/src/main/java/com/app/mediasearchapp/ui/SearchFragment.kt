package com.app.mediasearchapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.mediasearchapp.R
import com.app.mediasearchapp.databinding.FragmentSearchBinding
import com.app.mediasearchapp.model.Result
import com.app.mediasearchapp.utils.ListAdapter

class SearchFragment : Fragment() {

    private lateinit var adapter: ListAdapter
    private lateinit var viewModel: SharedViewModel
    private lateinit var binding: FragmentSearchBinding
    private val resultsMedia = mutableListOf<Result>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity?)?.supportActionBar?.setDisplayHomeAsUpEnabled(false)

        binding = FragmentSearchBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        val action = SearchFragmentDirections.actionSearchFragmentToDetailsFragment()

        //Create media types list
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.mediaType,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            //Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            //Apply the adapter to the spinner
            binding.spType.adapter = adapter
        }

        //Create adapter for RV
        adapter = ListAdapter(
            requireContext(),
            resultsMedia,
            object : ListAdapter.OnClickListener {
                //When user taps on a view in RV, navigate to link
                override fun onItemClick(position: Int) {
                    viewModel.setPosition(position)
                    findNavController().navigate(action)
                }
            })

        //Populate RV
        binding.rvList.adapter = adapter
        binding.rvList.setHasFixedSize(true)
        binding.rvList.layoutManager =
            LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )

        //Observe the results to update RV
        viewModel.results.observe(viewLifecycleOwner, { data ->
            resultsMedia.clear()
            resultsMedia.addAll(data)
            adapter.notifyDataSetChanged()
        })

        binding.btnSearch.setOnClickListener {
            clearRecyclerData()
            val nameSearch = binding.srcMedia.getQuery().toString()
            val spinnerValue = binding.spType.selectedItem.toString()
            //Set media results, get observed and rv refreshed automatically
            viewModel.getMediaApiCall(nameSearch, spinnerValue)
        }

        viewModel.resultsSize.observe(viewLifecycleOwner, { count ->
            if (count == 0) {
                Toast.makeText(requireContext(), "No results found", Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.error.observe(viewLifecycleOwner, { err ->
            if (err) {
                Toast.makeText(
                    requireContext(),
                    "There was an error receiving data",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

        return binding.root
    }

    private fun clearRecyclerData() {
        resultsMedia.clear()
        adapter.notifyDataSetChanged()
    }
}