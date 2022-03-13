package com.jjcc.dishdiscovery.activities.ui.diet

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

class DietFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_diet, container, false)

        val saveData = view.findViewById<Button>(R.id.save_diet)
        saveData.setOnClickListener {
            submitData(view)
            view.findNavController().popBackStack()
        }

        return view
    }

    fun submitData(view: View) = runBlocking {

        val halal: CheckBox = view.findViewById(R.id.halalDiet)
        val keto: CheckBox = view.findViewById(R.id.ketoDiet)
        val kosher: CheckBox = view.findViewById(R.id.kosherDiet)
        val vegetarian: CheckBox = view.findViewById(R.id.veggieDiet)
        val vegan: CheckBox = view.findViewById(R.id.veganDiet)
        val pescetarian: CheckBox = view.findViewById(R.id.pescaDiet)
        val lowcarb: CheckBox = view.findViewById(R.id.lowcarbDiet)

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
        val tableName = "Diet"
        val key = "id"

        val keyVal = uuid.toString()

        val halalDiet = "Halal"
        val halalDietVal = halal.isChecked
        val ketoDiet = "Keto"
        val ketoDietVal = keto.isChecked
        val kosherDiet = "kosher"
        val kosherDietVal = kosher.isChecked
        val vegetarianDiet = "Vegetarian"
        val vegetarianDietVal = vegetarian.isChecked
        val veganDiet = "Vegan"
        val veganDietVal = vegan.isChecked
        val pescetarianDiet = "Pescetarian"
        val pescetarianDietVal = pescetarian.isChecked
        val lowcarbDiet = "Low-Carb"
        val lowcarbDietVal = lowcarb.isChecked

        data.putItemInTableDiet(ddb, tableName, key, keyVal, halalDiet, halalDietVal, ketoDiet,
            ketoDietVal, kosherDiet, kosherDietVal, vegetarianDiet, vegetarianDietVal, veganDiet,
            veganDietVal, pescetarianDiet, pescetarianDietVal, lowcarbDiet, lowcarbDietVal)

        showToast("Item added")
    }

    fun showToast(value:String){
        val toast = Toast.makeText(activity, value, Toast.LENGTH_SHORT)
        toast.show()
    }
}