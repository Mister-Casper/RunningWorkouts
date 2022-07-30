package com.sgcdeveloper.runwork.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sgcdeveloper.runwork.data.dao.WorkoutDao
import com.sgcdeveloper.runwork.data.model.workout.WorkoutEntity

@Database(entities = [WorkoutEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract val workoutDao: WorkoutDao

}