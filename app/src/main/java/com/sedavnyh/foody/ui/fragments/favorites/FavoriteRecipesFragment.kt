package com.sedavnyh.foody.ui.fragments.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.sedavnyh.foody.R
import com.sedavnyh.foody.adapters.FavoriteRecipesAdapter
import com.sedavnyh.foody.databinding.FragmentFavoriteRecipesBinding
import com.sedavnyh.foody.databinding.FragmentIngredientsBinding
import com.sedavnyh.foody.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

// фрагмент с избранными рецептами
@AndroidEntryPoint
class FavoriteRecipesFragment : Fragment() {

    private var _binding: FragmentFavoriteRecipesBinding? = null
    private val binding get() = _binding!!
    private val mAdapter by lazy { FavoriteRecipesAdapter(requireActivity())  }
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Привязка лейаута и прокидывание модели\адаптера в него
        _binding = FragmentFavoriteRecipesBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel

        // Прокидывание данных из модели в адаптер
        mainViewModel.readFavoriteRecipes.observe(viewLifecycleOwner, {
            mAdapter.setData(it)
        })

        setupRecyclerView()

        return binding.root
    }

    private fun setupRecyclerView() {
        binding.favoriteRecipesRecyclerView.adapter = mAdapter
        binding.favoriteRecipesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}