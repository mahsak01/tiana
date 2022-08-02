package com.example.tianaserver

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tianaserver.common.TianaCompletableObserver
import com.example.tianaserver.common.TianaSingleObserver
import com.example.tianaserver.data.model.Server
import com.example.tianaserver.data.repository.ServerRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class ServerViewModel(val serverRepository: ServerRepository) : ViewModel() {

    val compositeDisposable = CompositeDisposable()

    var serversLiveData = MutableLiveData<List<Server>>()

    var serverDefaultLiveData = MutableLiveData<Server>()

    init {
        getServer()
    }
    fun getServer() {
        serverRepository.getServers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : TianaSingleObserver<List<Server>>(compositeDisposable) {
                override fun onSuccess(t: List<Server>) {
                    serversLiveData.value = t
                }
            })

        serverRepository.getDefaultServer()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : TianaSingleObserver<Server>(compositeDisposable) {
                override fun onSuccess(t: Server) {
                    serverDefaultLiveData.value = t
                }
            })
    }

    fun addServer(server: Server) {

        server.isDefault = (serverDefaultLiveData.value == null)
        serverRepository.addServer(
            server
        )
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : TianaCompletableObserver(compositeDisposable) {
                override fun onComplete() {
                    Timber.i("add server")
                }
            }
            )
        getServer()
    }

    fun deleteServer(server: Server) {

        server.isDefault = (serverDefaultLiveData.value == null)
        serverRepository.deleteServer(
            server
        )
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : TianaCompletableObserver(compositeDisposable) {
                override fun onComplete() {
                    Timber.i("delete server")
                }
            }
            )
        getServer()
    }

    fun updateServer(server: Server) {

        server.isDefault = true
        serverRepository.updateServer(
            server
        )
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : TianaCompletableObserver(compositeDisposable) {
                override fun onComplete() {
                    Timber.i("change server")
                }
            }
            )
        serverDefaultLiveData.value?.let {
            it.isDefault = false
            serverRepository.updateServer(
                it
            )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : TianaCompletableObserver(compositeDisposable) {
                    override fun onComplete() {
                        Timber.i("change server")
                    }
                }
                )
        }
        serverDefaultLiveData.value=server
        getServer()
    }


}