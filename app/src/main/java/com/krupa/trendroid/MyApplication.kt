package com.krupa.trendroid

import android.app.Application
import com.krupa.trendroid.di.*
import com.krupa.trendroid.di.modules.ApplicationModule
import com.krupa.trendroid.di.modules.MainModule
import com.krupa.trendroid.di.modules.RetroModule
import com.krupa.trendroid.di.modules.RoomModule

class MyApplication : Application() {
    private lateinit var trendDroidComponent: TrendDroidComponent

    override fun onCreate() {
        super.onCreate()
        trendDroidComponent = DaggerTrendDroidComponent.builder()
            .mainModule(MainModule())
            .retroModule(RetroModule())
            .applicationModule(ApplicationModule(this))
            .roomModule(RoomModule(this))
            .build()
    }

    fun getComponent(): TrendDroidComponent {
        return trendDroidComponent
    }
}