package com.miftah.sehaty.core.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.miftah.sehaty.core.data.remote.dto.response.DataItemHistory
import com.miftah.sehaty.utils.AppUtility.fromListToString


@Entity(tableName = "historyScanItem")
data class HistoryScannedEntity(
    @ColumnInfo("id")
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo("createdAt")
    val createdAt: String? = null,

    @ColumnInfo("dietaryFiber")
    val dietaryFiber: Int,

    @ColumnInfo("energy")
    val energy: Int,

    @ColumnInfo("grade")
    val grade: String,

    @ColumnInfo("nutriScore")
    val nutriScore: Int,

    @ColumnInfo("portion100gDietaryFiber")
    val portion100gDietaryFiber: Int,

    @ColumnInfo("portion100gEnergy")
    val portion100gEnergy: Int,

    @ColumnInfo("portion100gProtein")
    val portion100gProtein: Int,

    @ColumnInfo("portion100gSize")
    val portion100gSize: String,

    @ColumnInfo("portion100gSodium")
    val portion100gSodium: Int,

    @ColumnInfo("portion100gSugars")
    val portion100gSugars: Int,

    @ColumnInfo("portion100gTotalCarbs")
    val portion100gTotalCarbs: Int,

    @ColumnInfo("portion100gTotalFat")
    val portion100gTotalFat: Int,

    @ColumnInfo("portionSize")
    val portionSize: String,

    @ColumnInfo("productName")
    val productName: String,

    @ColumnInfo("productPhoto")
    val productPhoto: String,

    @ColumnInfo("protein")
    val protein: Int,

    @ColumnInfo("sodium")
    val sodium: Int,

    @ColumnInfo("sugars")
    val sugars: Int,

    @ColumnInfo("totalCarbs")
    val totalCarbs: Int,

    @ColumnInfo("totalFat")
    val totalFat: Int,

    @ColumnInfo("warnings")
    val warnings: String,

    @ColumnInfo("positiveFeedback")
    val positiveFeedback: String
)

fun DataItemHistory.convertHistoryScannedEntity() =
    HistoryScannedEntity(
        createdAt = this.createdAt,
        totalCarbs = this.totalCarbs,
        productPhoto = this.productPhoto,
        sodium = this.sodium,
        sugars = this.sugars,
        portionSize = this.portionSize,
        totalFat = this.totalFat,
        energy = this.energy,
        dietaryFiber = this.dietaryFiber,
        nutriScore = this.nutriScore,
        grade = this.grade,
        warnings = fromListToString(this.warnings),
        positiveFeedback = fromListToString(this.positive),
        productName = this.productName,
        protein = this.protein,
        portion100gTotalCarbs = this.portion100gTotalCarbs,
        portion100gTotalFat = this.portion100gTotalFat,
        portion100gSugars = this.portion100gSugars,
        portion100gEnergy = this.portion100gEnergy,
        portion100gProtein = this.portion100gProtein,
        portion100gSodium = this.portion100gSodium,
        portion100gSize = this.portion100gSize,
        portion100gDietaryFiber = this.portion100gDietaryFiber,
    )

