package com.example.photogenerator.favoritesPage

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.photogenerator.R

class FavoritesFragment : Fragment() {

    companion object {
        fun newInstance() = FavoritesFragment()
    }
    private var listOfDogs: MutableList<String>? = null;

    private lateinit var viewModel: FavoritesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FavoritesViewModel::class.java)

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val exitToMenu: ImageButton = view.findViewById(R.id.exitToMenu);
        listOfDogs =
            context?.openFileInput("myfile")?.bufferedReader()?.readLines() as MutableList<String>?;
        val myRecycler : RecyclerView = view.findViewById(R.id.myRecyclerView)
        myRecycler.adapter = context?.let { listOfDogs?.let { it1 -> PhotoRecyclerAdapter(it, it1) } }
        myRecycler.layoutManager = LinearLayoutManager(context)

        exitToMenu.setOnClickListener{
            findNavController().navigate(R.id.action_favoritesFragment_to_FirstFragment)
        }
    }
}