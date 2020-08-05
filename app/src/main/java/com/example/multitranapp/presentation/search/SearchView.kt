package com.example.multitranapp.presentation.search

import androidx.annotation.StringRes
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface SearchView: MvpView {

    @StateStrategyType(SkipStrategy::class)
    fun showSnackBarMessage(@StringRes messageId : Int)

    fun setupSpinnersPositions(firstSpinnerPosition: Int, secondSpinnerPosition: Int)

    fun setFirstSpinnerPosition(position: Int)

    fun setSecondSpinnerPosition(position: Int)

    fun showProgressBar(visible: Boolean)

    fun showQuery(query: String)

    fun showLanguage(language: String)

    fun showTranslations(listOfTranslations: List<String>)

    fun removeKeyboard()

    fun showEmptyResult(visible: Boolean)
}