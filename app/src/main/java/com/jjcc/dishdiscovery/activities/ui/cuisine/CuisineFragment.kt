package com.jjcc.dishdiscovery.activities.ui.cuisine

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import aws.sdk.kotlin.runtime.auth.credentials.StaticCredentialsProvider
import aws.sdk.kotlin.services.dynamodb.DynamoDbClient
import aws.sdk.kotlin.services.dynamodb.model.AttributeValue
import com.jjcc.dishdiscovery.R
import com.jjcc.dishdiscovery.activities.Database
import com.jjcc.dishdiscovery.activities.ui.diet.DietViewModel
import com.jjcc.dishdiscovery.databinding.FragmentCuisineBinding
import kotlinx.coroutines.runBlocking
import java.lang.Exception

class CuisineFragment : Fragment() {

    private var _binding: FragmentCuisineBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    //make a viewModel
    private val viewModel: CuisineViewModel by viewModels()

    //May 31st, added a Map Data Structure to hold queried data
    var cuisineMap = mutableMapOf<String, AttributeValue>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.fragment_cuisine, container, false)

        //GET request to query the Table using id (partition key) to see existing data
        try
        {
            Log.i(ContentValues.TAG, "Before Retrieve Data")
            retrieveData(view)
            Log.i(ContentValues.TAG, "After Retrieve Data")
        }
        catch (ex: Exception)
        {
            Log.i(ContentValues.TAG, "No initial Data: " + ex.message)
        }

        //Call method below to setCheckBoxes
        setCheckBox(view);

        //PUT Request to save Data to DynamoDB
        val saveData = view.findViewById<Button>(R.id.save_cuisine)
        saveData.setOnClickListener {
            submitData(view)
            view.findNavController().popBackStack()
        }

        return view
    }

    private fun setCheckBox(view: View) = runBlocking {
        cuisineMap.forEach { key1 ->

            //checks for true values only
            //key1.key can give just key names, like Vegetarian, Vegan etc.
            //key1.value will give Bool(value=true) or Bool(value=false)
            if (key1.value.toString().equals("Bool(value=true)"))
            {
                Log.i(ContentValues.TAG, "Key: " + key1.key + " Value: " + key1.value)
                if (key1.key.equals("Eastern European")) {
                    val easternEuropeanBox: CheckBox = view.findViewById(R.id.cuisineEasternEuropean)
                    easternEuropeanBox.isChecked = true;
                }
                if (key1.key.equals("Southern")) {
                    val southernBox: CheckBox = view.findViewById(R.id.cuisineSouthern)
                    southernBox.isChecked = true;
                }
                if (key1.key.equals("Chinese")) {
                    val chineseBox: CheckBox = view.findViewById(R.id.cuisineChinese)
                    chineseBox.isChecked = true;
                }
                if (key1.key.equals("American")) {
                    val americanBox: CheckBox = view.findViewById(R.id.cuisineAmerican)
                    americanBox.isChecked = true;
                }
                if (key1.key.equals("Thai")) {
                    val thaiBox: CheckBox = view.findViewById(R.id.cuisineThai)
                    thaiBox.isChecked = true;
                }
                if (key1.key.equals("Indian")) {
                    val indianBox: CheckBox = view.findViewById(R.id.cuisineIndian)
                    indianBox.isChecked = true;
                }
                if (key1.key.equals("Latin")) {
                    val latinBox: CheckBox = view.findViewById(R.id.cuisineLatin)
                    latinBox.isChecked = true;
                }
                if (key1.key.equals("Japanese")) {
                    val japaneseBox: CheckBox = view.findViewById(R.id.cuisineJapanese)
                    japaneseBox.isChecked = true;
                }
                if (key1.key.equals("Italian")) {
                    val italianBox: CheckBox = view.findViewById(R.id.cuisineItalian)
                    italianBox.isChecked = true;
                }
                if (key1.key.equals("Korean")) {
                    val koreanBox: CheckBox = view.findViewById(R.id.cuisineKorean)
                    koreanBox.isChecked = true;
                }
                if (key1.key.equals("Caribbean")) {
                    val caribbeanBox: CheckBox = view.findViewById(R.id.cuisineCaribbean)
                    caribbeanBox.isChecked = true;
                }
                if (key1.key.equals("Irish")) {
                    val irishBox: CheckBox = view.findViewById(R.id.cuisineIrish)
                    irishBox.isChecked = true;
                }
                if (key1.key.equals("Spanish")) {
                    val spanishBox: CheckBox = view.findViewById(R.id.cuisineSpanish)
                    spanishBox.isChecked = true;
                }
                if (key1.key.equals("British")) {
                    val britishBox: CheckBox = view.findViewById(R.id.cuisineBritish)
                    britishBox.isChecked = true;
                }
                if (key1.key.equals("Greek")) {
                    val greekBox: CheckBox = view.findViewById(R.id.cuisineGreek)
                    greekBox.isChecked = true;
                }
                if (key1.key.equals("Mediterranean")) {
                    val mediterraneanBox: CheckBox = view.findViewById(R.id.cuisineMediterranean)
                    mediterraneanBox.isChecked = true;
                }
                if (key1.key.equals("Middle East")) {
                    val middleEastBox: CheckBox = view.findViewById(R.id.cuisineMiddleEast)
                    middleEastBox.isChecked = true;
                }
                if (key1.key.equals("African")) {
                    val africanBox: CheckBox = view.findViewById(R.id.cuisineAfrican)
                    africanBox.isChecked = true;
                }
                if (key1.key.equals("Jewish")) {
                    val jewishBox: CheckBox = view.findViewById(R.id.cuisineJewish)
                    jewishBox.isChecked = true;
                }
                if (key1.key.equals("German")) {
                    val germanBox: CheckBox = view.findViewById(R.id.cuisineGerman)
                    germanBox.isChecked = true;
                }
                if (key1.key.equals("French")) {
                    val frenchBox: CheckBox = view.findViewById(R.id.cuisineFrench)
                    frenchBox.isChecked = true;
                }
                if (key1.key.equals("Cajun")) {
                    val cajunBox: CheckBox = view.findViewById(R.id.cuisineCajun)
                    cajunBox.isChecked = true;
                }
                if (key1.key.equals("European")) {
                    val europeanBox: CheckBox = view.findViewById(R.id.cuisineEuropean)
                    europeanBox.isChecked = true;
                }
                if (key1.key.equals("Nordic")) {
                    val nordicBox: CheckBox = view.findViewById(R.id.cuisineNordic)
                    nordicBox.isChecked = true;
                }

            }
        }
    }

    private fun retrieveData(view: View) = runBlocking {

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
        val intent = activity?.intent;
        var userSub = intent?.getStringExtra("User Sub").toString()

        val tableName = "Cuisine";
        val partitionKeyName = "id";
        val partitionKeyVal = userSub;

        // For more information about an alias, see:
        // https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/Expressions.ExpressionAttributeNames.html
        val partitionAlias = "#a"
//        val count = data.queryDynTable(ddb, tableName, partitionKeyName, partitionKeyVal, partitionAlias)
        cuisineMap = data.getSpecificItem(ddb, tableName, partitionKeyName, partitionKeyVal) as MutableMap<String, AttributeValue>

        //Mapping the items retrieved
//        cuisineMap.forEach { key1 ->
//            Log.i(ContentValues.TAG, "Key: " + key1 + " Value: " + key1.value.toString())
//        }

//        Log.i(ContentValues.TAG, "Count: " + count)
    }

    private fun submitData(view: View) = runBlocking {

        val american: CheckBox = view.findViewById(R.id.cuisineAmerican)
        val chinese: CheckBox = view.findViewById(R.id.cuisineChinese)
        val korean: CheckBox = view.findViewById(R.id.cuisineKorean)
        val japanese: CheckBox = view.findViewById(R.id.cuisineJapanese)
        val thai: CheckBox = view.findViewById(R.id.cuisineThai)
        val italian: CheckBox = view.findViewById(R.id.cuisineItalian)
        val indian: CheckBox = view.findViewById(R.id.cuisineIndian)
        val african: CheckBox = view.findViewById(R.id.cuisineAfrican)
        val british: CheckBox = view.findViewById(R.id.cuisineBritish)
        val cajun: CheckBox = view.findViewById(R.id.cuisineCajun)
        val caribbean: CheckBox = view.findViewById(R.id.cuisineCaribbean)
        val eastEuropean: CheckBox = view.findViewById(R.id.cuisineEasternEuropean)
        val european: CheckBox = view.findViewById(R.id.cuisineEuropean)
        val french: CheckBox = view.findViewById(R.id.cuisineFrench)
        val german: CheckBox = view.findViewById(R.id.cuisineGerman)
        val greek: CheckBox = view.findViewById(R.id.cuisineGreek)
        val irish: CheckBox = view.findViewById(R.id.cuisineIrish)
        val jewish: CheckBox = view.findViewById(R.id.cuisineJewish)
        val latin: CheckBox = view.findViewById(R.id.cuisineLatin)
        val mediterranean: CheckBox = view.findViewById(R.id.cuisineMediterranean)
        val middleEast: CheckBox = view.findViewById(R.id.cuisineMiddleEast)
        val nordic: CheckBox = view.findViewById(R.id.cuisineNordic)
        val southern: CheckBox = view.findViewById(R.id.cuisineSouthern)
        val spanish: CheckBox = view.findViewById(R.id.cuisineSpanish)

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
        val intent = activity?.intent;
        var userSub = intent?.getStringExtra("User Sub").toString()

        val tableName = "Cuisine"
        val key = "id"
        val keyVal = userSub

        // May 17th -- removed the random UUID
        // val uuid: UUID = UUID.randomUUID()
        // val keyVal = uuid.toString()

        val americanCuisine = "American"
        val americanCuisineVal = american.isChecked
        val chineseCuisine = "Chinese"
        val chineseCuisineVal = chinese.isChecked
        val koreanCuisine = "Korean"
        val koreanCuisineVal = korean.isChecked
        val japaneseCuisine = "Japanese"
        val japaneseCuisineVal = japanese.isChecked
        val thaiCuisine = "Thai"
        val thaiCuisineVal = thai.isChecked
        val italianCuisine = "Italian"
        val italianCuisineVal = italian.isChecked
        val indianCuisine = "Indian"
        val indianCuisineVal = indian.isChecked
        val africanCuisine = "African"
        val africanVal = african.isChecked
        val britishCuisine = "British"
        val britishCuisineVal = british.isChecked
        val cajunCuisine = "Cajun"
        val cajunCuisineVal = cajun.isChecked
        val caribbeanCuisine = "Caribbean"
        val caribbeanCuisineVal = caribbean.isChecked
        val eastEuropeanCuisine = "Eastern European"
        val eastEuropeanCuisineVal = eastEuropean.isChecked
        val europeanCuisine = "European"
        val europeanVal = european.isChecked
        val frenchCuisine = "French"
        val frenchVal = french.isChecked
        val irishCuisine = "Irish"
        val irishVal = irish.isChecked
        val germanCuisine = "German"
        val germanVal = german.isChecked
        val greekCuisine = "Greek"
        val greekVal = greek.isChecked
        val jewishCuisine = "Jewish"
        val jewishVal = jewish.isChecked
        val latinCuisine = "Latin"
        val latinVal = latin.isChecked
        val mediterraneanCuisine = "Mediterranean"
        val mediterraneanVal = mediterranean.isChecked
        val middleEastCuisine = "Middle East"
        val middleEastVal = middleEast.isChecked
        val nordicCuisine = "Nordic"
        val nordicVal = nordic.isChecked
        val southernCuisine = "Southern"
        val southernVal = southern.isChecked
        val spanishCuisine = "Spanish"
        val spanishVal = spanish.isChecked

        data.putItemInTableCuisine(
            ddb,
            tableName,
            key,
            keyVal,
            americanCuisine,
            americanCuisineVal,
            chineseCuisine,
            chineseCuisineVal,
            koreanCuisine,
            koreanCuisineVal,
            japaneseCuisine,
            japaneseCuisineVal,
            thaiCuisine,
            thaiCuisineVal,
            italianCuisine,
            italianCuisineVal,
            indianCuisine,
            indianCuisineVal,
            africanCuisine,
            africanVal,
            britishCuisine,
            britishCuisineVal,
            cajunCuisine,
            cajunCuisineVal,
            caribbeanCuisine,
            caribbeanCuisineVal,
            eastEuropeanCuisine,
            eastEuropeanCuisineVal,
            europeanCuisine,
            europeanVal,
            frenchCuisine,
            frenchVal,
            irishCuisine,
            irishVal,
            germanCuisine,
            germanVal,
            greekCuisine,
            greekVal,
            jewishCuisine,
            jewishVal,
            latinCuisine,
            latinVal,
            mediterraneanCuisine,
            mediterraneanVal,
            middleEastCuisine,
            middleEastVal,
            nordicCuisine,
            nordicVal,
            southernCuisine,
            southernVal,
            spanishCuisine,
            spanishVal,
        )
        showToast("Item added")
    }

    fun showToast(value: String) {
        val toast = Toast.makeText(activity, value, Toast.LENGTH_SHORT)
        toast.show()
    }

}