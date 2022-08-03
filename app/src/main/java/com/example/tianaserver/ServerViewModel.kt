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

    var serverDefaultLiveData = MutableLiveData<Server?>()

    init {
        getServers()
    }

    fun getServers() {
        serverRepository.getServers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : TianaSingleObserver<List<Server>>(compositeDisposable) {
                override fun onSuccess(t: List<Server>) {
                    serversLiveData.value = t
                    getDefault()
                }
            })

    }

    fun getDefault() {
        serverDefaultLiveData.value = null
        if (serversLiveData.value != null && serversLiveData.value!!.isNotEmpty()) {
            serversLiveData.value!!.forEach {
                if (it.isDefault)
                    serverDefaultLiveData.value = it
            }
            if (serverDefaultLiveData.value == null) {
                var saver = serversLiveData.value!![0]
                updateServer(saver)
            }
        }
    }

    fun addServer(server: Server) {

        if (serverDefaultLiveData.value == null)
            server.isDefault = true
        serverRepository.addServer(
            server
        )
            .subscribeOn(Schedulers.io())
            .doFinally { getServers() }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : TianaCompletableObserver(compositeDisposable) {
                override fun onComplete() {
                    Timber.i("add server")
                }
            }
            )
    }

    fun deleteServer(server: Server) {

        serverRepository.deleteServer(
            server
        )
            .subscribeOn(Schedulers.io())
            .doFinally {
                getServers()

            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : TianaCompletableObserver(compositeDisposable) {
                override fun onComplete() {
                }
            }
            )
    }

    fun updateServer(server: Server) {

        server.isDefault = true
        serverRepository.updateServer(
            server
        )
            .subscribeOn(Schedulers.io())
            .doFinally {
                if (serverDefaultLiveData.value==null)
                    serverDefaultLiveData.postValue(
                        server
                    )
                else{
                  serverDefaultLiveData.value!!.isDefault=false
                    serverRepository.updateServer(serverDefaultLiveData.value!!)
                        .doFinally {
                            getServers()
                        }
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(object : TianaCompletableObserver(compositeDisposable) {
                            override fun onComplete() {
                            }
                        }
                        )
                }



            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : TianaCompletableObserver(compositeDisposable) {
                override fun onComplete() {
                }
            }
            )


    }


}