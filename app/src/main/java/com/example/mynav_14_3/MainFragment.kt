package com.example.mynav_14_3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mynav_14_3.databinding.FragmentMainBinding


class MainFragment : Fragment() {
    private val viewModel by viewModels<LoginViewModel>()
    private lateinit var  binding: FragmentMainBinding
    //

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_main, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeAuthentificationState()
    }

    private fun observeAuthentificationState(){
        viewModel.authentificationState.observe(viewLifecycleOwner,{ authentificationState ->
            when(authentificationState){
                LoginViewModel.AuthentificationState.AUTHENCICATED ->{
                    binding.button.setOnClickListener {
                        findNavController().navigate(R.id.accountFragment)
                    }
                }else -> {
                    binding.button.setOnClickListener {
                        findNavController().navigate(R.id.loginFragment)
                    }
                }
            }
        })
    }


}