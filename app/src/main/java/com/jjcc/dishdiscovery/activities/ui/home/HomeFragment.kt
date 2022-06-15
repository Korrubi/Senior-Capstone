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
import aws.sdk.kotlin.runtime.auth.credentials.StaticCredentialsProvider
import aws.sdk.kotlin.services.dynamodb.DynamoDbClient
import aws.sdk.kotlin.services.dynamodb.model.AttributeValue
import com.jjcc.dishdiscovery.R
import com.jjcc.dishdiscovery.activities.Database
import com.jjcc.dishdiscovery.activities.spoonacular.RecipeInformationActivity
import com.jjcc.dishdiscovery.activities.spoonacular.Spoonacular
import com.jjcc.dishdiscovery.activities.spoonacular.SpoonacularRecommend
import com.jjcc.dishdiscovery.databinding.FragmentHomeBinding
import com.synnapps.carouselview.CarouselView
import com.synnapps.carouselview.ImageClickListener
import com.synnapps.carouselview.ImageListener
import kotlinx.coroutines.*
import java.lang.Exception

class HomeFragment : Fragment() {
    var sampleImages = intArrayOf(
        R.drawable.carousel_lunch_burrito,
        R.drawable.carousel_lunch_ramen,
        R.drawable.carousel_lunch_lahmacun,
        R.drawable.carousel_lunch_potato,
        R.drawable.carousel_lunch_burger
    )

    var sampleBreakfastImages = intArrayOf (
        R.drawable.carousel_breakfast_pancakes,
        R.drawable.carousel_breakfast_muffin,
        R.drawable.carousel_breakfast_polenta,
        R.drawable.carousel_breakfast_quesadilla,
        R.drawable.carousel_breakfast_birthday_pancake,
    )

    //Initialize name and email of a given user
//    var userName = ""
//    var userEmail = ""
//    var userSub = ""


    //Map structures to retrieve from Database
    var cuisineMap = mutableMapOf<String, AttributeValue>()
    var dietMap = mutableMapOf<String, AttributeValue>()
    var allergyMap = mutableMapOf<String, AttributeValue>()

    //Bundle to pass to next fragment or activity that needs info
    var bundleExtras = Bundle()


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

        //Grab userSub from previous intent (LoginActivity)
        val intent = activity?.intent;
        var userSub = intent?.getStringExtra("User Sub").toString()

        //For Carousel, display images according to time
        val carouselView = root.findViewById(R.id.carouselView1) as CarouselView;

        //May 26th- Testing Time-Adjusted Carousel
        Log.i(ContentValues.TAG, "Current Hour: " + viewModel.currentHour.value)
        Log.i(ContentValues.TAG, "Noon Time: " + viewModel.noonHour.value)
        Log.i(ContentValues.TAG, "Morning Time: " + viewModel.morningHour.value)

        //Compare if current hour is before Noon (12:00)
        //If true we show breakfast
        if (viewModel.currentHour.value?.isBefore(viewModel.noonHour.value) == true && viewModel.currentHour.value?.isAfter(
                viewModel.morningHour.value
            ) == true
        ) {

            Log.i(ContentValues.TAG, "Inside If")
            carouselView.setPageCount(sampleBreakfastImages.size);
            carouselView.setImageListener(imageListener1);

            carouselView.setImageClickListener(ImageClickListener { position ->
                Toast.makeText(activity, "Clicked item: $position", Toast.LENGTH_SHORT).show()
                if (carouselView.currentItem == 0) {

                    var intent = Intent(this.context, RecipeInformationActivity::class.java)
                    intent.putExtra("id", "1697551")
                    startActivity(intent)

                    /*val action = HomeFragmentDirections.actionNavHomeToMealFragment()
                    root.findNavController().navigate(action)*/
                }
                if (carouselView.currentItem == 1) {

                    var intent = Intent(this.context, RecipeInformationActivity::class.java)
                    intent.putExtra("id", "41365")
                    startActivity(intent)

                    /*val action = HomeFragmentDirections.actionNavHomeToMealFragment()
                    root.findNavController().navigate(action)*/
                }
                if (carouselView.currentItem == 2) {

                    var intent = Intent(this.context, RecipeInformationActivity::class.java)
                    intent.putExtra("id", "83235")
                    startActivity(intent)

                    /*val action = HomeFragmentDirections.actionNavHomeToMealFragment()
                    root.findNavController().navigate(action)*/
                }
                if (carouselView.currentItem == 3) {

                    var intent = Intent(this.context, RecipeInformationActivity::class.java)
                    intent.putExtra("id", "80801")
                    startActivity(intent)

                    /*val action = HomeFragmentDirections.actionNavHomeToMealFragment()
                    root.findNavController().navigate(action)*/
                }
                if (carouselView.currentItem == 4) {

                    var intent = Intent(this.context, RecipeInformationActivity::class.java)
                    intent.putExtra("id", "635001")
                    startActivity(intent)
                }
            })
        }
        //Else show some lunch
        else {
            Log.i(ContentValues.TAG, "Inside Else")
            carouselView.setPageCount(sampleImages.size);
            carouselView.setImageListener(imageListener);

            carouselView.setImageClickListener(ImageClickListener { position ->
                Toast.makeText(activity, "Clicked item: $position", Toast.LENGTH_SHORT)
                    .show()
                if (carouselView.currentItem == 0) {

                    var intent = Intent(this.context, RecipeInformationActivity::class.java)
                    intent.putExtra("id", "795794")
                    startActivity(intent)

                    /*val action = HomeFragmentDirections.actionNavHomeToMealFragment()
                    root.findNavController().navigate(action)*/
                }
                if (carouselView.currentItem == 1) {

                    var intent = Intent(this.context, RecipeInformationActivity::class.java)
                    intent.putExtra("id", "1138672")
                    startActivity(intent)

                    /*val action = HomeFragmentDirections.actionNavHomeToMealFragment()
                    root.findNavController().navigate(action)*/
                }
                if (carouselView.currentItem == 2) {

                    var intent = Intent(this.context, RecipeInformationActivity::class.java)
                    intent.putExtra("id", "1481259")
                    startActivity(intent)

                    /*val action = HomeFragmentDirections.actionNavHomeToMealFragment()
                    root.findNavController().navigate(action)*/
                }
                if (carouselView.currentItem == 3) {

                    var intent = Intent(this.context, RecipeInformationActivity::class.java)
                    intent.putExtra("id", "508918")
                    startActivity(intent)

                    /*val action = HomeFragmentDirections.actionNavHomeToMealFragment()
                    root.findNavController().navigate(action)*/
                }
                if (carouselView.currentItem == 4) {

                    var intent = Intent(this.context, RecipeInformationActivity::class.java)
                    intent.putExtra("id", "751214")
                    startActivity(intent)
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

        //GET request to query the Table using id (partition key) to see existing data
        try {
            Log.i(ContentValues.TAG, "Before Retrieve Data")
            retrieveData(userSub)
            Log.i(ContentValues.TAG, "After Retrieve Data")
        } catch (ex: Exception) {
            Log.i(ContentValues.TAG, "No initial Data: " + ex.message)
        }


        //Get reference to thai button
        val thaiButton = root.findViewById(R.id.thai) as ImageButton;

        //Set click listener to it
        thaiButton.setOnClickListener {
            Log.i(ContentValues.TAG, "I'm Thai Button");
            var intent = Intent(this.context, Spoonacular::class.java)
            intent.putExtra("cuisine", "thai")
            startActivity(intent)
        }

        //Get reference to chinese button
        val chineseButton = root.findViewById(R.id.chinese) as ImageButton;

        //Set click listener to it
        chineseButton.setOnClickListener {
            Log.i(ContentValues.TAG, "I'm Thai Button");
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

        val koreanButton = root.findViewById(R.id.korean) as ImageButton;
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

        //Recommend me button, pass bundles in
        val recommendButton = root.findViewById(R.id.recommend_me) as ImageButton;
        recommendButton.setOnClickListener {
            var intent = Intent(this.context, SpoonacularRecommend::class.java)
            intent.putExtras(bundleExtras)
            startActivity(intent)
        }

        return root
    }

    private fun retrieveData(userSub: String) = runBlocking {

        val data = Database()

        val staticCredentials = StaticCredentialsProvider {
            accessKeyId = "AKIA6NAG43PU374JR37G"
            secretAccessKey = "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"
        }

        val ddb = DynamoDbClient {
            region = "us-west-2"
            credentialsProvider = staticCredentials
        }

        //May 17th - Testing Adding UserSub to identify a user
//        val intent = activity?.intent;
//        var userSub = intent?.getStringExtra("User Sub").toString()

        val tableName = "Cuisine";
        val partitionKeyName = "id";
        val partitionKeyVal = userSub;

        // For more information about an alias, see:
        // https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/Expressions.ExpressionAttributeNames.html
        val partitionAlias = "#a"
//        val count = data.queryDynTable(ddb, tableName, partitionKeyName, partitionKeyVal, partitionAlias)
        cuisineMap = data.getSpecificItem(
            ddb,
            tableName,
            partitionKeyName,
            partitionKeyVal
        ) as MutableMap<String, AttributeValue>


        //Make array lists to hold data
        var cuisineArrayList = arrayListOf<String>()
        var dietArrayList = arrayListOf<String>()
        var allergyArrayList = arrayListOf<String>()

        //Mapping the items retrieved
        cuisineMap.forEach { key1 ->
            if (key1.value.toString().equals("Bool(value=true)")) {
                cuisineArrayList.add(key1.key)
                //Log.i(ContentValues.TAG, "Key: " + key1.key + " Value: " + key1.value)
            }
        }

        val tableName1 = "Diet";
        dietMap = data.getSpecificItem(
            ddb,
            tableName1,
            partitionKeyName,
            partitionKeyVal
        ) as MutableMap<String, AttributeValue>

        //Mapping the items retrieved
        dietMap.forEach { key1 ->
            if (key1.value.toString().equals("Bool(value=true)")) {
                dietArrayList.add(key1.key)
                //Log.i(ContentValues.TAG, "Key: " + key1.key + " Value: " + key1.value)
            }
        }

        val tableName2 = "Allergy"
        allergyMap = data.getSpecificItem(
            ddb,
            tableName2,
            partitionKeyName,
            partitionKeyVal
        ) as MutableMap<String, AttributeValue>

        //Mapping the items retrieved
        allergyMap.forEach { key1 ->
            if (key1.value.toString().equals("Bool(value=true)")) {
                allergyArrayList.add(key1.key)
                //Log.i(ContentValues.TAG, "Key: " + key1.key + " Value: " + key1.value)
            }
        }


        Log.i(ContentValues.TAG, "Cuisine Array List: " + cuisineArrayList.toString())
        Log.i(ContentValues.TAG, "Diet Array List: " + dietArrayList.toString())
        Log.i(ContentValues.TAG, "Allergy Array List: " + allergyArrayList.toString())

        bundleExtras.putSerializable("Cuisine", cuisineArrayList)
        bundleExtras.putSerializable("Diet", dietArrayList)
        bundleExtras.putSerializable("Allergy", allergyArrayList)
//        bundleExtras.putStringArrayList("Cuisine", cuisineArrayList)
//        bundleExtras.putStringArrayList("Diet", dietArrayList)
//        bundleExtras.putStringArrayList("Allergy", allergyArrayList)

        Log.i(ContentValues.TAG, "BUNDLE Cuisine:  " + bundleExtras.getSerializable("Cuisine"))
        Log.i(ContentValues.TAG, "BUNDLE Diet:  " + bundleExtras.getSerializable("Diet"))
        Log.i(ContentValues.TAG, "BUNDLE Allergy:  " + bundleExtras.getSerializable("Allergy"))
//        Log.i(ContentValues.TAG, "BUNDLE Allergy:  " + bundleExtras.getStringArrayList("Allergy"))
//        Log.i(ContentValues.TAG, "BUNDLE Cuisine:  " + bundleExtras.getStringArrayList("Cuisine"))
//        Log.i(ContentValues.TAG, "BUNDLE Diet:  " + bundleExtras.getStringArrayList("Diet"))

//        Log.i(ContentValues.TAG, "Count: " + count)
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