package com.miftah.sehaty.domain.model

import com.miftah.sehaty.core.data.remote.dto.request.FoodSaveRequest
import com.miftah.sehaty.core.data.remote.dto.request.NutritionRequest
import com.miftah.sehaty.core.data.remote.dto.request.Portion100gRequest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

/*data class FoodForCloud(
    val name: String,
    val photo: File,
    val nutrition: NutritionForCloud
)*/

/*data class FoodForCloud(
    val name: String,
    val photo: String,
    val nutrition: NutritionForCloud
)

data class NutritionForCloud(
    val dietaryFiber: Int,
    val energy: Int,
    val grade: String,
    val kolestrol: Int,
    val nutriScore: Int,
    val portionSize: Int,
    val protein: Int,
    val sodium: Int,
    val sugars: Int,
    val totalCarbs: Int,
    val totalFat: Int,
    val warnings: List<String>,
    val positiveFeedback: List<String>,
    val portion100g: Portion100gFoodForCloud
)

data class Portion100gFoodForCloud(
    val dietaryFiber: Int,
    val energy: Int,
    val portionSize: String,
    val protein: Int,
    val sodium: Int,
    val sugars: Int,
    val totalCarbs: Int,
    val totalFat: Int
)*/

data class FoodForCloud(
    val grade: String,
    val name: String,
    val nutriScore: Int,
    val nutrition: NutritionForCloud,
    val photo: String,
    val portion100g: Portion100gFoodForCloud,
    val positive: List<String>,
    val warnings: List<String>
)

data class NutritionForCloud(
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

data class Portion100gFoodForCloud(
    val dietaryFiber: Int,
    val energy: Int,
    val portionSize: String,
    val protein: Int,
    val sodium: Int,
    val sugars: Int,
    val totalCarbs: Int,
    val totalFat: Int
)

fun FoodForCloud.convertToFoodRequest(): FoodSaveRequest {
    return FoodSaveRequest(
        grade = grade,
        name = name,
        nutriScore = nutriScore,
        nutrition = nutrition.toNutritionRequest(),
        photo = photo,
        portion100g = portion100g.toPortion100gRequest(),
        positive = positive,
        warnings = warnings
    )
}

fun NutritionForCloud.toNutritionRequest(): NutritionRequest =
    NutritionRequest(
        dietaryFiber = dietaryFiber,
        energy = energy,
        kolestrol = kolestrol,
        protein = protein,
        totalFat = totalFat,
        sugars = sugars,
        totalCarbs = totalCarbs,
        sodium = sodium,
        portionSize = portionSize
    )

fun Portion100gFoodForCloud.toPortion100gRequest(): Portion100gRequest =
    Portion100gRequest(
        protein = protein,
        totalFat = totalFat,
        totalCarbs = totalCarbs,
        portionSize = portionSize,
        sugars = sugars,
        sodium = sodium,
        dietaryFiber = dietaryFiber,
        energy = energy
    )


fun FoodAfterScan.convertToFoodForCloud() : FoodForCloud {
    return FoodForCloud(
        name = productName?:"",
        photo = productPhoto,
        positive = positiveFeedback,
        grade = grade,
        warnings = warnings,
        nutriScore = nutriScore,
        nutrition = NutritionForCloud(
            sugars = sugars,
            totalCarbs = totalCarbs,
            portionSize = portionSize,
            energy = energy,
            sodium = sodium,
            dietaryFiber = dietaryFiber,
            totalFat = totalFat,
            protein = protein,
            kolestrol = cholesterol?:0
        ),
        portion100g = Portion100gFoodForCloud(
            portionSize = portionSize,
            sodium = sodium,
            dietaryFiber = dietaryFiber,
            totalFat = totalFat,
            protein = protein,
            sugars = sugars,
            energy = energy,
            totalCarbs = totalCarbs
        )
    )
}