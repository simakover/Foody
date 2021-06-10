package com.sedavnyh.foody.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sedavnyh.foody.databinding.RecipesRowLayoutBinding
import com.sedavnyh.foody.models.FoodRecipe
import com.sedavnyh.foody.models.Result


// Байндинг адаптер для списка рецептов рецепта. Создаем адаптер, как расширение адаптера Ресейклер вью
// Прокидываем кастомный вьюхолдер
class RecipesAdapter : RecyclerView.Adapter<RecipesAdapter.MyViewHolder>() {

    // Список рецептов
    private var recipe = emptyList<Result>()

    // Создаем кастомный вьюхолдер, прокидываем байдинг Recipes_row.xml
    class MyViewHolder(private val binding: RecipesRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

            //Привязываем к переменной в лейауте данные
            fun bind(result: Result) {
                binding.result = result
                binding.executePendingBindings()
            }

            companion object {
                // функция для запуска извне, привязывает к вьюгрупе лейаут Recipes_row.xml, создает екземпляр класса
                fun from(parent: ViewGroup): MyViewHolder {
                    val layoutInflater = LayoutInflater.from(parent.context)
                    val binding = RecipesRowLayoutBinding.inflate(layoutInflater, parent, false)
                    return MyViewHolder(binding)
                }
            }
    }

    // При создании вьюхолдера запускает функцию для привязки
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    // Привязывает конкретный рецепт к вьюхолдеру
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentResult = recipe[position]
        holder.bind(currentResult)
    }

    override fun getItemCount(): Int {
        return recipe.size
    }

    // Записывает все рецепты из модели(из апи) в переменную рецептов
    fun setData(newData: FoodRecipe){
        recipe = newData.results
        notifyDataSetChanged()
    }
}