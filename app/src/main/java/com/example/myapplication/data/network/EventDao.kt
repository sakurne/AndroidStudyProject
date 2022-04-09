package com.example.myapplication.data.network

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication.domain.event.Event

@Dao
interface EventDao {

    @Query("select * from event_table")
    fun getAll(): List<Event>

    @Query("DELETE FROM event_table")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(event: Event)

    @Query("SELECT * FROM event_table WHERE id = :eventId LIMIT 1")
    fun getEvent(eventId: Long): Event?
}
