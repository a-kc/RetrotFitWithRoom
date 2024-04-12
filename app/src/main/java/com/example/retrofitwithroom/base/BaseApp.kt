package com.example.retrofitwithroom.base

import androidx.hilt.work.HiltWorkerFactory
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.multidex.MultiDexApplication
import androidx.room.Room
import androidx.work.Configuration
import com.example.retrofitwithroom.room.AppDatabase

import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class BaseApp : MultiDexApplication(), DefaultLifecycleObserver, Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory
    companion object {
        lateinit var instance: BaseApp
        lateinit var database: AppDatabase

        fun getAppContext(): BaseApp {
            return instance
        }
    }

    override fun onCreate() {
        super<MultiDexApplication>.onCreate()
        instance = this
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "my_database"
        ).build()
    }


    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
    }


    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
}