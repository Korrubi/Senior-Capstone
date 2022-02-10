package com.jjcc.dishdiscovery.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jjcc.dishdiscovery.R

import aws.sdk.kotlin.runtime.auth.credentials.StaticCredentialsProvider
import aws.sdk.kotlin.services.dynamodb.DynamoDbClient
import kotlinx.coroutines.runBlocking
import android.view.View
import android.widget.*
import java.util.*

class AllergyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_allergy)

        val allergyBackButton = findViewById<ImageButton>(R.id.allergyBackButton)
        allergyBackButton.setOnClickListener{
            val intent = Intent(this, UserProfile::class.java)
            startActivity(intent)
        }
    }

    fun submitData(view: View) = runBlocking{

        val peanuts: CheckBox = findViewById(R.id.peanutCheck)
        val treenuts: CheckBox = findViewById(R.id.treenutsCheck)
        val dairy: CheckBox = findViewById(R.id.dairyCheck)
        val eggs: CheckBox = findViewById(R.id.eggsCheck)
        val shellfish: CheckBox = findViewById(R.id.shellfishCheck)
        val wheat: CheckBox = findViewById(R.id.wheatCheck)
        val soy: CheckBox = findViewById(R.id.soyCheck)
        val fish: CheckBox = findViewById(R.id.fishCheck)

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
        val tableName = "Android"
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

        data.putItemInTable2(ddb, tableName, key, keyVal, peanutAllergy, peanutAllergyVal, treenutsAllergy, treenutsAllergyVal, dairyAllergy, dairyAllergyVal, eggsAllergy, eggsAllergyVal, shellfishAllergy, shellfishAllergyVal, wheatAllergy, wheatAllergyVal, soyAllergy, soyAllergyVal, fishAllergy,fishAllergyVal)
        showToast("Item added")
    }

    fun showToast(value:String){
        val toast = Toast.makeText(applicationContext, value, Toast.LENGTH_SHORT)
        toast.show()
    }
}