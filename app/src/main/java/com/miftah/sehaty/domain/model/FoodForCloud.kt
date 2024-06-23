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

data class FoodForCloud(
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
)

fun FoodForCloud.convertToFoodRequest(): FoodSaveRequest {
    /*
    val name = this.name.toRequestBody("text/plain".toMediaTypeOrNull())
    val requestFile = this.photo.asRequestBody("image/*".toMediaTypeOrNull())
    val photoPart = MultipartBody.Part.createFormData("photo", this.name, requestFile)
    */

     */
    return FoodSaveRequest(
        nutrition = nutrition.toNutritionRequest(),
        name = name,
        photo = photo
    )
}

fun NutritionForCloud.toNutritionRequest(): NutritionRequest =
    NutritionRequest(
        dietaryFiber = dietaryFiber,
        energy = energy,
        grade = grade,
        kolestrol = kolestrol,
        nutriScore = nutriScore,
        portionSize = portionSize,
        protein = protein,
        sodium = sodium,
        sugars = sugars,
        totalCarbs = totalCarbs,
        totalFat = totalFat,
        warnings = warnings,
        portion100g = portion100g.toPortion100gRequest()
    )

fun Portion100gFoodForCloud.toPortion100gRequest(): Portion100gRequest =
    Portion100gRequest(
        dietaryFiber = dietaryFiber,
        energy = energy,
        portionSize = portionSize,
        protein = protein,
        sodium = sodium,
        sugars = sugars,
        totalCarbs = totalCarbs,
        totalFat = totalFat
    )

fun FoodAfterScan.convertToFoodForCloud() : FoodForCloud {
    return FoodForCloud(
        name = productName?:"",
        photo = productPhoto,
        nutrition = NutritionForCloud(
            dietaryFiber = dietaryFiber,
            energy = energy,
            grade = grade,
            kolestrol = cholesterol?:0,
            nutriScore = nutriScore,
            portionSize = portionSize,
            protein = protein,
            sodium = sodium,
            sugars = sugars,
            totalCarbs = totalCarbs,
            totalFat = totalFat,
            warnings = warnings,
            portion100g = Portion100gFoodForCloud(
                protein = protein100g,
                dietaryFiber = dietaryFiber100g,
                portionSize = portionSize100g,
                totalCarbs = totalCarbs100g,
                totalFat = totalFat100g,
                energy = energy100g,
                sugars = sugars100g,
                sodium = sodium100g
            ),
            positiveFeedback = this.positiveFeedback
        ),
    )
}