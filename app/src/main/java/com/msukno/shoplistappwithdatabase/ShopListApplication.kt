package com.msukno.shoplistappwithdatabase

import android.app.Application
import com.msukno.shoplistappwithdatabase.data.AppContainer
import com.msukno.shoplistappwithdatabase.data.AppDataContainer

class ShopListApplication : Application() {

    /**
     * AppContainer instance used by the rest of classes to obtain dependencies
     */
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}