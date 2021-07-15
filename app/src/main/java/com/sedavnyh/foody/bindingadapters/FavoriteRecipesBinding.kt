package com.sedavnyh.foody.bindingadapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sedavnyh.foody.adapters.FavoriteRecipesAdapter
import com.sedavnyh.foody.data.database.entities.FavoritesEntity

class FavoriteRecipesBinding {

    companion object {

        @BindingAdapter("viewVisibility")
        @JvmStatic
        fun setDataAndViewVisibility(
            view: View,
            favoritesEntity: List<FavoritesEntity>?,
        ) {
            if (favoritesEntity.isNullOrEmpty()) {
                when (view){
                    is ImageView -> view.visibility = View.VISIBLE
                    is TextView  -> view.visibility = View.VISIBLE
                    is RecyclerView -> view.visibility = View.INVISIBLE
                }
            } else {
                when (view){
                    is ImageView -> view.visibility = View.INVISIBLE
                    is TextView  -> view.visibility = View.INVISIBLE
                    is RecyclerView -> view.visibility = View.VISIBLE
                }
            }
        }
    }
}