package com.example.dogapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dogapp.data.ApiService
import com.example.dogapp.data.DogDatabase
import com.example.dogapp.data.DogRepository
import com.example.dogapp.databinding.FragmentDogListBinding
import com.example.dogapp.ui.adapter.DogAdapter
import com.example.dogapp.viewmodel.DogViewModel
import com.example.dogapp.viewmodel.DogViewModelFactory

class DogListFragment : Fragment() {

    private var _binding: FragmentDogListBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: DogViewModel
    private lateinit var adapter: DogAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDogListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializar ViewModel
        val database = DogDatabase.getDatabase(requireContext())
        val repository = DogRepository(apiService = ApiService.create(), dogDao = database.dogDao())
        val viewModelFactory = DogViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(DogViewModel::class.java)

        // Configurar RecyclerView
        adapter = DogAdapter(emptyList(), viewModel, findNavController())
        binding.recyclerViewDogs.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewDogs.adapter = adapter

        // Cargar imágenes de perros y observar cambios
        viewModel.loadDogImages("hound")
        viewModel.dogImages.observe(viewLifecycleOwner) { images ->
            adapter.updateData(images)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
