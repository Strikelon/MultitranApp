package com.example.multitranapp.model.repositories

import com.example.multitranapp.entity.TranslateQuery
import com.example.multitranapp.model.data.server.MultiTranApi
import io.reactivex.Single
import javax.inject.Inject

class TranslateRepository @Inject constructor(
    private val multiTranApi: MultiTranApi
) {

    fun getTranslate(translateQuery: TranslateQuery): Single<List<String>> {
        return multiTranApi.getTranslate(translateQuery)
    }

}