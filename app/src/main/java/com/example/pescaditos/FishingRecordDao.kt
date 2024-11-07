package com.example.pescaditos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FishingRecordDao {
    @Insert
    fun insertFishingRecord(record: FishingRecord)

    @Query("SELECT COUNT(*) FROM FishingRecord WHERE userId = :userId")
    fun getFishRecordCount(userId: Int): Int

    @Query("SELECT * FROM FishingRecord WHERE userId = :userId")
    fun getFishRecordsForUser(userId: Int): List<FishingRecord>
}