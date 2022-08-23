package com.example.youthcareproject.profile

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.youthcareproject.R
import com.example.youthcareproject.database.YouthCareDatabase
import com.example.youthcareproject.databinding.FragmentProfileBinding


class ProfileFragment : Fragment(){
    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentProfileBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_profile, container, false)


        //Set up application & data source
        val application = requireNotNull(this.activity).application
        val dataSource = YouthCareDatabase.getInstance(application).accountDao

        //Get account email
        val prefs = activity?.getSharedPreferences("login", AppCompatActivity.MODE_PRIVATE)

        //Set up ViewModelFactory
        val viewModelFactory = ProfileViewModelFactory(prefs!!.getString("user_email", "")!!, dataSource)

        //Set up DetailsViewModel
        profileViewModel =
            ViewModelProvider(
                this, viewModelFactory)[ProfileViewModel::class.java]

        binding.tvProfileFirstname.text = profileViewModel.account.firstName

        binding.viewModel = profileViewModel

        return binding.root
    }

}