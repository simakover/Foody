package com.sedavnyh.foody.adapters

import android.view.*
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sedavnyh.foody.R
import com.sedavnyh.foody.data.database.entities.FavoritesEntity
import com.sedavnyh.foody.databinding.FavoriteRecipesRowLayoutBinding
import com.sedavnyh.foody.ui.fragments.favorites.FavoriteRecipesFragmentDirections
import com.sedavnyh.foody.util.RecipesDiffUtil
import kotlinx.android.synthetic.main.favorite_recipes_row_layout.view.*

// Байндинг адаптер для списка избраных рецептов рецептов. Создаем адаптер, как расширение адаптера Ресейклер вью
// Прокидываем кастомный вьюхолдер, привязывает данные
class FavoriteRecipesAdapter(
    private val requireActivity: FragmentActivity
) : RecyclerView.Adapter<FavoriteRecipesAdapter.MyViewHolder>(), ActionMode.Callback {

    private var favoriteRecipes = emptyList<FavoritesEntity>()

    class MyViewHolder(private val binding: FavoriteRecipesRowLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        // Привязываем данные к переменной в лейауте
        fun bind(favoritesEntity: FavoritesEntity) {
            binding.favoritesEntity = favoritesEntity
            binding.executePendingBindings()
        }

        // Связываем холдер и лейаут строки избраного рецепта
        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = FavoriteRecipesRowLayoutBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val selectedRecipe = favoriteRecipes[position]
        holder.bind(selectedRecipe)


        // single click listener
        holder.itemView.favoriteRecipesRowLayout.setOnClickListener {
            val action =
                FavoriteRecipesFragmentDirections.actionFavoriteRecipesFragmentToDetailsActivity(selectedRecipe.result)
            holder.itemView.findNavController().navigate(action)
        }

        // long click listener
        holder.itemView.favoriteRecipesRowLayout.setOnLongClickListener {
            requireActivity.startActionMode(this)
            true
        }
    }

    override fun getItemCount(): Int {
        return favoriteRecipes.size
    }

    fun setData(newFavoriteRecipes: List<FavoritesEntity>) {
        val favoriteRecipesDiffUtil = RecipesDiffUtil(favoriteRecipes, newFavoriteRecipes)
        val diffUtilResult = DiffUtil.calculateDiff(favoriteRecipesDiffUtil)
        favoriteRecipes = newFavoriteRecipes
        diffUtilResult.dispatchUpdatesTo(this)
    }

    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        mode?.menuInflater?.inflate(R.menu.favorites_contextual_menu, menu)
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        return true
    }

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
        return true
    }

    override fun onDestroyActionMode(mode: ActionMode?) {

    }
}