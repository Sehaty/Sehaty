package com.miftah.sehaty.core.data.remote.dto.request

import okhttp3.MultipartBody
import okhttp3.RequestBody

/*data class FoodSaveRequest(
    val name: RequestBody,
    val nutrition: NutritionRequest,
    val photo: MultipartBody.Part
)*/

/*
data class FoodSaveRequest(
    val name: String,
    val nutrition: NutritionRequest,
    val photo: String
)

data class NutritionRequest(
    val dietaryFiber: Int,
    val energy: Int,
    val grade: String,
    val kolestrol: Int,
    val nutriScore: Int,
    val portion100g: Portion100gRequest,
    val portionSize: Int,
    val protein: Int,
    val sodium: Int,
    val sugars: Int,
    val totalCarbs: Int,
    val totalFat: Int,
    val warnings: List<String>,
    val positive: List<String>
)

data class Portion100gRequest(
    val dietaryFiber: Int,
    val energy: Int,
    val portionSize: String,
    val protein: Int,
    val sodium: Int,
    val sugars: Int,
    val totalCarbs: Int,
    val totalFat: Int
)*/

data class FoodSaveRequest(
    val grade: String,
    val name: String,
    val nutriScore: Int,
    val nutrition: NutritionRequest,
    val photo: String,
    val portion100g: Portion100gRequest,
    val positive: List<String>,
    val warnings: List<String>
)

data class NutritionRequest(
    val dietaryFiber: Int,
    val energy: Int,
    val kolestrol: Int,
    val portionSize: String,
    val protein: Int,
    val sodium: Int,
    val sugars: Int,
    val totalCarbs: Int,
    val totalFat: Int
)

data class Portion100gRequest(
    val dietaryFiber: Int,
    val energy: Int,
    val portionSize: String,
    val protein: Int,
    val sodium: Int,
    val sugars: Int,
    val totalCarbs: Int,
    val totalFat: Int
)