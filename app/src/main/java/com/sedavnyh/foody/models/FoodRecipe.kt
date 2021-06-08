package com.sedavnyh.foody.models


import com.google.gson.annotations.SerializedName

// Класс который содержит список из рецетов по запросу
data class FoodRecipe(
    @SerializedName("results")
    val results: List<Result>
)