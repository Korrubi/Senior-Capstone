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
import androidx.navigation.findNavController
import aws.sdk.kotlin.runtime.auth.credentials.StaticCredentialsProvider
import aws.sdk.kotlin.services.dynamodb.DynamoDbClient
import com.jjcc.dishdiscovery.R
import com.jjcc.dishdiscovery.activities.Database
import kotlinx.coroutines.runBlocking

class CuisineFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_cuisine, container, false)

        //Query the Table using id (partition key) to see existing data
        Log.i(ContentValues.TAG, "Before Retrieve Data")
        retrieveData(view)
        Log.i(ContentValues.TAG, "After Retrieve Data")


        val saveData = view.findViewById<Button>(R.id.save_cuisine)
        saveData.setOnClickListener {
            submitData(view)
            view.findNavController().popBackStack()
        }

        return view
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
        data.getSpecificItem(ddb, tableName, partitionKeyName, partitionKeyVal)

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
        val chineseCuisine = "Cuisine"
        val chineseCuisineVal = chinese.isChecked
        val koreanCuisine = "Korean"
        val koreanCuisineVal = korean.isChecked
        val japaneseCuisine = "Japanese"
        val japaneseCuisineVal = japanese.isChecked
        val thaiCuisine = "Thai"
        val thaiCuisineVal = thai.isChecked
        val italianCuisine = "italian"
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
        val mediterraneanCuisine = "German"
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