package com.jjcc.dishdiscovery.activities

import aws.sdk.kotlin.services.dynamodb.DynamoDbClient
import aws.sdk.kotlin.services.dynamodb.model.AttributeValue
import aws.sdk.kotlin.services.dynamodb.model.PutItemRequest
import aws.sdk.kotlin.services.dynamodb.model.DynamoDbException
import kotlin.system.exitProcess

class Database {

    suspend fun putItemInTableCuisine (
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
        indianVal: Boolean

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

        val request = PutItemRequest {
            tableName=tableNameVal
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

    suspend fun putItemInTableDiet(
        ddb: DynamoDbClient,
        tableNameVal: String,
        key: String,
        keyVal: String,

        halal: String,
        halalVal: Boolean,
        keto: String,
        ketoVal: Boolean,
        kosher: String,
        kosherVal: Boolean,
        vegetarian: String,
        vegetarianVal: Boolean,
        vegan: String,
        veganVal: Boolean,
        pescetarian: String,
        pescetarianVal: Boolean,
        lowcarb: String,
        lowcarbVal: Boolean
    ) {
        val itemValues = mutableMapOf<String, AttributeValue>()

        // Add all content to the table.
        itemValues[key] = AttributeValue.S(keyVal)
        itemValues[halal] = AttributeValue.Bool(halalVal)
        itemValues[keto] = AttributeValue.Bool(ketoVal)
        itemValues[kosher] = AttributeValue.Bool(kosherVal)
        itemValues[vegetarian] = AttributeValue.Bool(vegetarianVal)
        itemValues[vegan] = AttributeValue.Bool(veganVal)
        itemValues[pescetarian] = AttributeValue.Bool(pescetarianVal)
        itemValues[lowcarb] = AttributeValue.Bool(lowcarbVal)


        val request = PutItemRequest {
            tableName=tableNameVal
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
        fish: String,
        fishVal: Boolean

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
        itemValues[fish] = AttributeValue.Bool(fishVal)


        val request = PutItemRequest {
            tableName=tableNameVal
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
}