package com.example.binge.model.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.binge.model.data.Movies

@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(data: List<Movies>)

    @Query("select * from movies")
    suspend fun getAll() : List<Movies>

    @Query("delete from movies")
    suspend fun deleteOldData()

    @Query("select * from movies where id = :movieId")
    fun getMovieById(movieId: Long): Movies?

}