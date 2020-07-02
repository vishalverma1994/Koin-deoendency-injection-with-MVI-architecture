package com.mvisample

import android.app.Application
import com.mvisample.di.module.appModule
import com.mvisample.di.module.viewModelModule
import com.mvisample.di.module.repoModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

@ExperimentalCoroutinesApi
class MVIApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MVIApplication)
            modules(listOf(appModule, repoModule, viewModelModule))
        }
    }
}