package com.example.tiana.data.repository

import com.example.tiana.data.Model.Banner
import io.reactivex.Single

interface BannerRepository {
    fun getBanners(): Single<List<Banner>>

}