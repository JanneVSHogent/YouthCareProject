package com.example.youthcareproject.favourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.youthcareproject.R
import com.example.youthcareproject.database.YouthCareDatabase
import com.example.youthcareproject.databinding.FragmentFavouritesBinding

class FavouritesFragment : Fragment() {

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {

        val binding: FragmentFavouritesBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_favourites, container, false)

        val application = requireNotNull(this.activity).application


        val dataSource = YouthCareDatabase.getInstance(application).postDao

        val viewmodelFactory = FavouritesViewModelFactory(dataSource, application)

        val favouritesViewModel = ViewModelProvider(this, viewmodelFactory)
            .get(FavouritesViewModel::class.java)

        binding.favouritesViewModel = favouritesViewModel

        binding.lifecycleOwner = this

        val manager = GridLayoutManager(activity, 3)
        binding.favouritesList.layoutManager = manager

        manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int  = when (position) {
                0 -> 3
                else -> 1
            }
        }

        val adapter = FavouritesAdapter()

        binding.favouritesList.adapter = adapter

        favouritesViewModel.posts.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.data = it
            }
        })

        return binding.root
    }
}