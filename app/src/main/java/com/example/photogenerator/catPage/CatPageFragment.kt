package com.example.photogenerator.catPage

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.photogenerator.R
import com.example.photogenerator.databinding.FragmentCatPageBinding

class CatPageFragment : Fragment() {

    companion object {
        fun newInstance() = CatPageFragment()
    }

    private val viewModel: CatPageViewModel by lazy{
        ViewModelProvider(this).get(CatPageViewModel::class.java)
    }

    private var _binding: FragmentCatPageBinding? = null
    private val binding get() = _binding!!
    private val filename = "myfile"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_cat_page,
            container,
            false
        )
        binding.lifecycleOwner = this
        binding.catModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonGetCat.setOnClickListener{
            viewModel.getCatPhoto()
        }
        binding.buttonSaveCat.setOnClickListener {
            val fileContents = viewModel.photo.value+"\n"
            context?.openFileOutput(filename, Context.MODE_APPEND).use {
                it?.write(fileContents.toByteArray())
            }
            Toast.makeText(context, "Added to Favorites", Toast.LENGTH_SHORT).show()
            viewModel.getCatPhoto()
        }
        binding.exitToMenu.setOnClickListener{
            findNavController().navigate(R.id.action_catPageFragment_to_FirstFragment)
        }
    }

}