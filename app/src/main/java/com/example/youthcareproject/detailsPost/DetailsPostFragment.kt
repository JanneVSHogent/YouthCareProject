package com.example.youthcareproject.detailsPost

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.youthcareproject.R
import com.example.youthcareproject.database.YouthCareDatabase
import com.example.youthcareproject.databinding.FragmentDetailsPostBinding

class DetailsPostFragment : Fragment(){

    private lateinit var detailsViewModel: DetailsPostViewModel
    private val args: DetailsPostFragmentArgs by navArgs()
    private lateinit var binding: FragmentDetailsPostBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_details_post, container, false)

        //Set up application & data source
        val application = requireNotNull(this.activity).application
        val dataSource = YouthCareDatabase.getInstance(application).postDao

        //Set up ViewModelFactory
        val viewModelFactory = DetailsPostViewModelFactory(args.postId, dataSource)

        //Set up DetailsViewModel
        detailsViewModel = ViewModelProvider(this, viewModelFactory)[DetailsPostViewModel::class.java]
        binding.lifecycleOwner = this
        binding.viewModel = detailsViewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Bind retrieved Post to View
        detailsViewModel.post.observe(viewLifecycleOwner, Observer { it ->
            if (it != null) {
                //Set text views & stuff
                //binding.tvTitleDetails.text = it.title
            }
        })
    }
}