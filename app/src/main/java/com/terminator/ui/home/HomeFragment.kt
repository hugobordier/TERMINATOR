package com.terminator.ui.home

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.terminator.DetailArticle
import com.terminator.PRODUCT_DATA
import com.terminator.ProductAdapter
import com.terminator.databinding.FragmentHomeBinding
import com.terminator.model.Product
import com.terminator.viewmodel.ProductViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var productViewModel: ProductViewModel
    private lateinit var productAdapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialiser le ViewModel
        productViewModel = ViewModelProvider(this)[ProductViewModel::class.java]

        setupRecyclerView()
        setupSearchBar()
        observeViewModel()

        // Charger les produits
        productViewModel.loadAllProducts()
    }

    private fun setupRecyclerView() {
        binding.listArticles.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )

        // Initialiser l'adapter avec une liste vide
        productAdapter = ProductAdapter(mutableListOf()) { product ->
            val intent = Intent(requireContext(), DetailArticle::class.java).apply {
                putExtra(PRODUCT_DATA, product)
            }
            startActivity(intent)
        }
        binding.listArticles.adapter = productAdapter
    }

    private fun setupSearchBar() {
        binding.articleEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                productAdapter.filter(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun observeViewModel() {
        productViewModel.products.observe(viewLifecycleOwner) { products ->
            productAdapter.setOriginalProducts(products)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}