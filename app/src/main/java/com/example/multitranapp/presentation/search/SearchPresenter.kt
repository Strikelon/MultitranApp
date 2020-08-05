package com.example.multitranapp.presentation.search

import android.text.TextUtils
import com.example.multitranapp.R
import com.example.multitranapp.entity.TranslateLanguage
import com.example.multitranapp.entity.TranslateQuery
import com.example.multitranapp.model.repositories.TranslateRepository
import com.example.multitranapp.presentation.BasePresenter
import moxy.InjectViewState
import ru.terrakok.cicerone.Router
import timber.log.Timber
import javax.inject.Inject

@InjectViewState
class SearchPresenter @Inject constructor(
    private val router: Router,
    private val translateRepository: TranslateRepository
): BasePresenter<SearchView>() {

    private var query: String? = null
    private var targetLanguage: TranslateLanguage? = null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        Timber.tag("SearchPresenter").i("onFirstViewAttach()")
        viewState.setupSpinnersPositions(TranslateLanguage.RUSSIAN.stringArrayPosition, TranslateLanguage.ENGLISH.stringArrayPosition)
    }

    fun onFirstSpinnerItemSelected(firstSpinnerPosition: Int, secondSpinnerPosition: Int) {
        if (firstSpinnerPosition == secondSpinnerPosition) {
            val correctPosition = getCorrectPosition(firstSpinnerPosition)
            viewState.setSecondSpinnerPosition(correctPosition)
        }
    }

    fun onSecondSpinnerItemSelected(firstSpinnerPosition: Int, secondSpinnerPosition: Int) {
        if (firstSpinnerPosition == secondSpinnerPosition) {
            val correctPosition = getCorrectPosition(secondSpinnerPosition)
            viewState.setFirstSpinnerPosition(correctPosition)
        }
    }

    private fun getCorrectPosition(position: Int): Int =
        if (position == TranslateLanguage.RUSSIAN.stringArrayPosition)
            TranslateLanguage.ENGLISH.stringArrayPosition
        else
            TranslateLanguage.RUSSIAN.stringArrayPosition

    fun onTranslateButtonClick(query: String, firstSpinnerPosition: Int, secondSpinnerPosition: Int) {
        viewState.removeKeyboard()
        if (TextUtils.isEmpty(query)) {
            viewState.showSnackBarMessage(R.string.empty_word_to_translate)
            return
        }

        val sourceLanguage = TranslateLanguage.getLanguage(firstSpinnerPosition)
        val targetLanguage = TranslateLanguage.getLanguage(secondSpinnerPosition)

        if (sourceLanguage == TranslateLanguage.RUSSIAN && query.matches(Regex("^[A-Za-z]+\$"))) {
            viewState.showSnackBarMessage(R.string.enter_the_word_in_cyrillic)
            return
        }
        if (sourceLanguage != TranslateLanguage.RUSSIAN && !query.matches(Regex("^[A-Za-z]+\$"))) {
            viewState.showSnackBarMessage(R.string.enter_the_word_in_latin_characters)
            return
        }

        val translateQuery = TranslateQuery(
            query = query,
            sourceTranslateLanguage = sourceLanguage,
            targetTranslateLanguage = targetLanguage
        )
        Timber.tag("SearchPresenter").i("translateQuery = $translateQuery")
        getTranslate(translateQuery)
    }

    private fun getTranslate(translateQuery: TranslateQuery) {
        query = translateQuery.query
        targetLanguage = translateQuery.targetTranslateLanguage
        translateRepository.getTranslate(translateQuery)
            .doOnSubscribe {viewState.showProgressBar(true)}
            .doOnError {viewState.showProgressBar(false)}
            .doAfterTerminate {viewState.showProgressBar(false)}
            .subscribe(
                this::getTranslateSuccess,
                this::getTranslateError
            ).addToCompositeDisposable()
    }

    private fun getTranslateSuccess(listOfTranslations: List<String>) {
        Timber.tag("SearchPresenter").i("getTranslateSuccess = $listOfTranslations")
        query?.let {
            viewState.showQuery(it)
        }
        targetLanguage?.let {
            viewState.showLanguage(it.threeLetterIdentifier)
        }
        viewState.showEmptyResult(listOfTranslations.isEmpty())
        viewState.showTranslations(listOfTranslations)
    }

    private fun getTranslateError(throwable: Throwable) {
        Timber.tag("SearchPresenter").i("getTranslateError = $throwable")
        viewState.showSnackBarMessage(R.string.network_error)
    }

    fun onRecyclerViewScrolled() {
        viewState.removeKeyboard()
    }

    fun onBackPressed() {
        router.exit()
    }

}