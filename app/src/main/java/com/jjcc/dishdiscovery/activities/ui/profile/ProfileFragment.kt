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
import androidx.fragment.app.viewModels
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

    //make a viewModel
    private val viewModel: ProfileViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        val galleryViewModel =
//            ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)


        val root: View = binding.root

        //Testing viewModel interaction
        Log.i(ContentValues.TAG, "Test String1: " + viewModel.textBlabla.value)
        Log.i(ContentValues.TAG, "Test String2: " + viewModel.userSub.value)
//        Log.i(ContentValues.TAG, "Current Time: " + viewModel.currentTime.value)


//        val textView: TextView = binding.textGallery
//        galleryViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }

        //Grab currentUser's Name and Email passed from Intent (PROPS)
        val intent = activity?.intent;
        var userName = intent?.getStringExtra("User Name").toString()
        var userEmail = intent?.getStringExtra("User Email").toString()

        //Testing May 18th - add userSub
        var userSub = intent?.getStringExtra("User Sub").toString()


        val passwordButton = root.findViewById<Button>(R.id.changePW)
        passwordButton.setOnClickListener{
            val action = ProfileFragmentDirections.actionNavProfileToPasswordFragment()
            root.findNavController().navigate(action)
        }

        val dietButton = root.findViewById<Button>(R.id.changeDiet)
        dietButton.setOnClickListener{
            val action = ProfileFragmentDirections.actionNavProfileToDietFragment()
            intent?.putExtra("User Sub", userSub)
            root.findNavController().navigate(action)
        }

        val cuisineButton = root.findViewById<Button>(R.id.changeCuisine)
        cuisineButton.setOnClickListener{
            val action = ProfileFragmentDirections.actionNavProfileToCuisineFragment()
            intent?.putExtra("User Sub", userSub)
            root.findNavController().navigate(action)
        }

        val allergyButton = root.findViewById<Button>(R.id.changeAllergy)
        allergyButton.setOnClickListener{
            val action = ProfileFragmentDirections.actionNavProfileToAllergyActivity()
            intent?.putExtra("User Sub", userSub)
            root.findNavController().navigate(action)
        }


        //Retrieve data from bundle
//        val intent = activity?.intent.getBundleExtra("User Name").toString()

//        val bundle = this.arguments
//            var userName = bundle?.getString("User Name").toString()
//        var userName1 = bundle?.getString("User Name")
//        Log.i(ContentValues.TAG, "Profile User: $userName1")
//            var userEmail = bundle?.getString("User Email").toString()
//
////        val intent = activity?.intent;
////        var userName = intent?.getStringExtra("User Name").toString()
////        var userEmail = intent?.getStringExtra("User Email").toString()
//
//        Log.i(ContentValues.TAG, "Profile: $userName")
//        Log.i(ContentValues.TAG, "Profile: $userEmail")
//
//        var userNameText = root.findViewById<TextView>(R.id.updatedUserName)
//        userNameText.text = userName
//
//        var emailText = root.findViewById<TextView>(R.id.updatedEmail)
//        emailText.text = userEmail

        var userNameText = root.findViewById<TextView>(R.id.updatedUserName)
        userNameText.text = userName

        var emailText = root.findViewById<TextView>(R.id.updatedEmail)
        emailText.text = userEmail

        Log.i(ContentValues.TAG, "userSub: $userSub")

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