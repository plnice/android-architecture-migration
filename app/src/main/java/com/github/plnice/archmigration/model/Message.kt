package com.github.plnice.archmigration.model
import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity
data class Message(
        @PrimaryKey val id: Long?,
        @ColumnInfo(name = "created_at") val createdAt: Date = Date(),
        @ColumnInfo(name = "message") val message: String = "")
