package com.example.tianaserver.data.source.local

import android.text.BoringLayout
import androidx.room.*
import com.example.tianaserver.data.model.Server
import com.example.tianaserver.data.source.ServerDataSource
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface ServerLocalDataSource:ServerDataSource {

    @Query("SELECT * from servers WHERE is_default = 1")
    override fun getDefaultServer(): Single<Server>

    @Query("SELECT * from servers")
    override fun getServers(): Single<List<Server>>

    @Insert
    override fun addServer(server: Server): Completable

    @Update
    override fun updateServer(server: Server): Completable

    @Delete
    override fun deleteServer(server: Server): Completable
}