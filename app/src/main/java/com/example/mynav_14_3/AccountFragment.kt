package com.example.mynav_14_3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mynav_14_3.databinding.FragmentAccountBinding
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth

class AccountFragment : Fragment() {
    private val viewModel by viewModels<LoginViewModel>()
    private lateinit var binding: FragmentAccountBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_account, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeAuthenticationState()
    }

    private fun observeAuthenticationState(){
        viewModel.authentificationState.observe(viewLifecycleOwner, {authenticateState ->
            if (authenticateState == LoginViewModel.AuthentificationState.AUTHENCICATED){
                val hello = String.format(resources.getString(R.string.welcome_message),
                FirebaseAuth.getInstance().currentUser?.displayName)

                binding.tvLoginPromt.text = hello
                binding.loginButton.setOnClickListener{
                    AuthUI.getInstance().signOut(requireContext())
                    findNavController().popBackStack()
                }
            }
        })
    }
}