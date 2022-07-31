package com.example.tiana.data.implement

import com.example.tiana.data.Model.Banner
import com.example.tiana.data.repository.BannerRepository
import com.example.tiana.data.source.BannerDataSource
import io.reactivex.Single

class BannerRepositoryImplement(private val bannerRemoteDataSource: BannerDataSource) :
    BannerRepository {
    override fun getBanners(): Single<List<Banner>> = bannerRemoteDataSource.getBanners()
}