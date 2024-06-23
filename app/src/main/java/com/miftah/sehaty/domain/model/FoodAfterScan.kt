package com.miftah.sehaty.domain.model

import android.os.Parcelable
import com.miftah.sehaty.core.data.remote.dto.response.ScanNutritionResponse
import kotlinx.parcelize.Parcelize

@Parcelize
data class FoodAfterScan(
    val productName: String? = null,
    val productPhoto: String,
    val grade: String,
    val nutriScore: Int,
    val warnings: List<String>,
    val positiveFeedback: List<String>,
    val dietaryFiber: Int,
    val energy: Int,
    val portionSize: Int,
    val protein: Int,
    val sodium: Int,
    val sugars: Int,
    val totalCarbs: Int,
    val totalFat: Int,
    val dietaryFiber100g: Int,
    val energy100g: Int,
    val portionSize100g: String,
    val protein100g: Int,
    val sodium100g: Int,
    val sugars100g: Int,
    val totalCarbs100g: Int,
    val totalFat100g: Int,
    val cholesterol : Int? = null
) : Parcelable

fun ScanNutritionResponse.convertToFoodAfterScan(): FoodAfterScan {
    this.data!!.let {
        return FoodAfterScan(
            warnings = it.warnings,
            grade = it.grade,
            nutriScore = it.nutriScore,
            dietaryFiber = it.nutrition.dietaryFiber,
            energy = it.nutrition.energy,
            totalFat = it.nutrition.totalFat,
            portionSize = it.nutrition.portionSize,
            totalCarbs = it.nutrition.totalCarbs,
            sodium = it.nutrition.sodium,
            sugars = it.nutrition.sugars,
            protein = it.nutrition.protein,
            dietaryFiber100g = it.portion100g.dietaryFiber,
            totalCarbs100g = it.nutrition.totalCarbs,
            totalFat100g = it.portion100g.totalFat,
            energy100g = it.portion100g.energy,
            sodium100g = it.portion100g.sodium,
            sugars100g = it.portion100g.sugars,
            protein100g = it.portion100g.protein,
            portionSize100g = it.portion100g.portionSize,
            productPhoto = it.productPhoto,
            positiveFeedback = it.positiveFeedback
        )
    }
}

fun HistoryScanned.convertToFoodAfterScan(): FoodAfterScan =
    FoodAfterScan(
        totalCarbs = totalCarbs,
        totalFat = totalFat,
        productPhoto = productPhoto,
        sugars = sugars,
        warnings = warnings,
        productName = productName,
        sodium = sodium,
        dietaryFiber = dietaryFiber,
        protein = protein,
        portionSize = portionSize,
        energy = energy,
        dietaryFiber100g = portion100gDietaryFiber,
        totalCarbs100g = portion100gTotalCarbs,
        totalFat100g = portion100gTotalFat,
        energy100g = portion100gEnergy,
        sodium100g = portion100gSodium,
        sugars100g = portion100gSugars,
        protein100g = portion100gProtein,
        portionSize100g = portion100gSize,
        grade = grade,
        nutriScore = nutriScore,
        positiveFeedback = positiveFeedback
    )