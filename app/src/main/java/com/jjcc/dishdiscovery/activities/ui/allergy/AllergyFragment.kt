package com.jjcc.dishdiscovery.activities.ui.allergy

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

class AllergyFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_allergy, container, false)

        val saveData = view.findViewById<Button>(R.id.save_allergy)
        saveData.setOnClickListener {
            submitData(view)
            view.findNavController().popBackStack()
        }

        return view
    }

    private fun submitData(view: View) = runBlocking{

        val peanuts: CheckBox = view.findViewById(R.id.peanutCheck)
        val treenuts: CheckBox = view.findViewById(R.id.treenutsCheck)
        val dairy: CheckBox = view.findViewById(R.id.dairyCheck)
        val eggs: CheckBox = view.findViewById(R.id.eggsCheck)
        val shellfish: CheckBox = view.findViewById(R.id.shellfishCheck)
        val wheat: CheckBox = view.findViewById(R.id.wheatCheck)
        val soy: CheckBox = view.findViewById(R.id.soyCheck)
        val fish: CheckBox = view.findViewById(R.id.fishCheck)

        val data = Database()

        val staticCredentials = StaticCredentialsProvider {
            accessKeyId = "AKIA6NAG43PU374JR37G"
            secretAccessKey = "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"
        }

        val ddb = DynamoDbClient{
            region = "us-west-2"
            credentialsProvider = staticCredentials
        }

        // Set values to save in the Amazon DynamoDB table.
        val uuid: UUID = UUID.randomUUID()
        val tableName = "Allergy"
        val key = "id"
        val keyVal = uuid.toString()

        val peanutAllergy = "Peanuts"
        val peanutAllergyVal = peanuts.isChecked
        val treenutsAllergy = "Tree Nuts"
        val treenutsAllergyVal = treenuts.isChecked
        val dairyAllergy = "Dairy"
        val dairyAllergyVal = dairy.isChecked
        val eggsAllergy = "Eggs"
        val eggsAllergyVal = eggs.isChecked
        val shellfishAllergy = "Shellfish"
        val shellfishAllergyVal = shellfish.isChecked
        val wheatAllergy = "Wheat"
        val wheatAllergyVal = wheat.isChecked
        val soyAllergy = "Soy"
        val soyAllergyVal = soy.isChecked
        val fishAllergy = "Fish"
        val fishAllergyVal = fish.isChecked

        data.putItemInTableAllergy(ddb, tableName, key, keyVal, peanutAllergy, peanutAllergyVal, treenutsAllergy, treenutsAllergyVal, dairyAllergy, dairyAllergyVal, eggsAllergy, eggsAllergyVal, shellfishAllergy, shellfishAllergyVal, wheatAllergy, wheatAllergyVal, soyAllergy, soyAllergyVal, fishAllergy,fishAllergyVal)
        showToast("Item added")
    }

    private fun showToast(value:String){
        val toast = Toast.makeText(activity, value, Toast.LENGTH_SHORT)
        toast.show()
    }

}