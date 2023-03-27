package com.lifeutil.jokester.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMessage(message: DBMessage)

    @Query("DELETE FROM message_table")
    suspend fun deleteMessages()

    @Query("SELECT * FROM message_table ORDER BY date")
    fun getMessages(): Flow<List<DBMessage>>
}