package com.example.tiana.data.source.remote

import com.example.tiana.data.Model.Banner
import com.example.tiana.data.source.BannerDataSource
import com.example.tiana.servise.ApiService
import io.reactivex.Single

class BannerRemoteDataSource(private val apiService: ApiService) : BannerDataSource {
    override fun getBanners(): Single<List<Banner>> = apiService.getBanners()
}