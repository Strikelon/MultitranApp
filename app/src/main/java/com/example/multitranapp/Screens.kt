package com.example.multitranapp

import com.example.multitranapp.ui.search.SearchFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

object Screens {

    object SearchScreen : SupportAppScreen() {
        override fun getFragment() =
            SearchFragment()
    }

}