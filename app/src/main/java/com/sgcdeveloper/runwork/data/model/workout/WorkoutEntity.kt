package com.sgcdeveloper.runwork.data.model.workout

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class WorkoutEntity(
    @PrimaryKey(autoGenerate = true) val workoutId: Long,
    val workoutPic: String,
    val workoutPlace: PLace,
    val workoutNote: String,
    val avgUserHeartRate: Int,
    val maxUserHeartRate: Int,
    val userMood: Mood,
    val weatherTemp: Int,
    val weather: Weather,
    val startTime: Long,
    val endTime: Long,
    val distance: Int
)