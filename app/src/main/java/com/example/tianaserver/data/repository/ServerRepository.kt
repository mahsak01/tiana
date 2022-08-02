package com.example.tianaserver.data.repository

import com.example.tianaserver.data.model.Server
import io.reactivex.Completable
import io.reactivex.Single

interface ServerRepository {

    fun getDefaultServer():Single<Server>

    fun getServers():Single<List<Server>>

    fun addServer(server: Server):Completable

    fun updateServer(server: Server): Completable

    fun deleteServer(server: Server):Completable
}