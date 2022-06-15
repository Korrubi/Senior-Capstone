package com.jjcc.dishdiscovery.activities

import android.content.ContentValues
import android.util.Log
import aws.sdk.kotlin.services.dynamodb.DynamoDbClient
import aws.sdk.kotlin.services.dynamodb.model.*
import kotlin.system.exitProcess


class Database {

    //PUT request to add an item to Table Cuisine
    suspend fun putItemInTableCuisine(
        ddb: DynamoDbClient,
        tableNameVal: String,
        key: String,
        keyVal: String,

        american: String,
        americanVal: Boolean,
        chinese: String,
        chineseVal: Boolean,
        korean: String,
        koreanVal: Boolean,
        japanese: String,
        japaneseVal: Boolean,
        turkish: String,
        turkishVal: Boolean,
        thai: String,
        thaiVal: Boolean,
        italian: String,
        italianVal: Boolean,
        indian: String,
        indianVal: Boolean,
        british: String,
        britishVal: Boolean,
        cajun: String,
        cajunVal: Boolean,
        caribbean: String,
        caribbeanVal: Boolean,
        eastEuropean: String,
        eastEuropeanVal: Boolean,
        european: String,
        europeanVal: Boolean,
        french: String,
        frenchVal: Boolean,
        irish: String,
        irishVal: Boolean,
        german: String,
        germanVal: Boolean,
        greek: String,
        greekVal: Boolean,
        jewish: String,
        jewishVal: Boolean,
        latin: String,
        latinVal: Boolean,
        mediterranean: String,
        mediterraneanVal: Boolean,
        middleEast: String,
        middleEastVal: Boolean,
        nordic: String,
        nordicVal: Boolean,
        southern: String,
        southernVal: Boolean,
        spanish: String,
        spanishVal: Boolean

    ) {
        val itemValues = mutableMapOf<String, AttributeValue>()

        // Add all content to the table.
        itemValues[key] = AttributeValue.S(keyVal)
        itemValues[american] = AttributeValue.Bool(americanVal)
        itemValues[chinese] = AttributeValue.Bool(chineseVal)
        itemValues[korean] = AttributeValue.Bool(koreanVal)
        itemValues[japanese] = AttributeValue.Bool(japaneseVal)
        itemValues[turkish] = AttributeValue.Bool(turkishVal)
        itemValues[thai] = AttributeValue.Bool(thaiVal)
        itemValues[italian] = AttributeValue.Bool(italianVal)
        itemValues[indian] = AttributeValue.Bool(indianVal)
        itemValues[british] = AttributeValue.Bool(britishVal)
        itemValues[cajun] = AttributeValue.Bool(cajunVal)
        itemValues[caribbean] = AttributeValue.Bool(caribbeanVal)
        itemValues[eastEuropean] = AttributeValue.Bool(eastEuropeanVal)
        itemValues[european] = AttributeValue.Bool(europeanVal)
        itemValues[french] = AttributeValue.Bool(frenchVal)
        itemValues[irish] = AttributeValue.Bool(irishVal)
        itemValues[german] = AttributeValue.Bool(germanVal)
        itemValues[greek] = AttributeValue.Bool(greekVal)
        itemValues[jewish] = AttributeValue.Bool(jewishVal)
        itemValues[latin] = AttributeValue.Bool(latinVal)
        itemValues[mediterranean] = AttributeValue.Bool(mediterraneanVal)
        itemValues[middleEast] = AttributeValue.Bool(middleEastVal)
        itemValues[nordic] = AttributeValue.Bool(nordicVal)
        itemValues[southern] = AttributeValue.Bool(southernVal)
        itemValues[spanish] = AttributeValue.Bool(spanishVal)

        val request = PutItemRequest {
            tableName = tableNameVal
            item = itemValues
        }

        try {
            ddb.putItem(request)
            println(" A new item was placed into $tableNameVal.")

        } catch (ex: DynamoDbException) {
            println(ex.message)
            ddb.close()
            exitProcess(0)
        }
    }

    //PUT request to add an item to Table Diet
    suspend fun putItemInTableDiet(
        ddb: DynamoDbClient,
        tableNameVal: String,
        key: String,
        keyVal: String,

        gluten: String,
        glutenVal: Boolean,
        keto: String,
        ketoVal: Boolean,
        vegetarian: String,
        vegetarianVal: Boolean,
        vegan: String,
        veganVal: Boolean,
        pescetarian: String,
        pescetarianVal: Boolean,
        lactoVege: String,
        lactoVal: Boolean,
        ovoVege: String,
        ovoVegeVal: Boolean,
        paleo: String,
        paleoVal: Boolean,
        primal: String,
        primalVal: Boolean,
        lowFod: String,
        lowFodVal: Boolean,
    ) {
        val itemValues = mutableMapOf<String, AttributeValue>()

        // Add all content to the table.
        itemValues[key] = AttributeValue.S(keyVal)
        itemValues[gluten] = AttributeValue.Bool(glutenVal)
        itemValues[keto] = AttributeValue.Bool(ketoVal)
        itemValues[vegetarian] = AttributeValue.Bool(vegetarianVal)
        itemValues[vegan] = AttributeValue.Bool(veganVal)
        itemValues[pescetarian] = AttributeValue.Bool(pescetarianVal)
        itemValues[lactoVege] = AttributeValue.Bool(lactoVal)
        itemValues[ovoVege] = AttributeValue.Bool(ovoVegeVal)
        itemValues[paleo] = AttributeValue.Bool(paleoVal)
        itemValues[primal] = AttributeValue.Bool(primalVal)
        itemValues[lowFod] = AttributeValue.Bool(lowFodVal)

        val request = PutItemRequest {
            tableName = tableNameVal
            item = itemValues
        }

        try {
            ddb.putItem(request)
            println(" A new item was placed into $tableNameVal.")

        } catch (ex: DynamoDbException) {
            println(ex.message)
            ddb.close()
            exitProcess(0)
        }
    }

    //tableName - DynamoDB table name, e.g Allergy
    //partitionKeyName - partition key name of DynamoDB table, e.g id
    //partitionKeyVal - value of the parittion key, e.g userSub id
    //Not used at the moment (returns count)
    suspend fun queryDynTable(
        ddb: DynamoDbClient,
        tableNameVal: String,
        partitionKeyName: String,
        partitionKeyVal: String,
        partitionAlias: String
    ): Int {

        val attrNameAlias = mutableMapOf<String, String>()
        attrNameAlias[partitionAlias] = partitionKeyName

        // Set up mapping of the partition name with the value.
        val attrValues = mutableMapOf<String, AttributeValue>()
        attrValues[":$partitionKeyName"] = AttributeValue.S(partitionKeyVal)

        val request = QueryRequest {
            tableName = tableNameVal
            keyConditionExpression = "$partitionAlias = :$partitionKeyName"
            expressionAttributeNames = attrNameAlias
            this.expressionAttributeValues = attrValues
        }
        try {
            val response = ddb.query(request)
            println("Trying a query... $tableNameVal.")
            return response.count

        } catch (ex: DynamoDbException) {
            println(ex.message)
            ddb.close()
            exitProcess(0)
        }

//        DynamoDbClient { region = "us-west-2" }.use { ddb ->
//            val response = ddb.query(request)
//            return response.count
//        }
    }

    suspend fun putFirstLogin(
        ddb: DynamoDbClient,
        tableNameVal: String,
        key: String,
        keyVal: String,
        loginCheck: String,
        loginBool: Boolean
    ) {
        val itemValues = mutableMapOf<String, AttributeValue>()
        itemValues[loginCheck] = AttributeValue.Bool(loginBool)

        val request = PutItemRequest {
            tableName = tableNameVal
            item = itemValues
        }

        try {
            ddb.putItem(request)
            //println(" A new item was placed into $tableNameVal.")

        } catch (ex: DynamoDbException) {
            println(ex.message)
            ddb.close()
            exitProcess(0)
        }
    }

    //PUT request to add an item to Table Allergy
    suspend fun putItemInTableAllergy(
        ddb: DynamoDbClient,
        tableNameVal: String,
        key: String,
        keyVal: String,

        peanuts: String,
        peanutsVal: Boolean,
        treenuts: String,
        treenutsVal: Boolean,
        dairy: String,
        dairyVal: Boolean,
        eggs: String,
        eggsVal: Boolean,
        shellfish: String,
        shellfishVal: Boolean,
        wheat: String,
        wheatVal: Boolean,
        soy: String,
        soyVal: Boolean,
        gluten: String,
        glutenVal: Boolean,
        seafood: String,
        seafoodVal: Boolean,
        grain: String,
        grainVal: Boolean,
        sesame: String,
        sesameVal: Boolean,
        sulfite: String,
        sulfiteVal: Boolean

    ) {
        val itemValues = mutableMapOf<String, AttributeValue>()

        // Add all content to the table.
        itemValues[key] = AttributeValue.S(keyVal)
        itemValues[peanuts] = AttributeValue.Bool(peanutsVal)
        itemValues[treenuts] = AttributeValue.Bool(treenutsVal)
        itemValues[dairy] = AttributeValue.Bool(dairyVal)
        itemValues[eggs] = AttributeValue.Bool(eggsVal)
        itemValues[shellfish] = AttributeValue.Bool(shellfishVal)
        itemValues[wheat] = AttributeValue.Bool(wheatVal)
        itemValues[soy] = AttributeValue.Bool(soyVal)
        itemValues[gluten] = AttributeValue.Bool(glutenVal)
        itemValues[seafood] = AttributeValue.Bool(seafoodVal)
        itemValues[grain] = AttributeValue.Bool(grainVal)
        itemValues[sesame] = AttributeValue.Bool(sesameVal)
        itemValues[sulfite] = AttributeValue.Bool(sulfiteVal)

        val request = PutItemRequest {
            tableName = tableNameVal
            item = itemValues
        }

        try {
            ddb.putItem(request)
            println(" A new item was placed into $tableNameVal.")

        } catch (ex: DynamoDbException) {
            println(ex.message)
            ddb.close()
            exitProcess(0)
        }
    }

    //GET request to retrieve an item using userId
    suspend fun getSpecificItem(
        ddb: DynamoDbClient,
        tableNameVal: String,
        keyName: String,
        keyVal: String
    ): Map<String, AttributeValue>? {

        //Partition key used to query the table
        val keyToGet = mutableMapOf<String, AttributeValue>()
        keyToGet[keyName] = AttributeValue.S(keyVal)

        val request = GetItemRequest {
            key = keyToGet
            tableName = tableNameVal
        }

        try {
            val returnedItem = ddb.getItem(request)
            println(" Getting Item ... $tableNameVal.")
            val numbersMap = returnedItem.item
            numbersMap?.forEach { key1 ->
                // Log.i(ContentValues.TAG, "Key: " + key1.key + " Value: " + key1.value.toString())
//                Log.i(ContentValues.TAG, key1.value.toString())
//                println(key1.value)
            }

            return numbersMap

        } catch (ex: DynamoDbException) {
            println(ex.message)
            ddb.close()
            exitProcess(0)
        }
    }
}