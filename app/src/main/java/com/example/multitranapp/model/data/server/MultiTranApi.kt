package com.example.multitranapp.model.data.server

import android.net.Uri
import com.example.multitranapp.BuildConfig
import com.example.multitranapp.entity.TranslateQuery
import com.example.multitranapp.system.SchedulersProvider
import io.reactivex.Single
import org.jsoup.Jsoup
import timber.log.Timber
import javax.inject.Inject

class MultiTranApi @Inject constructor(
    private val schedulers: SchedulersProvider
) {

    companion object {
        private const val QUERY = "s"
        private const val SOURCE_LANGUAGE = "l1"
        private const val TARGET_LANGUAGE = "l2"
        private const val TRANS_CELL = "td.trans"
    }

    fun getTranslate(translateQuery: TranslateQuery): Single<List<String>> {
        return Single.fromCallable {
            val url = Uri.parse(BuildConfig.API_BASE_URL)
                .buildUpon()
                .appendQueryParameter(QUERY, translateQuery.query)
                .appendQueryParameter(SOURCE_LANGUAGE, translateQuery.sourceTranslateLanguage.id)
                .appendQueryParameter(TARGET_LANGUAGE, translateQuery.targetTranslateLanguage.id)
                .build()
                .toString()

            val doc = Jsoup.connect(url)
                .get()
            val transWords = mutableListOf<String>()
            val elements = doc.select(TRANS_CELL)
            elements.forEach { element ->
                val words = element.text()
                val wordList = words.split(";")
                transWords.addAll(wordList)
            }
            return@fromCallable transWords.toList()
        }
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())
    }

}