package com.example.tiana

import android.app.Application
import com.example.tiana.data.implement.BannerRepositoryImplement
import com.example.tiana.data.repository.BannerRepository
import com.example.tiana.data.source.remote.BannerRemoteDataSource
import com.example.tiana.servise.FrescoLoadingServiceImplement
import com.example.tiana.servise.ImageLoadingService
import com.example.tiana.servise.createApiServiceInstance
import com.facebook.drawee.backends.pipeline.Fresco
import org.koin.androidx.viewmodel.dsl.viewModel

import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module
import timber.log.Timber

class App:Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        Fresco.initialize(this)

        val myModules = module {
            single { createApiServiceInstance() }
            single<ImageLoadingService> { FrescoLoadingServiceImplement() }
            factory<BannerRepository> {
                BannerRepositoryImplement(BannerRemoteDataSource(get()))
            }
            viewModel{MainViewModel(get())}
        }

        startKoin {
            androidContext(this@App)
            modules(myModules)
        }

    }
}