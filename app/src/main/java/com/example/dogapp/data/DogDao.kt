package com.example.dogapp.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DogDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertDog(dog: DogEntity)

    @Delete
    suspend fun deleteDog(dog: DogEntity)

    @Query("SELECT * FROM dog_table")
    fun getAllDogs(): LiveData<List<DogEntity>>

    // Método para eliminar todos los registros
    @Query("DELETE FROM dog_table")
    suspend fun deleteAllDogs()
}
