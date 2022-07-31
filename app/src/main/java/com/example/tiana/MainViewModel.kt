package com.example.tiana

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tiana.common.TianaSingleObserver
import com.example.tiana.data.Model.Banner
import com.example.tiana.data.repository.BannerRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel(private val bannerRepository: BannerRepository):ViewModel() {

    val compositeDisposable = CompositeDisposable()
    val progressLiveData = MutableLiveData<Boolean>()
    val bannerLiveData= MutableLiveData<List<Banner>>()

    init {
        progressLiveData.value=true
        bannerRepository.getBanners()
            .subscribeOn(Schedulers.io())
            .doFinally { progressLiveData.postValue(false) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : TianaSingleObserver<List<Banner>>(compositeDisposable){
                override fun onSuccess(t: List<Banner>) {
                    bannerLiveData.value=t
                }

            })
    }
    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}