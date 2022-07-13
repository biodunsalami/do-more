package com.example.domore.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.domore.R
import com.example.domore.databinding.FragmentSignUpBinding


class SignUpFragment : SharedFragment() {

    private lateinit var binding: FragmentSignUpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(inflater, container, false)

        activityCast().supportActionBar?.hide()

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signInArrow.setOnClickListener {
            activityCast().changeActivityFragment(
                SignUpFragmentDirections.actionSignUpFragmentToLoginFragment())
        }

        binding.loginTextView.setOnClickListener {
            activityCast().changeActivityFragment(
                SignUpFragmentDirections.actionSignUpFragmentToLoginFragment())
        }

    }

    override fun onStop() {
        super.onStop()

        activityCast().supportActionBar?.show()
    }


}