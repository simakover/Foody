package com.sedavnyh.foody.ui.fragments.recipes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sedavnyh.foody.MainViewModel
import com.sedavnyh.foody.adapters.RecipesAdapter
import com.sedavnyh.foody.databinding.FragmentRecipesBinding
import com.sedavnyh.foody.util.Constants.Companion.API_KEY
import com.sedavnyh.foody.util.NetworkResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_recipes.view.*
import kotlinx.android.synthetic.main.recipes_row_layout.*

// Фрагмент со списком рецептов
@AndroidEntryPoint
class RecipesFragment : Fragment() {

    private var _binding: FragmentRecipesBinding? = null
    private val binding get() = _binding!!
    private lateinit var mView: View
    private val mAdapter by lazy { RecipesAdapter() }
    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecipesBinding.inflate(inflater, container, false)
        mView = binding.root

        // Привязка основной модели
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        setupRecyclerView()
        requestApiData()

        return mView
    }

    // Шиммер еффект для фрагмента
    private fun showShimmerEffect() {
        mView.recyclerView.showShimmer()
    }

    private fun hideShimmerEffect() {
        mView.recyclerView.hideShimmer()
    }

    // Связывание ресайклер вью с адаптером
    private fun setupRecyclerView() {
        mView.recyclerView.adapter = mAdapter
        mView.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        showShimmerEffect()
    }

    // Запрос даты из Апи
    private fun requestApiData(){
        mainViewModel.getRecipes(applyQueries())
        mainViewModel.recipesResponse.observe(viewLifecycleOwner, { response ->
            when(response) {
                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    response.data?.let { mAdapter.setData(it) }
                }
                is NetworkResult.Error -> {
                    hideShimmerEffect()
                    Toast.makeText(requireContext(), response.message.toString(), Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading -> {
                    showShimmerEffect()
                }
            }
        })
    }

    // Параметры запроса
    private fun applyQueries(): HashMap<String, String>{
        val queries: HashMap<String, String> = HashMap()
        queries["number"] = "50"
        queries["apiKey"] = API_KEY
        queries["type"] = "snack"
        queries["diet"] = "vegan"
        queries["addRecipeInformation"] = "true"
        queries["fillIngredients"] = "true"
        return queries
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}