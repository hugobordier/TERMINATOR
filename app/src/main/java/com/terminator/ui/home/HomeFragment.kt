package com.terminator.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.terminator.DetailArticle
import com.terminator.PRODUCT_DATA
import com.terminator.ProductAdapter
import com.terminator.databinding.FragmentHomeBinding
import com.terminator.repository.ProductRepository
import kotlinx.coroutines.runBlocking
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView =binding.listArticles

        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
        lifecycleScope.launch {
            val repository = ProductRepository()
            val products = repository.getAllProducts()

            recyclerView.adapter = ProductAdapter(products) { product ->
                val intent = Intent(requireContext(), DetailArticle::class.java).apply {
                    putExtra(PRODUCT_DATA, product)
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


//    @RequiresApi(Build.VERSION_CODES.R)
//    fun hideSystemBars() {
//        val controller = requireActivity().window.insetsController
//        if (controller != null) {
//            controller.hide(WindowInsets.Type.systemBars())
//            controller.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
//        }
//    }
//    @RequiresApi(Build.VERSION_CODES.R)
//    override fun onResume() {
//        super.onResume()
//        hideSystemBars()
//    }
//}