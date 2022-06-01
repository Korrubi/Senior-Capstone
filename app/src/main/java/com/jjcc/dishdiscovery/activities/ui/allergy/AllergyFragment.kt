package com.jjcc.dishdiscovery.activities.ui.allergy

import android.content.ContentValues
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.util.Log
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

class AllergyFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_allergy, container, false)


//        Log.i(ContentValues.TAG, "Before Retrieve Data")
//        retrieveData(view)
//        Log.i(ContentValues.TAG, "After Retrieve Data")

        //Query the Table using id (partition key) to see existing data
        Log.i(ContentValues.TAG, "Before Retrieve Data")
        //retrieveData(view)
        Log.i(ContentValues.TAG, "After Retrieve Data")

        val saveData = view.findViewById<Button>(R.id.save_allergy)
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

        val tableName = "Allergy";
        val partitionKeyName = "id";
        val partitionKeyVal = userSub;

        // For more information about an alias, see:
        // https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/Expressions.ExpressionAttributeNames.html
        val partitionAlias = "#a"
//        val count = data.queryDynTable(ddb, tableName, partitionKeyName, partitionKeyVal, partitionAlias)
        data.getSpecificItem(ddb, tableName, partitionKeyName, partitionKeyVal)

//        Log.i(ContentValues.TAG, "Count: " + count)
    }


    //May 27th - Changing fields to match Spoonacular
    private fun submitData(view: View) = runBlocking {

        val dairy: CheckBox = view.findViewById(R.id.dairyCheck)
        val eggs: CheckBox = view.findViewById(R.id.eggsCheck)
        val gluten: CheckBox = view.findViewById(R.id.glutenCheck)
        val grain: CheckBox = view.findViewById(R.id.grainCheck)
        val peanuts: CheckBox = view.findViewById(R.id.peanutCheck)
        val seafood: CheckBox = view.findViewById(R.id.seafoodCheck)
        val sesame: CheckBox = view.findViewById(R.id.sesameCheck)
        val shellfish: CheckBox = view.findViewById(R.id.shellfishCheck)
        val soy: CheckBox = view.findViewById(R.id.soyCheck)
        val sulfite: CheckBox = view.findViewById(R.id.sulfiteCheck)
        val treenuts: CheckBox = view.findViewById(R.id.treenutsCheck)
        val wheat: CheckBox = view.findViewById(R.id.wheatCheck)

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

        // Set values to save in the Amazon DynamoDB table.
        val tableName = "Allergy"
        val key = "id"
        val keyVal = userSub

        //May 17th -- removed the random UUID
        // Old code
        // val uuid: UUID = UUID.randomUUID()
        // val keyVal = uuid.toString()

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
        val glutenAllergy = "Gluten"
        val glutenAllergyVal = gluten.isChecked
        val seafoodAllergy = "Seafood"
        val seafoodAllergyVal = seafood.isChecked
        val grainAllergy = "Grain"
        val grainAllergyVal = grain.isChecked
        val sesameAllergy = "Sesame"
        val sesameAllergyVal = sesame.isChecked
        val sulfiteAllergy = "Sulfite"
        val sulfiteAllergyVal = sulfite.isChecked


        data.putItemInTableAllergy(
            ddb,
            tableName,
            key,
            keyVal,
            peanutAllergy,
            peanutAllergyVal,
            treenutsAllergy,
            treenutsAllergyVal,
            dairyAllergy,
            dairyAllergyVal,
            eggsAllergy,
            eggsAllergyVal,
            shellfishAllergy,
            shellfishAllergyVal,
            wheatAllergy,
            wheatAllergyVal,
            soyAllergy,
            soyAllergyVal,
            glutenAllergy,
            glutenAllergyVal,
            seafoodAllergy,
            seafoodAllergyVal,
            grainAllergy,
            grainAllergyVal,
            sesameAllergy,
            sesameAllergyVal,
            sulfiteAllergy,
            sulfiteAllergyVal
        )
        showToast("Item added")
    }

    private fun showToast(value: String) {
        val toast = Toast.makeText(activity, value, Toast.LENGTH_SHORT)
        toast.show()
    }
}

