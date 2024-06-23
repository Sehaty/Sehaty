package com.miftah.sehaty.core.data.remote.dto.response

import com.google.gson.annotations.SerializedName

data class GetHistoryResponse(

    @field:SerializedName("data")
    val data: List<DataItemHistory> = emptyList(),

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("error")
    val error: String? = null,
)

data class DataItemHistory(

    @field:SerializedName("totalCarbs")
    val totalCarbs: Int,

    @field:SerializedName("totalFat")
    val totalFat: Int,

    @field:SerializedName("productPhoto")
    val productPhoto: String,

    @field:SerializedName("sugars")
    val sugars: Int,

    @field:SerializedName("productId")
    val productId: String,

    @field:SerializedName("warnings")
    val warnings: List<String>,

    @field:SerializedName("positiveFeedback")
    val positiveFeedback: List<String>,

    @field:SerializedName("productName")
    val productName: String,

    @field:SerializedName("sodium")
    val sodium: Int,

    @field:SerializedName("createdAt")
    val createdAt: String,

    @field:SerializedName("portion100gSize")
    val portion100gSize: String,

    @field:SerializedName("dietaryFiber")
    val dietaryFiber: Int,

    @field:SerializedName("protein")
    val protein: Int,

    @field:SerializedName("_id")
    val id: String,

    @field:SerializedName("portionSize")
    val portionSize: Int,

    @field:SerializedName("energy")
    val energy: Int,

    @field:SerializedName("portion100gDietaryFiber")
    val portion100gDietaryFiber: Int,

    @field:SerializedName("portion100gEnergy")
    val portion100gEnergy: Int,

    @field:SerializedName("portion100gSodium")
    val portion100gSodium: Int,

    @field:SerializedName("portion100gTotalCarbs")
    val portion100gTotalCarbs: Int,

    @field:SerializedName("portion100gTotalFat")
    val portion100gTotalFat: Int,

    @field:SerializedName("portion100gSugars")
    val portion100gSugars: Int,

    @field:SerializedName("grade")
    val grade: String,

    @field:SerializedName("portion100gProtein")
    val portion100gProtein: Int,

    @field:SerializedName("nutriScore")
    val nutriScore: Int
)
