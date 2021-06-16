package com.sedavnyh.foody.ui.fragments.recipes

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.sedavnyh.foody.viewmodels.MainViewModel
import com.sedavnyh.foody.adapters.RecipesAdapter
import com.sedavnyh.foody.databinding.FragmentRecipesBinding
import com.sedavnyh.foody.util.Constants.Companion.API_KEY
import com.sedavnyh.foody.util.NetworkResult
import com.sedavnyh.foody.util.observeOnce
import com.sedavnyh.foody.viewmodels.RecipesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_recipes.view.*
import kotlinx.coroutines.launch

// Фрагмент со списком рецептов
@AndroidEntryPoint
class RecipesFragment : Fragment() {

    private var _binding: FragmentRecipesBinding? = null
    private val binding get() = _binding!!
    private lateinit var mView: View
    private val mAdapter by lazy { RecipesAdapter() }
    private lateinit var mainViewModel: MainViewModel
    private lateinit var recipesViewModel: RecipesViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Привязка основной модели
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        // Привязка модели с очередью
        recipesViewModel = ViewModelProvider(requireActivity()).get(RecipesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecipesBinding.inflate(inflater, container, false)
        mView = binding.root

        setupRecyclerView()
        readDatabase()

        /*mainViewModel.readRecipes.observe(viewLifecycleOwner, {database ->
            if (database.isEmpty()){
                hideShimmerEffect()
                binding.errorImageView.visibility = View.VISIBLE
                binding.errorTextView.visibility = View.VISIBLE
            } else {
                binding.errorImageView.visibility = View.INVISIBLE
                binding.errorTextView.visibility = View.INVISIBLE
            }
        })*/

        return mView
    }

    // Связывание ресайклер вью с адаптером
    private fun setupRecyclerView() {
        mView.recyclerView.adapter = mAdapter
        mView.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        showShimmerEffect()
    }

    // Читаем из базы данных, и если данных нет - запрашиваем из апи
    private fun readDatabase() {
        lifecycleScope.launch {
            mainViewModel.readRecipes.observeOnce(viewLifecycleOwner, {database ->
                if (database.isNotEmpty()){
                    mAdapter.setData(database[0].foodRecipe)
                    hideShimmerEffect()
                } else {
                    requestApiData()
                }
            })
        }
    }

    // Запрос даты из Апи
    private fun requestApiData(){
        mainViewModel.getRecipes(recipesViewModel.applyQueries())
        mainViewModel.recipesResponse.observe(viewLifecycleOwner, { response ->
            when(response) {
                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    response.data?.let { mAdapter.setData(it) }
                }
                is NetworkResult.Error -> {
                    hideShimmerEffect()
                    loadDataFromCache()
                    Toast.makeText(requireContext(), response.message.toString(), Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading -> {
                    showShimmerEffect()
                }
            }
        })
    }

    private fun loadDataFromCache() {
        lifecycleScope.launch {
            mainViewModel.readRecipes.observe(viewLifecycleOwner, {database ->
                if (database.isNotEmpty()) {
                    mAdapter.setData(database[0].foodRecipe)
                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // Шиммер еффект для фрагмента
    private fun showShimmerEffect() {
        mView.recyclerView.showShimmer()
    }

    private fun hideShimmerEffect() {
        mView.recyclerView.hideShimmer()
    }
}