package uz.mobiler.m1lesson84.presentation.main.product.update

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.mobiler.m1lesson84.data.product.remote.models.ProductUpdateRequest
import uz.mobiler.m1lesson84.databinding.FragmentUpdateProductBinding
import uz.mobiler.m1lesson84.domain.product.models.ProductData

@AndroidEntryPoint
class UpdateProductFragment : Fragment() {

    private var _binding: FragmentUpdateProductBinding? = null
    private val binding get() = _binding!!
    private lateinit var product:ProductData
    private val viewModel:UpdateProductViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUpdateProductBinding.inflate(inflater, container, false)
        product = arguments?.getSerializable("product") as ProductData
        observe()
        update()


        binding.apply {
            etNameProduct.setText(product.name)
            etPriceProduct.setText(product.price.toString())
            btnUpdate.setOnClickListener {
                update()
            }
        }
        return binding.root
    }

    private fun observe() {
        viewModel.state.flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { state -> handleState(state) }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }
    private fun handleState(state: UpdateProductProcess) {
        when (state) {
            is UpdateProductProcess.SuccessUpdate -> {
                setResultOkToPreviousFragment()
                findNavController().navigateUp()
            }
            is UpdateProductProcess.Init -> Unit
            is UpdateProductProcess.ShowToast -> {
                Toast.makeText(
                    requireContext(),
                    state.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
            is UpdateProductProcess.IsLoading -> handleLoading(state.isLoading)
        }
    }

    private fun setResultOkToPreviousFragment() {
        val r = Bundle().apply {
            putBoolean("success_update", true)
        }
        setFragmentResult("success_update", r)
    }

    private fun handleLoading(isLoading: Boolean) {
        binding.btnUpdate.isEnabled = !isLoading
    }

    private fun update() {
            val name = binding.etNameProduct.text.toString().trim()
            val price = binding.etPriceProduct.text.toString().trim()
            if (validate(name)) {
                viewModel.update(ProductUpdateRequest(name, price.toInt()), product.id.toString())
            }
    }

    private fun validate(name: String): Boolean {
        return name.isNotEmpty()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}