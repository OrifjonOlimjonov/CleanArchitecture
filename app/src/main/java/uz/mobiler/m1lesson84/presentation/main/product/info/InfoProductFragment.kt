package uz.mobiler.m1lesson84.presentation.main.product.info

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import uz.mobiler.m1lesson84.R
import uz.mobiler.m1lesson84.databinding.FragmentInfoProductBinding
import uz.mobiler.m1lesson84.domain.product.models.ProductData
import uz.mobiler.m1lesson84.presentation.main.home.HomeViewModel

@AndroidEntryPoint
class InfoProductFragment : Fragment() {

    private var _binding: FragmentInfoProductBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()
    private val infoViewModel: InfoProductViewModel by viewModels()
    private lateinit var product: ProductData
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInfoProductBinding.inflate(inflater, container, false)
        product = arguments?.getSerializable("product") as ProductData

        setInfo()
        observe()
        binding.btnDelete.setOnClickListener {
            delete()
        }


        setFragmentResultListener("success_update") { requestKey, bundle ->
            if (bundle.getBoolean("success_update")) {
                viewModel.getAllMyProducts()
                val productUpdate = bundle.getSerializable("update") as ProductData
                binding.tvPriceProduct.text = productUpdate.price.toString()
                binding.tvNameProduct.text = productUpdate.name
            }
        }

        return binding.root
    }

    private fun delete() {
        infoViewModel.delete(product.id.toString())
    }

    private fun setInfo() {
        binding.apply {
            tvNameProduct.text = product.name
            tvPriceProduct.text = product.price.toString()
        }
        binding.btnUpdate.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("product", product)
            findNavController().navigate(R.id.updateProductFragment, bundle)
        }
    }

    private fun observe() {
        infoViewModel.state.flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach {
                when (it) {
                    is DeleteProductProcess.Init -> Unit
                    is DeleteProductProcess.IsLoading -> it.isLoading
                    is DeleteProductProcess.ShowToast -> Toast.makeText(
                        requireContext(),
                        it.message,
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    is DeleteProductProcess.SuccessDelete ->{
                        setResultOK()
                        findNavController().navigateUp()
                    }
                }
            }
            .launchIn(lifecycleScope)
    }

    private fun setResultOK() {
        val r = Bundle().apply {
            putBoolean("success_delete", true)
        }
        setFragmentResult("success_delete", r)
    }
}