package com.sedavnyh.foody.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.sedavnyh.foody.R
import com.sedavnyh.foody.models.ExtendedIngredient
import com.sedavnyh.foody.util.Constants.Companion.BASE_IMAGE_URL
import com.sedavnyh.foody.util.RecipesDiffUtil
import kotlinx.android.synthetic.main.ingredients_row_layout.view.*


//Адаптер для списка ингредиентов
class IngredientsAdapter : RecyclerView.Adapter<IngredientsAdapter.MyViewHolder>() {

    // Список ингредиентов
    private var ingredientList = emptyList<ExtendedIngredient>()

    // вьюхолдер
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    // при созданию вьюхолдера - создаем его екземпляр и возвращаем
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.ingredients_row_layout, parent, false))
    }

    // привязываем все данные
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.ingredient_imageView.load(BASE_IMAGE_URL + ingredientList[position].image) {
            crossfade(600)
            error(R.drawable.ic_error_placeholder)
        }
        holder.itemView.ingredientName_textView.text = ingredientList[position].name.capitalize()
        holder.itemView.ingredientAmount_textView.text = ingredientList[position].amount.toString()
        holder.itemView.ingredientUnit_textView.text = ingredientList[position].unit
        holder.itemView.ingredientConsistency_textView.text = ingredientList[position].consistency
        holder.itemView.ingredientOriginal_textView.text = ingredientList[position].original
    }

    override fun getItemCount(): Int {
        return ingredientList.size
    }

    // записать данные с проверкой на одинаковость
    fun setData(newIngredients: List<ExtendedIngredient>) {
        val ingredientsDiffUtil = RecipesDiffUtil(ingredientList, newIngredients)
        val diffUtilResult = DiffUtil.calculateDiff(ingredientsDiffUtil)
        ingredientList = newIngredients
        diffUtilResult.dispatchUpdatesTo(this)
    }
}