package com.example.dogapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogapp.data.DogEntity
import com.example.dogapp.data.DogRepository
import kotlinx.coroutines.launch

class DogViewModel(private val repository: DogRepository) : ViewModel() {
    val favoriteDogs = repository.getAllFavorites()

    // LiveData para almacenar las imágenes de perros
    private val _dogImages = MutableLiveData<List<String>>()
    val dogImages: LiveData<List<String>> get() = _dogImages

    // Método para cargar imágenes desde el repositorio
    fun loadDogImages(breed: String) {
        viewModelScope.launch {
            val images = repository.fetchDogImages(breed)
            _dogImages.value = images
        }
    }

    // Método para agregar un favorito
    fun addFavorite(dog: DogEntity) {
        viewModelScope.launch {
            repository.addFavorite(dog)
        }
    }

    // Método para eliminar todos los favoritos
    fun deleteAllFavorites() {
        viewModelScope.launch {
            repository.deleteAllFavorites()
        }
    }
}
