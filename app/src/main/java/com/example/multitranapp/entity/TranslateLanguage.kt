package com.example.multitranapp.entity

import android.os.Parcelable
import java.io.Serializable

enum class TranslateLanguage(val id: String, val stringArrayPosition: Int, val threeLetterIdentifier: String): Serializable {
    RUSSIAN(id = "2", stringArrayPosition = 0, threeLetterIdentifier = "RUS"),
    ENGLISH(id = "1", stringArrayPosition = 1, threeLetterIdentifier = "ENG"),
    GERMAN(id = "3", stringArrayPosition = 2, threeLetterIdentifier = "GER"),
    FRENCH(id = "4", stringArrayPosition = 3, threeLetterIdentifier = "FRE");

    companion object {
        fun getLanguage(position: Int) = values()[position]
    }
}