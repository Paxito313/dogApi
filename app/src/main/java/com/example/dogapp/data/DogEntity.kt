package com.example.dogapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dog_table")
data class DogEntity(
    @PrimaryKey val url: String
)
