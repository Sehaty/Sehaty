package com.miftah.sehaty.domain.model

import com.miftah.sehaty.core.data.local.entity.HistoryScannedEntity
import com.miftah.sehaty.core.data.remote.dto.response.GetHistoryResponse
import com.miftah.sehaty.utils.AppUtility.fromListToString
import com.miftah.sehaty.utils.AppUtility.fromStringToList

data class HistoryScanned(
    val createdAt: String? = null,
    val dietaryFiber: Int,
    val energy: Int,
    val grade: String,
    val nutriScore: Int,
    val portion100gDietaryFiber: Int,
    val portion100gEnergy: Int,
    val portion100gProtein: Int,
    val portion100gSize: String,
    val portion100gSodium: Int,
    val portion100gSugars: Int,
    val portion100gTotalCarbs: Int,
    val portion100gTotalFat: Int,
    val portionSize: Int,
    val productName: String,
    val productPhoto: String,
    val protein: Int,
    val sodium: Int,
    val sugars: Int,
    val totalCarbs: Int,
    val totalFat: Int,
    val warnings: List<String>,
    val positiveFeedback: List<String>
)

fun GetHistoryResponse.getHistoryConvertToScanItem(): List<HistoryScanned> {
    val result: MutableList<HistoryScanned> = arrayListOf()
    this.data.forEach {
        result.add(
            HistoryScanned(
                totalCarbs = it.totalCarbs,
                totalFat = it.totalFat,
                productPhoto = it.productPhoto,
                sugars = it.sugars,
                warnings = it.warnings,
                productName = it.productName,
                sodium = it.sodium,
                createdAt = it.createdAt,
                portion100gSize = it.portion100gSize,
                dietaryFiber = it.dietaryFiber,
                protein = it.protein,
                portionSize = it.portionSize,
                energy = it.energy,
                portion100gDietaryFiber = it.portion100gDietaryFiber,
                portion100gEnergy = it.portion100gEnergy,
                portion100gProtein = it.portion100gProtein,
                portion100gSodium = it.portion100gSodium,
                portion100gSugars = it.portion100gSugars,
                portion100gTotalCarbs = it.portion100gTotalCarbs,
                portion100gTotalFat = it.portion100gTotalFat,
                grade = it.grade,
                nutriScore = it.nutriScore,
                positiveFeedback = it.positive
            )
        )
    }
    return result
}

fun HistoryScannedEntity.convertToHistoryScanned() : HistoryScanned =
    HistoryScanned(
        totalCarbs = totalCarbs,
        totalFat = totalFat,
        productPhoto = productPhoto,
        sugars = sugars,
        warnings = fromStringToList(warnings),
        productName = productName,
        sodium = sodium,
        createdAt = createdAt,
        portion100gSize = portion100gSize,
        dietaryFiber = dietaryFiber,
        protein = protein,
        portionSize = portionSize,
        energy = energy,
        portion100gDietaryFiber = portion100gDietaryFiber,
        portion100gEnergy = portion100gEnergy,
        portion100gProtein = portion100gProtein,
        portion100gSodium = portion100gSodium,
        portion100gSugars = portion100gSugars,
        portion100gTotalCarbs = portion100gTotalCarbs,
        portion100gTotalFat = portion100gTotalFat,
        grade = grade,
        nutriScore = nutriScore,
        positiveFeedback = fromStringToList(positiveFeedback)
    )

fun HistoryScanned.convertToHistoryEntity() : HistoryScannedEntity =
    HistoryScannedEntity(
        totalCarbs = totalCarbs,
        totalFat = totalFat,
        productPhoto = productPhoto,
        sugars = sugars,
        warnings = fromListToString(warnings),
        productName = productName,
        sodium = sodium,
        createdAt = createdAt,
        portion100gSize = portion100gSize,
        dietaryFiber = dietaryFiber,
        protein = protein,
        portionSize = portionSize,
        energy = energy,
        portion100gDietaryFiber = portion100gDietaryFiber,
        portion100gEnergy = portion100gEnergy,
        portion100gProtein = portion100gProtein,
        portion100gSodium = portion100gSodium,
        portion100gSugars = portion100gSugars,
        portion100gTotalCarbs = portion100gTotalCarbs,
        portion100gTotalFat = portion100gTotalFat,
        grade = grade,
        nutriScore = nutriScore,
        positiveFeedback = fromListToString(positiveFeedback)
    )

/*fun HistoryScannedEntity.convertToHistoryScanned() : HistoryScanned =
    HistoryScanned(
        dietaryFiber = dietaryFiber,
        totalCarbs = totalCarbs,
        productName = productName,
        positiveFeedback = fromStringToList(positiveFeedback),
        productPhoto = productPhoto,
        totalFat = totalFat,
        grade = grade,
        nutriScore = nutriScore,
        warnings = fromStringToList(warnings),
        sugars = sugars,
        portionSize = portionSize,
        energy = energy,
        sodium = sodium,
        protein = protein,
        portion100gTotalCarbs = portion100gTotalCarbs,
        portion100gTotalFat = portion100gTotalFat,
        portion100gEnergy = portion100gEnergy,
        portion100gSodium = portion100gSodium,
        portion100gSugars = portion100gSugars,
        portion100gProtein = portion100gProtein,
        createdAt = createdAt,
        portion100gSize = portion100gSize,
        portion100gDietaryFiber = portion100gDietaryFiber
    )*/

fun FoodAfterScan.convertToHistoryScan(): HistoryScanned =
    HistoryScanned(
        totalCarbs = totalCarbs,
        totalFat = totalFat,
        productPhoto = productPhoto,
        sugars = sugars,
        warnings = warnings,
        productName = productName?:"",
        sodium = sodium,
        dietaryFiber = dietaryFiber,
        protein = protein,
        portionSize = portionSize,
        energy = energy,
        portion100gDietaryFiber = dietaryFiber100g,
        portion100gTotalCarbs = totalCarbs100g,
        portion100gTotalFat = totalFat100g,
        portion100gEnergy = energy100g,
        portion100gSodium = sodium100g,
        portion100gSugars = sugars100g,
        portion100gProtein = protein100g,
        portion100gSize = portionSize100g,
        grade = grade,
        nutriScore = nutriScore,
        positiveFeedback = positiveFeedback
    )
