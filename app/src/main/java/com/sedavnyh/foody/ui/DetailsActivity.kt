package com.sedavnyh.foody.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.navArgs
import com.google.android.material.snackbar.Snackbar
import com.sedavnyh.foody.R
import com.sedavnyh.foody.adapters.PagerAdapter
import com.sedavnyh.foody.data.database.entities.FavoritesEntity
import com.sedavnyh.foody.ui.fragments.ingredients.IngredientsFragment
import com.sedavnyh.foody.ui.fragments.instructions.InstructionsFragment
import com.sedavnyh.foody.ui.fragments.overview.OverviewFragment
import com.sedavnyh.foody.util.Constants.Companion.RECIPE_RESULT_KEY
import com.sedavnyh.foody.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_details.*
import java.lang.Exception

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {

    private val args by navArgs<DetailsActivityArgs>()
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        // Установка тулбара
        setSupportActionBar(toolbar)
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //Создаем листы из активити и заголовков для прокидывания в адаптер закладок
        val fragments = ArrayList<Fragment>()
        fragments.add(OverviewFragment())
        fragments.add(IngredientsFragment())
        fragments.add(InstructionsFragment())

        val titles = ArrayList<String>()
        titles.add("Overview")
        titles.add("Ingredients")
        titles.add("Instructions")

        // Прокидываем в бандл аргументы из списка рецептов( один рецепт)
        val resultBundle = Bundle()
        resultBundle.putParcelable(RECIPE_RESULT_KEY, args.result)

        //Создаем адаптер для закладок
        val adapter = PagerAdapter(resultBundle, fragments, titles, supportFragmentManager)

        //Привзяываем адаптер к вью пейджеру на лейауте и связываем с закладками на лейауте
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)
    }

    // Привязка меню со звездой
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.details_menu, menu)

        val menuItem = menu?.findItem(R.id.save_to_favorite)
        checkSavedRecipe(menuItem!!)

        return true
    }

    // закрыть активити через стрелку в баре
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            R.id.save_to_favorite -> saveToFavorite(item)
        }
        return super.onOptionsItemSelected(item)
    }

    // Сохранение рецепта в избраное
    private fun saveToFavorite(item: MenuItem) {
        val favoritesEntity =
            FavoritesEntity(
                0,
                args.result
            )
        mainViewModel.insertFavoriteRecipe(favoritesEntity)
        changeMenuItemColor(item, true )
        showSnackBar("Recipe saved")
    }

    // Проверка сохренен ли рецерт - для цвета и поведения звезды
    private fun checkSavedRecipe(item: MenuItem) {
        mainViewModel.readFavoriteRecipes.observe(this, {
            try {
                for(savedRecipe in it) {
                    if (savedRecipe.result.id == args.result.id) {
                        changeMenuItemColor(item, true)
                        return@observe
                    }
                }
                changeMenuItemColor(item, false)
            } catch (e: Exception) {
                Log.d("DetailsActivity", e.message.toString())
            }
        })
    }

    // Смена кнопки звезды
    private fun changeMenuItemColor(item: MenuItem, found: Boolean) {
        if (found) {
            item.icon = ContextCompat.getDrawable(this, R.drawable.ic_star)
            item.icon.setTint(ContextCompat.getColor(this, R.color.yellow))
        } else {
            item.icon = ContextCompat.getDrawable(this, R.drawable.ic_star_empty)
            item.icon.setTint(ContextCompat.getColor(this, R.color.white))
        }
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(details_layout, message, Snackbar.LENGTH_SHORT).setAction("Ok"){}.show()
    }
}