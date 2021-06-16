package com.sedavnyh.foody.bindingadapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import coil.load
import com.sedavnyh.foody.R

// Байндинг адапетр для конкретного рецетпа\карточки
class RecipesRowBinding {

    // объект для обращения к классу без создания екземпляров
    companion object{

        // Создаем новый параметр для вью который устанавлиет текст вью
        // JvmStatic дает доступ из любого места
        // BindingAdapter - создает свойство для вью в лейауте
        // первый параметр обязательный - тип вью
        @BindingAdapter("setNumberOfLikes")
        @JvmStatic
        fun setNumberOfLikes(textView: TextView, likes: Int){
            textView.text = likes.toString()
        }

        @BindingAdapter("setNumberOfMinutes")
        @JvmStatic
        fun setNumberOfMinutes(textView: TextView, minutes: Int){
            textView.text = minutes.toString()
        }

        // Установить цвет взависимости от веган\не веган
        // Если вью - текстовое, то цвет текста
        // Если вью - картинка, то тинт
        @BindingAdapter("isVegan")
        @JvmStatic
        fun isVegan(view: View, vegan: Boolean){
            if (vegan){
                when(view){
                    is TextView -> view.setTextColor(ContextCompat.getColor(view.context, R.color.green))
                    is ImageView -> view.setColorFilter(ContextCompat.getColor(view.context, R.color.green))
                }
            }

        }

        @BindingAdapter("loadImageFromUrl")
        @JvmStatic
        fun loadImageFromUrl(imageView: ImageView, imageUrl: String){
            imageView.load(imageUrl){
                crossfade(600)
                error(R.drawable.ic_error_placeholder)
            }
        }
    }
}