package com.example.myapplication.domain.eventCategory

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "event_category_table")
data class EventCategory(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val name: String
) : Parcelable
