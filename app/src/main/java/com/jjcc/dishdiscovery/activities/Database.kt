package com.jjcc.dishdiscovery.activities

import aws.sdk.kotlin.services.dynamodb.DynamoDbClient
import aws.sdk.kotlin.services.dynamodb.model.AttributeValue
import aws.sdk.kotlin.services.dynamodb.model.PutItemRequest
import aws.sdk.kotlin.services.dynamodb.model.DynamoDbException
import kotlin.system.exitProcess

class Database {

    suspend fun putItemInTable2(
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