package com.example.tianaserver

import android.app.Application
import androidx.room.Room
import com.example.tianaserver.data.db.AppDatabase
import com.example.tianaserver.data.implement.ServerRepositoryImplement
import com.example.tianaserver.data.repository.ServerRepository
import com.facebook.drawee.backends.pipeline.Fresco
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import timber.log.Timber

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        Fresco.initialize(this)

        val myModules = module {
            single {
                Room.databaseBuilder(this@App, AppDatabase::class.java, "server_db_app")
                    .allowMainThreadQueries().fallbackToDestructiveMigration().build()
            }
            factory<ServerRepository> {
                ServerRepositoryImplement(get<AppDatabase>().serviceDao())
            }
            viewModel{ ServerViewModel(get()) }
        }

        startKoin {
            androidContext(this@App)
            modules(myModules)
        }

    }
}