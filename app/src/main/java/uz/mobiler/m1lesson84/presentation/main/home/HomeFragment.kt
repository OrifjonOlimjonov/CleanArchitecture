package uz.mobiler.m1lesson84.presentation.main.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.mobiler.m1lesson84.R
import uz.mobiler.m1lesson84.databinding.FragmentHomeBinding

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: HomeProductAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        adapter = HomeProductAdapter { productData, i ->

        }
        binding.rvProducts.adapter = adapter
        observe()

        setFragmentResultListener("success_create"){ requestKey, bundle ->
            if(bundle.getBoolean("success_create")){
                viewModel.getAllMyProducts()
            }
        }
        binding.btnCreateProduct.setOnClickListener{
            findNavController().navigate(R.id.addProductFragment)
        }
        return binding.root
    }

    private fun observe() {
        viewModel.products
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach {
                Log.d("Products: ", "observe: $it")
                adapter.submitList(it)
            }.launchIn(lifecycleScope)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}