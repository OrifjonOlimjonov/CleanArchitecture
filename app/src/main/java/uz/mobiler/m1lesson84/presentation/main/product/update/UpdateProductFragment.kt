package uz.mobiler.m1lesson84.presentation.main.product.update

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import uz.mobiler.m1lesson84.R
import uz.mobiler.m1lesson84.databinding.FragmentUpdateProductBinding

class UpdateProductFragment : Fragment() {

    private var _binding:FragmentUpdateProductBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{
        _binding = FragmentUpdateProductBinding.inflate(inflater,container,false)





        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}