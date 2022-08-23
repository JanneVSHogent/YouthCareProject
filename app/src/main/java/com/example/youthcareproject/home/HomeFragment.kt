package com.example.youthcareproject.home

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.youthcareproject.LoginActivity
import com.example.youthcareproject.MainActivity
import com.example.youthcareproject.R
import com.example.youthcareproject.database.YouthCareDatabase
import com.example.youthcareproject.databinding.FragmentHomeBinding
import kotlinx.android.synthetic.main.fragment_settings.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var postAdapter: PostAdapter? = null

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?,
                               savedInstanceState: Bundle?): View {

        val binding: FragmentHomeBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_home, container, false)

        //Set up application & data source
        val application = requireNotNull(this.activity).application
        val dataSource = YouthCareDatabase.getInstance(application).postDao

        //Set up ViewModelFactory & ViewModel
        val viewModelFactory = HomeViewModelFactory(dataSource, application)
        homeViewModel = ViewModelProvider(this, viewModelFactory)[HomeViewModel::class.java]
        binding.homeViewModel = homeViewModel
        binding.lifecycleOwner = this

        //GridLayout for RecyclerView
        val manager = LinearLayoutManager(activity)
        binding.postsList.layoutManager = manager

        //Set up HomeAdapter
        postAdapter = PostAdapter(PostListener { postId ->
            homeViewModel.onPostClicked(postId)
        })
        binding.postsList.adapter = postAdapter

        //Add Extra Options to menu
        addMenuOptions()

        //return
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Observe navigateToPostDetails for navigation to DetailPostFragment
        homeViewModel.navigateToPostDetails.observe(viewLifecycleOwner, Observer { post ->
            post?.let {
                var selectedPost = homeViewModel.posts.value?.filter {
                    it.postId == post
                }
                this.findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToDetailsPostFragment(
                        post,
                    )
                )
                homeViewModel.onPostDetailsNavigated()
            }
        })

        homeViewModel.posts.observe(viewLifecycleOwner, Observer { posts ->
            posts?.apply {
                postAdapter?.submitList(posts)
            }
        })
    }

    private fun addMenuOptions(){
        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object: MenuProvider {

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.toolbar_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.settingsFragment -> {
                        findNavController().navigate(R.id.navigation_settings)
                        true
                    }
                    R.id.logout -> {
                        (activity as MainActivity?)?.logout()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
}