package com.khaledsh.githubapp

import android.app.Application
import com.khaledsh.core.di.CoreComponent
import com.khaledsh.core.di.DaggerCoreComponent
import com.khaledsh.core.di.modules.ContextModule
import com.khaledsh.data.di.RoomModule
import com.khaledsh.githubapp.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class GithubApp : Application(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    lateinit var coreComponent: CoreComponent

    override fun onCreate() {
        super.onCreate()
        initCoreDependencyInjection()
        initAppDependencyInjection()
    }

    private fun initAppDependencyInjection() {
        DaggerAppComponent
            .builder()
            .coreComponent(coreComponent)
            .roomModule(RoomModule(this))
            .build()
            .inject(this)
    }

    private fun initCoreDependencyInjection() {
        coreComponent = DaggerCoreComponent
            .builder()
            .contextModule(ContextModule(this))
            .build()
    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

}