package com.example.tianaserver.data.implement

import com.example.tianaserver.data.model.Server
import com.example.tianaserver.data.repository.ServerRepository
import com.example.tianaserver.data.source.ServerDataSource
import io.reactivex.Completable
import io.reactivex.Single

class ServerRepositoryImplement(
    val serverLocalDataSource: ServerDataSource
) : ServerRepository {
    override fun getDefaultServer(): Single<Server> =serverLocalDataSource.getDefaultServer()

    override fun getServers(): Single<List<Server>> =serverLocalDataSource.getServers()

    override fun addServer(server: Server): Completable =serverLocalDataSource.addServer(server)

    override fun updateServer(server: Server): Completable =serverLocalDataSource.updateServer(server)

    override fun deleteServer(server: Server): Completable =serverLocalDataSource.deleteServer(server)
}