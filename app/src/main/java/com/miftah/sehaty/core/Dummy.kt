package com.miftah.sehaty.core

import com.miftah.sehaty.domain.model.FoodAfterScan
import com.miftah.sehaty.domain.model.HistoryScanned
import com.miftah.sehaty.utils.AppUtility.getRandomString
import com.miftah.sehaty.utils.generateRandomTitle

/*fun dummyHistoriesScanned(count : Int) : List<HistoryScanned> {
    val result : MutableList<HistoryScanned> = arrayListOf()
    var index = 0
    while (index < count) {
        result.add(dummyHistoryScanned(index))
        index += 1
    }
    return result
}

fun dummyHistoryScanned(index : Int) : HistoryScanned {
    return HistoryScanned(
        totalCarbs = index,
        grade = "$index",
        energy = index,
        sodium = index,
        sugars = index,
        totalFat = index,
        warnings = listOf("Gula"),
        protein = index,
        portion100gSize = "$index",
        createdAt = "$index",
        dietaryFiber = index,
        productPhoto = "$index",
        nutriScore = index,
        portion100g = dummyPortion100g(index),
        portionSize = index,
        productName = "$index"
    )
}

fun dummyNutrients(count: Int) : List<Nutrition>{
    val result : MutableList<Nutrition> = arrayListOf()
    var index = 0
    while (index < count) {
        result.add(dummyNutrient(index))
        index += 1
    }
    return result
}

fun dummyNutrient(index: Int) : Nutrition {
    return Nutrition(
        grade = index.toString(),
        totalCarbs = index,
        dietaryFiber = index,
        warnings = listOf(index.toString()),
        totalFat = index,
        nutriScore = index,
        portion100g = dummyPortion100g(index),
        energy = index,
        sugars = index,
        sodium = index,
        protein = index,
        portionSize = index,
        kolestrol = index
    )
}
fun dummyPortions100g(count: Int) : List<Portion100g> {
    val result : MutableList<Portion100g> = arrayListOf()
    var index = 0
    while (index < count) {
        result.add(dummyPortion100g(index))
        index += 1
    }
    return result
}

fun dummyPortion100g(index: Int) : Portion100g {
    return Portion100g(
        dietaryFiber = index,
        protein = index,
        totalFat = index,
        portionSize = "$index",
        energy = index,
        totalCarbs = index,
        sugars = index,
        sodium = index
    )
}*/

fun dummyFoodAfterScan() : FoodAfterScan {
    return FoodAfterScan(
        sodium = 100,
        dietaryFiber = 100,
        portionSize = 100,
        totalCarbs = 100,
        productPhoto = "https://sehaty.akutegar.com/uploads/1718869390165-label nutrisi berbahaya.jpeg",
        grade = getRandomString(10),
        totalFat = 100,
        sugars = 100,
        energy = 100,
        protein = 100,
        dietaryFiber100g = 100,
        productName = getRandomString(10),
        nutriScore = 100,
        totalCarbs100g = 100,
        sugars100g = 100,
        sodium100g = 100,
        totalFat100g = 100,
        energy100g = 100,
        warnings = listOf("Sugar"),
        cholesterol = 100,
        protein100g = 100,
        portionSize100g = "100",
        positiveFeedback = listOf("Sugar")
    )
}


