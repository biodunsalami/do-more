package com.example.domore.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.domore.R
import com.example.domore.databinding.FragmentLoginBinding


class LoginFragment : SharedFragment() {

    private lateinit var binding : FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        activityCast().supportActionBar?.hide()

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginArrow.setOnClickListener {
            activityCast().changeActivityFragment(
                LoginFragmentDirections.actionLoginFragmentToHomeFragment())
        }


    }

    override fun onStop() {
        super.onStop()

        activityCast().supportActionBar?.show()
    }

}