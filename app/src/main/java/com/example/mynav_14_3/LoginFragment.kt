package com.example.mynav_14_3

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.mynav_14_3.databinding.FragmentLoginBinding
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {
    companion object {
        const val TAG = "LoginFragment"
        const val SIGN_IN_RESULT_CODE = 100500
    }

    private val viewModel by viewModels<LoginViewModel>()
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentLoginBinding>(
            inflater, R.layout.fragment_login, container, false
        )
        binding.loginButton.setOnClickListener {launchSignInFlow()  }
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun launchSignInFlow() {
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
        )
        startActivityForResult(
            AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(
                providers
            ).build(), SIGN_IN_RESULT_CODE
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SIGN_IN_RESULT_CODE){
            val responce = IdpResponse.fromResultIntent(data)
            if(resultCode == Activity.RESULT_OK){
                Log.i(TAG, "Successfully ${FirebaseAuth.getInstance().currentUser?.displayName}")
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            navController.popBackStack(R.id.mainFragment,false)
        }
        viewModel.authentificationState.observe(viewLifecycleOwner, Observer { authentificationState ->
            when (authentificationState){
                LoginViewModel.AuthentificationState.AUTHENCICATED ->{
                    navController.popBackStack()
                    navController.navigate(R.id.accountFragment)
                }
                LoginViewModel.AuthentificationState.UNAUTHENCICATED -> Snackbar.make(
                    view, requireActivity().getString(R.string.login_unsuccessful_msg),Snackbar.LENGTH_LONG
                ).show()
                else -> Log.i(TAG, "AuthentificationState false $authentificationState")

            }
        })
    }

}