package uz.mobiler.m1lesson84.presentation.main.product.add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import uz.mobiler.m1lesson84.R
import uz.mobiler.m1lesson84.databinding.FragmentAddProductBinding

@AndroidEntryPoint
class AddProductFragment : Fragment() {

    private var _binding:FragmentAddProductBinding?=null
    private var binding = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddProductBinding.inflate(inflater,container,false)





        return binding.root
    }

}