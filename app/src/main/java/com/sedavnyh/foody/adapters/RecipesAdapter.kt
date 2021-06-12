package com.sedavnyh.foody.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sedavnyh.foody.databinding.RecipesRowLayoutBinding
import com.sedavnyh.foody.models.FoodRecipe
import com.sedavnyh.foody.models.Result
import com.sedavnyh.foody.util.RecipesDiffUtil


// Байндинг адаптер для списка рецептов рецепта. Создаем адаптер, как расширение адаптера Ресейклер вью
// Прокидываем кастомный вьюхолдер, привязывает данные из апи к конкретным карточкам
class RecipesAdapter : RecyclerView.Adapter<RecipesAdapter.MyViewHolder>() {

    // Список рецептов
    private var recipes = emptyList<Result>()

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

    // При создании вьюхолдера запускает функцию для создания и привязки
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    // Привязывает конкретный рецепт к вьюхолдеру
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentRecipe = recipes[position]
        holder.bind(currentRecipe)
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    // Записывает все рецепты из модели(из апи) в переменную рецептов
    // Сравнивает новый и старый спиcок, обновляет только при наличии изменений
    fun setData(newData: FoodRecipe){
        val recipesDiffUtil = RecipesDiffUtil(recipes, newData.results)
        val diffUtilResult = DiffUtil.calculateDiff(recipesDiffUtil)
        recipes = newData.results
        diffUtilResult.dispatchUpdatesTo(this)
    }
}