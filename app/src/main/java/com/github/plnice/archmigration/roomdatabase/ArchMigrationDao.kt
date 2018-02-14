package com.github.plnice.archmigration.roomdatabase

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.github.plnice.archmigration.model.Message
import io.reactivex.Flowable

@Dao
interface MessageDao {

    @Query("select * from Message")
    fun getAll(): Flowable<List<Message>>

    @Insert
    fun insert(message: Message)

    @Delete
    fun delete(message: Message)

}
