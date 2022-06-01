package com.jjcc.dishdiscovery.activities.ui.diet

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
import com.jjcc.dishdiscovery.databinding.FragmentDietBinding
import kotlinx.coroutines.runBlocking

class DietFragment : Fragment() {

    private var _binding: FragmentDietBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    //make a viewModel
    private val viewModel: DietViewModel by viewModels()

    //May 31st, added a Map Data Structure to hold queried data
    var dietMap = mutableMapOf<String, AttributeValue>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentDietBinding.inflate(inflater, container, false)

        val root: View = binding.root
        // val view: View = inflater.inflate(R.layout.fragment_diet, container, false)

        //Query the Table using id (partition key) to see existing data
        Log.i(ContentValues.TAG, "Before Retrieve Data")
        //retrieveData(root)
        Log.i(ContentValues.TAG, "After Retrieve Data")

        val saveData = root.findViewById<Button>(R.id.save_diet)
        saveData.setOnClickListener {
            submitData(root)
            root.findNavController().popBackStack()
        }

        return root
    }

    //Function use to retrieveData, calls getSpecificItem from Database.kt
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

        val tableName = "Diet";
        val partitionKeyName = "id";
        val partitionKeyVal = userSub;

        // For more information about an alias, see:
        // https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/Expressions.ExpressionAttributeNames.html
        val partitionAlias = "#a"
//        val count = data.queryDynTable(ddb, tableName, partitionKeyName, partitionKeyVal, partitionAlias)
        dietMap = data.getSpecificItem(
            ddb,
            tableName,
            partitionKeyName,
            partitionKeyVal
        ) as MutableMap<String, AttributeValue>

        //Mapping the items retrieved
        dietMap.forEach { key1 ->
            Log.i(ContentValues.TAG, "Key: " + key1 + "Value: " + key1.value.toString())
        }
//        Log.i(ContentValues.TAG, "Count: " + count)
    }

    private fun submitData(view: View) = runBlocking {

        val gluten: CheckBox = view.findViewById(R.id.glutenDiet)
        val keto: CheckBox = view.findViewById(R.id.ketoDiet)
        val vegetarian: CheckBox = view.findViewById(R.id.veggieDiet)
        val vegan: CheckBox = view.findViewById(R.id.veganDiet)
        val pescetarian: CheckBox = view.findViewById(R.id.pescaDiet)
        val lactoVege: CheckBox = view.findViewById(R.id.lactoVegeDiet)
        val ovoVege: CheckBox = view.findViewById(R.id.ovoVegeDiet)
        val paleo: CheckBox = view.findViewById(R.id.paleoDiet)
        val primal: CheckBox = view.findViewById(R.id.primalDiet)
        val lowFodmap: CheckBox = view.findViewById(R.id.fodmapDiet)

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

        val tableName = "Diet"
        val key = "id"
        val keyVal = userSub

        // May 17th -- removed the random UUID
        // val uuid: UUID = UUID.randomUUID()
        // val keyVal = uuid.toString()
        val glutenDiet = "Gluten"
        val glutenDietVal = gluten.isChecked
        val ketoDiet = "Keto"
        val ketoDietVal = keto.isChecked
        val vegetarianDiet = "Vegetarian"
        val vegetarianDietVal = vegetarian.isChecked
        val veganDiet = "Vegan"
        val veganDietVal = vegan.isChecked
        val pescetarianDiet = "Pescetarian"
        val pescetarianDietVal = pescetarian.isChecked
        val lactoVegeDiet = "Lacto-Vegetarian"
        val lactoDietVal = lactoVege.isChecked
        val ovoVegeDiet = "Ovo-Vegetarian"
        val ovoVegeDietVal = ovoVege.isChecked
        val paleoDiet = "Paleo"
        val paleoDietVal = paleo.isChecked
        val primalDiet = "Primal"
        val primalDietVal = primal.isChecked
        val lowFodDiet = "Low FODMAP"
        val lowFodDietVal = lowFodmap.isChecked

        data.putItemInTableDiet(
            ddb,
            tableName,
            key,
            keyVal,
            glutenDiet,
            glutenDietVal,
            ketoDiet,
            ketoDietVal,
            vegetarianDiet,
            vegetarianDietVal,
            veganDiet,
            veganDietVal,
            pescetarianDiet,
            pescetarianDietVal,
            lactoVegeDiet,
            lactoDietVal,
            ovoVegeDiet,
            ovoVegeDietVal,
            paleoDiet,
            paleoDietVal,
            primalDiet,
            primalDietVal,
            lowFodDiet,
            lowFodDietVal
        )

        showToast("Item added")
    }

    fun showToast(value: String) {
        val toast = Toast.makeText(activity, value, Toast.LENGTH_SHORT)
        toast.show()
    }
}