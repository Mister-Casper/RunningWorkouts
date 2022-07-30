package com.sgcdeveloper.runwork.data.repository

import com.sgcdeveloper.runwork.data.AppDatabase
import com.sgcdeveloper.runwork.data.model.workout.WorkoutEntity
import com.sgcdeveloper.runwork.domain.repository.WorkoutRepository

class WorkoutRepositoryImpl(private val appDatabase: AppDatabase) :
    WorkoutRepository {
    override suspend fun insertWorkouts(workoutEntries: List<WorkoutEntity>) {
        appDatabase.workoutDao.insertWorkoutEntries(workoutEntries)
    }

    override suspend fun insertWorkout(workoutEntity: WorkoutEntity) {
        appDatabase.workoutDao.insertWorkoutEntry(workoutEntity)
    }

    override suspend fun getAllWorkouts(): List<WorkoutEntity> {
        return appDatabase.workoutDao.getAllWorkoutEntries()
    }

    override suspend fun deleteWorkout(workoutEntity: WorkoutEntity) {
        appDatabase.workoutDao.deleteWorkoutEntryById(workoutEntity.workoutId)
    }

    override suspend fun deleteWorkoutById(id: Long) {
        appDatabase.workoutDao.deleteWorkoutEntryById(id)
    }
}