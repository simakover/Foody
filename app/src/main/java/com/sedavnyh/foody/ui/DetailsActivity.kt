package com.sedavnyh.foody.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.navArgs
import com.sedavnyh.foody.R
import com.sedavnyh.foody.adapters.PagerAdapter
import com.sedavnyh.foody.ui.fragments.ingredients.IngredientsFragment
import com.sedavnyh.foody.ui.fragments.instructions.InstructionsFragment
import com.sedavnyh.foody.ui.fragments.overview.OverviewFragment
import com.sedavnyh.foody.util.Constants.Companion.RECIPE_RESULT_KEY
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {

    private val args by navArgs<DetailsActivityArgs>()

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

    // закрыть активити через стрелку в баре
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}