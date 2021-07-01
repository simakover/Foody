package com.sedavnyh.foody.ui.fragments.ingredients

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.sedavnyh.foody.R
import com.sedavnyh.foody.adapters.IngredientsAdapter
import com.sedavnyh.foody.databinding.FragmentIngredientsBinding
import com.sedavnyh.foody.databinding.FragmentRecipesBinding
import com.sedavnyh.foody.models.Result
import com.sedavnyh.foody.util.Constants.Companion.RECIPE_RESULT_KEY

class IngredientsFragment : Fragment() {

    private var _binding: FragmentIngredientsBinding? = null
    private val binding get() = _binding!!
    private val mAdapter by lazy { IngredientsAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentIngredientsBinding.inflate(inflater, container, false)

        // загрузка аргументов из бандла
        val args = arguments
        val myBundle: Result? = args?.getParcelable(RECIPE_RESULT_KEY)

        // прокинуть ингредиенты из бандла в вьюхолдеры
        myBundle?.extendedIngredients?.let {
            mAdapter.setData(it)
        }

        setupRecyclerView()

        return binding.root
    }

    // Установка адаптера для ресайклер вью
    private fun setupRecyclerView() {
        binding.ingredientsRecyclerView.adapter = mAdapter
        binding.ingredientsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }
}