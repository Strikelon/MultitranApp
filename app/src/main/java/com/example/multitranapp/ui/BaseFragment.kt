package com.example.multitranapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.multitranapp.di.DI
import moxy.MvpAppCompatFragment
import toothpick.Scope
import toothpick.Toothpick

abstract class BaseFragment: MvpAppCompatFragment() {

    abstract val layoutRes: Int

    protected lateinit var scope: Scope
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        initScope()
        super.onCreate(savedInstanceState)
    }

    private fun initScope() {
        scope = Toothpick.openScopes(DI.APP_SCOPE)
        Toothpick.inject(this, scope)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        inflater.inflate(layoutRes, container, false)


    open fun onBackPressed() {}

}