package com.example.tianaserver.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tianaserver.data.model.Server
import com.example.tianaserver.data.source.local.ServerLocalDataSource

@Database(entities = [Server::class] , version = 2, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract fun serviceDao(): ServerLocalDataSource
}