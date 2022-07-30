package com.sgcdeveloper.runwork.domain.repository

import com.sgcdeveloper.runwork.data.model.workout.WorkoutEntity

interface WorkoutRepository {

    suspend fun insertWorkouts(workoutEntries: List<WorkoutEntity>)

    suspend fun insertWorkout(workoutEntity: WorkoutEntity)

    suspend fun getAllWorkouts(): List<WorkoutEntity>

    suspend fun deleteWorkout(workoutEntity: WorkoutEntity)

    suspend fun deleteWorkoutById(id: Long)

}