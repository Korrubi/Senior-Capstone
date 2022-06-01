package com.jjcc.dishdiscovery.activities.ui.home

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.jjcc.dishdiscovery.R
import com.jjcc.dishdiscovery.activities.spoonacular.Spoonacular
import com.jjcc.dishdiscovery.databinding.FragmentHomeBinding
import com.synnapps.carouselview.CarouselView
import com.synnapps.carouselview.ImageClickListener
import com.synnapps.carouselview.ImageListener
import kotlinx.coroutines.*

class HomeFragment : Fragment() {
    var sampleImages = intArrayOf(
        R.drawable.carousel_1,
        R.drawable.carousel_2,
        R.drawable.carousel_3,
        R.drawable.carousel_4,
        R.drawable.carousel_5
    )

    var sampleBreakfastImages = intArrayOf (
        R.drawable.carousel_bfast_1,
        R.drawable.carousel_bfast_2,
        R.drawable.carousel_bfast_3,
        R.drawable.carousel_bfast_4,
        R.drawable.carousel_bfast_5,
    )

    //Initialize name and email of a given user
//    var userName = ""
//    var userEmail = ""
//    var userSub = ""

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    //make a viewModel
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        val homeViewModel =
//            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //For Carousel, display images according to time
        val carouselView = root.findViewById(R.id.carouselView1) as CarouselView;

        //May 26th- Testing Time-Adjusted Carousel
        Log.i(ContentValues.TAG, "Current Hour: " + viewModel.currentHour.value)
        Log.i(ContentValues.TAG, "Noon Time: " + viewModel.NoonHour.value)

        //Compare if current hour is after Noon (12:00)
        //If true we show some dinner pictures
        if (viewModel.currentHour.value?.isAfter(viewModel.NoonHour.value) == true)
        {
            Log.i(ContentValues.TAG, "Inside If")
            carouselView.setPageCount(sampleImages.size);
            carouselView.setImageListener(imageListener);

            carouselView.setImageClickListener(ImageClickListener { position ->
                Toast.makeText(activity, "Clicked item: $position", Toast.LENGTH_SHORT).show()
                if(carouselView.currentItem == 3){
                    val action = HomeFragmentDirections.actionNavHomeToMealFragment()
                    root.findNavController().navigate(action)
                }
            })
        }
        //Else show some breakfast pictures
        else {
            Log.i(ContentValues.TAG, "Inside Else")
            carouselView.setPageCount(sampleBreakfastImages.size);
            carouselView.setImageListener(imageListener1);

            carouselView.setImageClickListener(ImageClickListener { position ->
                Toast.makeText(activity, "Clicked item: $position", Toast.LENGTH_SHORT).show()
                if(carouselView.currentItem == 3){
                    val action = HomeFragmentDirections.actionNavHomeToMealFragment()
                    root.findNavController().navigate(action)
                }
            })
        }

//        val textView: TextView = binding.textHome
//        homeViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }

        //Old carousel functioning code
//        val carouselView = root.findViewById(R.id.carouselView1) as CarouselView;
//        carouselView.setPageCount(sampleImages.size);
//        carouselView.setImageListener(imageListener);
//
//        carouselView.setImageClickListener(ImageClickListener { position ->
//            Toast.makeText(activity, "Clicked item: $position", Toast.LENGTH_SHORT).show()
//            if(carouselView.currentItem == 3){
//                val action = HomeFragmentDirections.actionNavHomeToMealFragment()
//                root.findNavController().navigate(action)
//            }
//        })

        //April 18th Update -- Try moving things from homeactivity.kt to here

        //Initialize name and email of a given user to hold information for Cognito
//        var userName = ""
//        var userEmail = ""
////        val bundle = Bundle()
//        val intent = Intent()
//
//        //Make cognitoSettings and CognitoUser object to grab info using tokens
//        val cognitoSettings: CognitoSettings = CognitoSettings(this.context)
//        val thisUser : CognitoUser = cognitoSettings.userPool.currentUser
//
//        //callback Handler to perform getAttributes() call from cognitoUser
//        val detailsHandler = object: GetDetailsHandler {
//            override fun onSuccess(cognitoUserDetails: CognitoUserDetails?) {
//                Log.i(ContentValues.TAG, "GetDetailsHandler succeeded!")
//                Log.i(ContentValues.TAG, "Attributes Returned: " + cognitoUserDetails?.attributes?.attributes)
//
//                userName = cognitoUserDetails?.attributes?.attributes?.getValue("name").toString()
//                userEmail = cognitoUserDetails?.attributes?.attributes?.getValue("email").toString()
//                Log.i(ContentValues.TAG, "Username: $userName")
//                Log.i(ContentValues.TAG, "Email: $userEmail")
//            }
//
//            override fun onFailure(exception: Exception?) {
//                Log.i(ContentValues.TAG, "GetDetailsHandler Failed!")
//                Log.i(ContentValues.TAG, "Error: " + exception?.localizedMessage)
//            }
//        }
//
//        CoroutineScope(Dispatchers.IO).launch {
//            val result = async { thisUser.getDetailsInBackground(detailsHandler)}.await()
//            //            delay(1000)
//            Log.i(ContentValues.TAG, "Start of CoroutineScope")
//
//            Log.i(ContentValues.TAG, "userName: $userName")
//            Log.i(ContentValues.TAG, "userEmail: $userEmail")
//
//            intent.putExtra("User Name", userName)
//            intent.putExtra("User Email", userEmail)
//
//            Log.i(ContentValues.TAG, "Scope done")
//        }

//
////        var homeIntent = Intent(this.context)
////        savedInstanceState.putString("User Name", userName)
//
////        activity?.let{
////            val intent = Intent (it, HomeFragment::class.java)
////
////        }

//
//        val fragmentB = ProfileFragment()
//        fragmentB.arguments = bundle

        //Get reference to thai button
        val thaiButton = root.findViewById(R.id.thai) as ImageButton;

        //Set click listener to it
        thaiButton.setOnClickListener {
            Log.i(ContentValues.TAG,"I'm Thai Button");
            var intent = Intent(this.context, Spoonacular::class.java)
            intent.putExtra("cuisine", "thai")
            startActivity(intent)
        }

        //Get reference to chinese button
        val chineseButton = root.findViewById(R.id.chinese) as ImageButton;

        //Set click listener to it
        chineseButton.setOnClickListener {
            Log.i(ContentValues.TAG,"I'm Thai Button");
            var intent = Intent(this.context, Spoonacular::class.java)
            intent.putExtra("cuisine", "chinese")
            startActivity(intent)
        }

        val japaneseButton = root.findViewById(R.id.japanese) as ImageButton;
        japaneseButton.setOnClickListener {
            var intent = Intent(this.context, Spoonacular::class.java)
            intent.putExtra("cuisine", "japanese")
            startActivity(intent)
        }

        val greekButton = root.findViewById(R.id.greek) as ImageButton;
        greekButton.setOnClickListener {
            var intent = Intent(this.context, Spoonacular::class.java)
            intent.putExtra("cuisine", "greek")
            startActivity(intent)
        }

        val americanButton = root.findViewById(R.id.american) as ImageButton;
        americanButton.setOnClickListener {
            var intent = Intent(this.context, Spoonacular::class.java)
            intent.putExtra("cuisine", "american")
            startActivity(intent)
        }

        val mexicanButton = root.findViewById(R.id.mexican) as ImageButton;
        mexicanButton.setOnClickListener {
            var intent = Intent(this.context, Spoonacular::class.java)
            intent.putExtra("cuisine", "mexican")
            startActivity(intent)
        }

        val koreanButton = root.findViewById(R.id.japanese) as ImageButton;
        koreanButton.setOnClickListener {
            var intent = Intent(this.context, Spoonacular::class.java)
            intent.putExtra("cuisine", "korean")
            startActivity(intent)
        }

        val vietButton = root.findViewById(R.id.vietnamese) as ImageButton;
        vietButton.setOnClickListener {
            var intent = Intent(this.context, Spoonacular::class.java)
            intent.putExtra("cuisine", "vietnamese")
            startActivity(intent)
        }

        val africanButton = root.findViewById(R.id.african) as ImageButton;
        africanButton.setOnClickListener {
            var intent = Intent(this.context, Spoonacular::class.java)
            intent.putExtra("cuisine", "african")
            startActivity(intent)
        }

        val britishButton = root.findViewById(R.id.british) as ImageButton;
        britishButton.setOnClickListener {
            var intent = Intent(this.context, Spoonacular::class.java)
            intent.putExtra("cuisine", "british")
            startActivity(intent)
        }

        val cajunButton = root.findViewById(R.id.cajun) as ImageButton;
        cajunButton.setOnClickListener {
            var intent = Intent(this.context, Spoonacular::class.java)
            intent.putExtra("cuisine", "cajun")
            startActivity(intent)
        }

        val caribbeanButton = root.findViewById(R.id.caribbean) as ImageButton;
        caribbeanButton.setOnClickListener {
            var intent = Intent(this.context, Spoonacular::class.java)
            intent.putExtra("cuisine", "caribbean")
            startActivity(intent)
        }

        val europeanButton = root.findViewById(R.id.european) as ImageButton;
        europeanButton.setOnClickListener {
            var intent = Intent(this.context, Spoonacular::class.java)
            intent.putExtra("cuisine", "european")
            startActivity(intent)
        }

        val frenchButton = root.findViewById(R.id.french) as ImageButton;
        frenchButton.setOnClickListener {
            var intent = Intent(this.context, Spoonacular::class.java)
            intent.putExtra("cuisine", "french")
            startActivity(intent)
        }

        val germanButton = root.findViewById(R.id.german) as ImageButton;
        germanButton.setOnClickListener {
            var intent = Intent(this.context, Spoonacular::class.java)
            intent.putExtra("cuisine", "german")
            startActivity(intent)
        }

        val indianButton = root.findViewById(R.id.indian) as ImageButton;
        indianButton.setOnClickListener {
            var intent = Intent(this.context, Spoonacular::class.java)
            intent.putExtra("cuisine", "indian")
            startActivity(intent)
        }

        val irishButton = root.findViewById(R.id.irish) as ImageButton;
        irishButton.setOnClickListener {
            var intent = Intent(this.context, Spoonacular::class.java)
            intent.putExtra("cuisine", "irish")
            startActivity(intent)
        }

        val italianButton = root.findViewById(R.id.italian) as ImageButton;
        italianButton.setOnClickListener {
            var intent = Intent(this.context, Spoonacular::class.java)
            intent.putExtra("cuisine", "italian")
            startActivity(intent)
        }

        val jewishButton = root.findViewById(R.id.jewish) as ImageButton;
        jewishButton.setOnClickListener {
            var intent = Intent(this.context, Spoonacular::class.java)
            intent.putExtra("cuisine", "jewish")
            startActivity(intent)
        }

        val latinButton = root.findViewById(R.id.latin) as ImageButton;
        latinButton.setOnClickListener {
            var intent = Intent(this.context, Spoonacular::class.java)
            intent.putExtra("cuisine", "latin")
            startActivity(intent)
        }

        val mediterraneanButton = root.findViewById(R.id.mediterranean) as ImageButton;
        mediterraneanButton.setOnClickListener {
            var intent = Intent(this.context, Spoonacular::class.java)
            intent.putExtra("cuisine", "mediterranean")
            startActivity(intent)
        }

        val middleEastButton = root.findViewById(R.id.middle_east) as ImageButton;
        middleEastButton.setOnClickListener {
            var intent = Intent(this.context, Spoonacular::class.java)
            intent.putExtra("cuisine", "middle eastern")
            startActivity(intent)
        }

        val nordicButton = root.findViewById(R.id.nordic) as ImageButton;
        nordicButton.setOnClickListener {
            var intent = Intent(this.context, Spoonacular::class.java)
            intent.putExtra("cuisine", "nordic")
            startActivity(intent)
        }

        val southernButton = root.findViewById(R.id.southern) as ImageButton;
        southernButton.setOnClickListener {
            var intent = Intent(this.context, Spoonacular::class.java)
            intent.putExtra("cuisine", "southern")
            startActivity(intent)
        }

        val spanishButton = root.findViewById(R.id.spanish) as ImageButton;
        spanishButton.setOnClickListener {
            var intent = Intent(this.context, Spoonacular::class.java)
            intent.putExtra("cuisine", "spanish")
            startActivity(intent)
        }

        val easternEuropeanButton = root.findViewById(R.id.east_european) as ImageButton;
        easternEuropeanButton.setOnClickListener {
            var intent = Intent(this.context, Spoonacular::class.java)
            intent.putExtra("cuisine", "eastern european")
            startActivity(intent)
        }

        return root
    }

    // Carousel
    var imageListener: ImageListener = object : ImageListener {
        override fun setImageForPosition(position: Int, imageView: ImageView) {
            // You can use Glide or Picasso here
            imageView.setImageResource(sampleImages[position])
        }
    }

    //May 26th - adding a Different Carousel
    var imageListener1: ImageListener = object : ImageListener {
        override fun setImageForPosition(position: Int, imageView: ImageView) {
            // You can use Glide or Picasso here
            imageView.setImageResource(sampleBreakfastImages[position])
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}