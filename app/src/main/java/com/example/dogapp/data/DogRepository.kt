package com.example.dogapp.data

class DogRepository(private val apiService: ApiService, private val dogDao: DogDao) {

    suspend fun fetchDogImages(breed: String): List<String> {
        val response = apiService.getDogsByBreeds("breed/$breed/images")
        return if (response.isSuccessful) response.body()?.images ?: emptyList() else emptyList()
    }

    suspend fun addFavorite(dog: DogEntity) {
        dogDao.insertDog(dog)
    }

    suspend fun removeFavorite(dog: DogEntity) {
        dogDao.deleteDog(dog)
    }

    fun getAllFavorites() = dogDao.getAllDogs()

    // Método para borrar todos los favoritos
    suspend fun deleteAllFavorites() {
        dogDao.deleteAllDogs()
    }
}
