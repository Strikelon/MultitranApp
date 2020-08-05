package com.example.multitranapp.entity

import java.io.Serializable

data class TranslateQuery(
    val query: String,
    val sourceTranslateLanguage: TranslateLanguage,
    val targetTranslateLanguage: TranslateLanguage
): Serializable