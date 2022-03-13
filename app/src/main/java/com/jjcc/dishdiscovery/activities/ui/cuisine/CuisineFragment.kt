package com.jjcc.dishdiscovery.activities.ui.cuisine

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import aws.sdk.kotlin.runtime.auth.credentials.StaticCredentialsProvider
import aws.sdk.kotlin.services.dynamodb.DynamoDbClient
import com.jjcc.dishdiscovery.R
import com.jjcc.dishdiscovery.activities.Database
import kotlinx.coroutines.runBlocking
import java.util.*

class CuisineFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_cuisine, container, false)

        val saveData = view.findViewById<Button>(R.id.save_cuisine)
        saveData.setOnClickListener {
            submitData(view)
            view.findNavController().popBackStack()
        }

        return view
    }

    fun submitData(view: View) = runBlocking {

        val american: CheckBox = view.findViewById(R.id.cuisineAmerican)
        val chinese: CheckBox = view.findViewById(R.id.cuisineChinese)
        val korean: CheckBox = view.findViewById(R.id.cuisineKorean)
        val japanese: CheckBox = view.findViewById(R.id.cuisineJapanese)
        val turkish: CheckBox = view.findViewById(R.id.cuisineTurkish)
        val thai: CheckBox = view.findViewById(R.id.cuisineThai)
        val italian: CheckBox = view.findViewById(R.id.cuisineItalian)
        val indian: CheckBox = view.findViewById(R.id.cuisineIndian)

        val data = Database()



        val staticCredentials = StaticCredentialsProvider {
            accessKeyId = "AKIA6NAG43PU374JR37G"
            secretAccessKey = "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"
        }

        val ddb = DynamoDbClient {
            region = "us-west-2"
            credentialsProvider = staticCredentials
        }

        val uuid: UUID = UUID.randomUUID()
        val tableName = "Cuisine"
        val key = "id"
        val keyVal = uuid.toString()

        val americanCuisine = "American"
        val americanCuisineVal = american.isChecked
        val chineseCuisine = "Cuisine"
        val chineseCuisineVal = chinese.isChecked
        val koreanCuisine = "Korean"
        val koreanCuisineVal = korean.isChecked
        val japaneseCuisine = "Japanese"
        val japaneseCuisineVal = japanese.isChecked
        val turkishCuisine = "Turkish"
        val turkishCuisineVal = turkish.isChecked
        val thaiCuisine = "Thai"
        val thaiCuisineVal = thai.isChecked
        val italianCuisine = "italian"
        val italianCuisineVal = italian.isChecked
        val indianCuisine = "Indian"
        val indianCuisineVal = indian.isChecked


        data.putItemInTableCuisine(ddb, tableName, key, keyVal, americanCuisine, americanCuisineVal, chineseCuisine,
            chineseCuisineVal, koreanCuisine, koreanCuisineVal, japaneseCuisine, japaneseCuisineVal, turkishCuisine, turkishCuisineVal,
            thaiCuisine, thaiCuisineVal, italianCuisine, italianCuisineVal, indianCuisine, indianCuisineVal)
        showToast("Item added")
    }

    fun showToast(value:String){
        val toast = Toast.makeText(activity, value, Toast.LENGTH_SHORT)
        toast.show()
    }

}