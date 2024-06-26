package com.miftah.sehaty.core.data.remote.dto.response

import com.google.gson.annotations.SerializedName

data class ScanNutritionResponse(

    @field:SerializedName("data")
	val data: DataScanNutritionResponse? = null,

    @field:SerializedName("message")
	val message: String,

    @field:SerializedName("error")
	val error: String? = null,
)

data class Portion100gNutritionResponse(

	@field:SerializedName("totalCarbs")
	val totalCarbs: Int,

	@field:SerializedName("sodium")
	val sodium: Int,

	@field:SerializedName("totalFat")
	val totalFat: Int,

	@field:SerializedName("sugars")
	val sugars: Int,

	@field:SerializedName("dietaryFiber")
	val dietaryFiber: Int,

	@field:SerializedName("protein")
	val protein: Int,

	@field:SerializedName("portionSize")
	val portionSize: String,

	@field:SerializedName("energy")
	val energy: Int
)

data class DataScanNutritionResponse(

    @field:SerializedName("nutrition")
	val nutrition: NutritionScanResponse,

    @field:SerializedName("grade")
	val grade: String,

    @field:SerializedName("warnings")
	val warnings: List<String>,

    @field:SerializedName("portion100g")
	val portion100g: Portion100gNutritionResponse,

    @field:SerializedName("nutriScore")
	val nutriScore: Int,

	@field:SerializedName("productPhoto")
	val productPhoto: String,

	@field:SerializedName("positiveFeedback")
	val positiveFeedback: List<String>,
)

data class NutritionScanResponse(

	@field:SerializedName("totalCarbs")
	val totalCarbs: Int,

	@field:SerializedName("sodium")
	val sodium: Int,

	@field:SerializedName("totalFat")
	val totalFat: Int,

	@field:SerializedName("sugars")
	val sugars: Int,

	@field:SerializedName("kolestrol")
	val kolestrol: Int,

	@field:SerializedName("dietaryFiber")
	val dietaryFiber: Int,

	@field:SerializedName("protein")
	val protein: Int,

	@field:SerializedName("portionSize")
	val portionSize: String,

	@field:SerializedName("energy")
	val energy: Int
)
