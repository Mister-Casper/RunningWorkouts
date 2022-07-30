package com.sgcdeveloper.runwork.data.dao

import androidx.room.TypeConverter
import com.sgcdeveloper.runwork.data.model.workout.Mood

class MoodConverter {
    @TypeConverter
    fun fromMood(mood: Mood): String {
        return mood.name
    }

    @TypeConverter
    fun toMood(mood: String): Mood {
        return Mood.valueOf(mood)
    }
}