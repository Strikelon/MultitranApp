package com.example.multitranapp.ui.search.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.multitranapp.R
import kotlinx.android.synthetic.main.item_translations.view.*

class TranslationsAdapter(var listOfTranslations: List<String> = listOf()): RecyclerView.Adapter<TranslationsAdapter.TranslationsViewHolder>() {

    class TranslationsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: String, position: Int) {
            val text = "${position+1}. $item"
            itemView.translation_text.text = text
        }
    }

    override fun getItemCount(): Int = listOfTranslations.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TranslationsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_translations, parent, false)
        return TranslationsViewHolder(view)
    }

    override fun onBindViewHolder(holder: TranslationsViewHolder, position: Int) {
        holder.bind(listOfTranslations[position], position)
    }

    fun updateData(updateListOfTranslations: List<String>) {
        listOfTranslations = updateListOfTranslations
        notifyDataSetChanged()
    }
}