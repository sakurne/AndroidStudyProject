package com.example.myapplication.data.network

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication.domain.eventCategory.EventCategory

@Dao
interface EventCategoryDao {

    @Query("select * from event_category_table")
    fun getAll(): List<EventCategory>

    @Query("DELETE FROM event_category_table")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(eventCategory: EventCategory)
}
