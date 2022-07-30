package com.sgcdeveloper.runwork.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sgcdeveloper.runwork.data.model.workout.WorkoutEntity
import retrofit2.http.DELETE

@Dao
interface WorkoutDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkoutEntries(workouts: List<WorkoutEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkoutEntry(workout: WorkoutEntity)

    @Query("SELECT * FROM WorkoutEntity")
    suspend fun getAllWorkoutEntries(): List<WorkoutEntity>

    @Query("DELETE FROM WorkoutEntity WHERE workoutId = :workoutId")
    suspend fun deleteWorkoutEntryById(workoutId: Long)
}