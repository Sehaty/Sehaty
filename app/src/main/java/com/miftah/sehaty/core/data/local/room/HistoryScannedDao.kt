package com.miftah.sehaty.core.data.local.room

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.miftah.sehaty.core.data.local.entity.HistoryScannedEntity

@Dao
interface HistoryScannedDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistoriesScanned(historiesEntity: List<HistoryScannedEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistoryScanned(historyEntity: HistoryScannedEntity)

    @Query("SELECT * FROM historyScanItem")
    fun getAllHistoryScanned(): PagingSource<Int, HistoryScannedEntity>

    @Query("SELECT * FROM historyScanItem")
    suspend fun getAllHistoriesScanned() : List<HistoryScannedEntity>

    @Query("DELETE FROM historyScanItem")
    suspend fun deleteAllHistoryScanned()

    @Query("SELECT * FROM historyScanItem WHERE id = :id")
    suspend fun findHistoryScanItemWithID(id: Int) : HistoryScannedEntity

    @Query("SELECT * FROM historyScanItem WHERE productName LIKE :search")
    fun searchItem(search: String?): List<HistoryScannedEntity>
}