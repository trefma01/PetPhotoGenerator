package com.example.photogenerator.dogPage

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.photogenerator.R
import com.example.photogenerator.databinding.FragmentDogPageBinding

class DogPageFragment : Fragment() {

    companion object {
        fun newInstance() = DogPageFragment()
    }

    private val viewModel: DogPageViewModel by lazy{
        ViewModelProvider(this).get(DogPageViewModel::class.java)
    }

    private var _binding: FragmentDogPageBinding? = null
    private val binding get() = _binding!!
    private val filename = "myfile"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_dog_page,
            container,
            false
        )
        binding.lifecycleOwner = this
        binding.dogModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonGetDog.setOnClickListener{
            viewModel.getDogPhoto()
        }
        binding.buttonSaveDog.setOnClickListener {
            val fileContents = viewModel.photo.value+"\n"
            context?.openFileOutput(filename, Context.MODE_APPEND).use {
                it?.write(fileContents.toByteArray())
            }
            Toast.makeText(context, "Added to Favorites", Toast.LENGTH_SHORT).show()
            viewModel.getDogPhoto()
        }
        binding.exitToMenu.setOnClickListener{
            findNavController().navigate(R.id.action_dogPageFragment2_to_FirstFragment)
        }
    }

}