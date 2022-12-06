package com.khaledsh.core.di

import android.content.Context
import com.khaledsh.core.di.modules.ContextModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ContextModule::class
    ]
)
interface CoreComponent {
    fun context(): Context
}