package com.jjcc.dishdiscovery.activities.ui.profile

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser
import com.jjcc.dishdiscovery.R
import com.jjcc.dishdiscovery.activities.*
import com.jjcc.dishdiscovery.databinding.FragmentProfileBinding

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val galleryViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textGallery
//        galleryViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }

        val passwordButton = root.findViewById<Button>(R.id.changePW)
        passwordButton.setOnClickListener{
            val action = ProfileFragmentDirections.actionNavProfileToPasswordFragment()
            root.findNavController().navigate(action)
        }

        val dietButton = root.findViewById<Button>(R.id.changeDiet)
        dietButton.setOnClickListener{
            val action = ProfileFragmentDirections.actionNavProfileToDietFragment()
            root.findNavController().navigate(action)
        }

        val cuisineButton = root.findViewById<Button>(R.id.changeCuisine)
        cuisineButton.setOnClickListener{
            val action = ProfileFragmentDirections.actionNavProfileToCuisineFragment()
            root.findNavController().navigate(action)
        }

        val allergyButton = root.findViewById<Button>(R.id.changeAllergy)
        allergyButton.setOnClickListener{
            val action = ProfileFragmentDirections.actionNavProfileToAllergyActivity()
            root.findNavController().navigate(action)
        }

        //Load intent (PROPS) from previous Activity
        val intent = activity?.intent;
        val accessToken:String = intent?.getStringExtra("Access Token").toString()

        //Sanity check if token matches from before..
        Log.i(ContentValues.TAG, "Access Token in UserProfile: $accessToken")

        //Grab currentUser's Name and Email passed from Intent (PROPS)
        var userName = intent?.getStringExtra("User Name").toString()
        var userEmail = intent?.getStringExtra("User Email").toString()

        var userNameText = root.findViewById<TextView>(R.id.updatedUserName)
        userNameText.setText(userName)

        var emailText = root.findViewById<TextView>(R.id.updatedEmail)
        emailText.setText(userEmail)


        //Grab current user to enable logout button
        val cognitoSettings: CognitoSettings = CognitoSettings(activity)
        val thisUser : CognitoUser = cognitoSettings.userPool.currentUser

        //Attach cognitoUser to logoutButton
        val logoutButton = root.findViewById<Button>(R.id.logout)
        logoutButton.setOnClickListener{
            Log.i(ContentValues.TAG, "Signing Out...")

            thisUser.signOut()
            Toast.makeText(activity, "Logging Out.. Redirecting", Toast.LENGTH_SHORT).show()
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}