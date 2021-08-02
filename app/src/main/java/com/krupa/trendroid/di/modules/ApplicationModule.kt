package com.krupa.trendroid.di.modules

import android.app.Application
import com.krupa.trendroid.MyApplication
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule {
    private val myApplication: MyApplication

    constructor(myApplication: MyApplication) {
        this.myApplication = myApplication
    }

    @Provides
    fun provideMyApplication(): MyApplication {
        return myApplication
    }

    @Provides
    fun provideApplication(): Application {
        return myApplication
    }
}