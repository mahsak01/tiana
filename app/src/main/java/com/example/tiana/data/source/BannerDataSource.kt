package com.example.tiana.data.source

import com.example.tiana.data.Model.Banner
import io.reactivex.Single

interface BannerDataSource {


    fun getBanners(): Single<List<Banner>>
}