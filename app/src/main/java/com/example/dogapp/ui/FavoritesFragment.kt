package com.example.dogapp.ui

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dogapp.R
import com.example.dogapp.data.ApiService
import com.example.dogapp.data.DogDatabase
import com.example.dogapp.data.DogRepository
import com.example.dogapp.databinding.FragmentFavoritesBinding
import com.example.dogapp.ui.adapter.DogAdapter
import com.example.dogapp.viewmodel.DogViewModel
import com.example.dogapp.viewmodel.DogViewModelFactory

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: DogViewModel
    private lateinit var adapter: DogAdapter
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializar ViewModel
        val database = DogDatabase.getDatabase(requireContext())
        val repository = DogRepository(apiService = ApiService.create(), dogDao = database.dogDao())
        val viewModelFactory = DogViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(DogViewModel::class.java)

        // Configurar RecyclerView de favoritos
        adapter = DogAdapter(emptyList(), viewModel)
        binding.recyclerViewFavorites.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewFavorites.adapter = adapter

        // Observar los favoritos
        viewModel.favoriteDogs.observe(viewLifecycleOwner) { favorites ->
            adapter.updateData(favorites.map { it.url })
        }

        // Inicializar el MediaPlayer con el sonido de eliminar
        mediaPlayer = MediaPlayer.create(requireContext(), R.raw.delete_sound)

        // Configurar el botón para eliminar todos los favoritos
        binding.btnDeleteAllFavorites.setOnClickListener {
            viewModel.deleteAllFavorites()
            Toast.makeText(requireContext(), "QUE ARDAN EN EL INFIERNO!!!", Toast.LENGTH_SHORT).show()
            mediaPlayer.start()  // Reproducir el sonido
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        mediaPlayer.release()  // Liberar el MediaPlayer
    }
}
