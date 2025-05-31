package com.terminator.ui.dashboard

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.terminator.DetailArticle
import com.terminator.PANIER
import com.terminator.PRODUCT_DATA
import com.terminator.ProductAdapter
import com.terminator.databinding.FragmentDashboardBinding
import com.terminator.repository.PanierRepository
import kotlinx.coroutines.launch

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadPanier()

        binding.buttonViderPanier.setOnClickListener{
            lifecycleScope.launch {
                val repo = PanierRepository
                repo.vider_panier()
                Toast.makeText(requireContext(), "Panier vidé", Toast.LENGTH_SHORT).show()
                loadPanier()
            }
        }
    }

    private fun loadPanier() {
        lifecycleScope.launch {
            val products = PanierRepository.get_panier()
            val recyclerView = binding.panierRecyclerView
            recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

            // Solution : Convertir la List en MutableList
            recyclerView.adapter = ProductAdapter(products.toMutableList()) { product ->
                val intent = Intent(requireContext(), DetailArticle::class.java).apply {
                    putExtra(PRODUCT_DATA, product)
                    putExtra(PANIER, "frompanier")
                }
                startActivity(intent)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}