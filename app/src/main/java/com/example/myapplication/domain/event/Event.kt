package com.example.myapplication.domain.event

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "event_table")
data class Event(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val name: String,
    val startDate: String,
    val endDate: String,
    val address: String,
    val phone: String,
    val description: String,
    val photos: ArrayList<String>,
    val category: String,
    val createAt: Long,
    val organisation: String,
    val status: Long
) : Parcelable
