package com.example.multitranapp.di

import com.example.multitranapp.model.data.server.MultiTranApi
import com.example.multitranapp.model.repositories.TranslateRepository
import com.example.multitranapp.system.AppSchedulers
import com.example.multitranapp.system.SchedulersProvider
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import toothpick.config.Module

class AppModule: Module() {

    init {
        val cicerone = Cicerone.create()
        bind(Router::class.java).toInstance(cicerone.router)
        bind(NavigatorHolder::class.java).toInstance(cicerone.navigatorHolder)
        bind(SchedulersProvider::class.java).toInstance(AppSchedulers())
        bind(MultiTranApi::class.java).singletonInScope()
        bind(TranslateRepository::class.java).singletonInScope()
    }
}