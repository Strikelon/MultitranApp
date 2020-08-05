package com.example.multitranapp.ui.search

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.multitranapp.R
import com.example.multitranapp.extensions.hideKeyboard
import com.example.multitranapp.extensions.showSnackMessage
import com.example.multitranapp.extensions.visible
import com.example.multitranapp.presentation.search.SearchPresenter
import com.example.multitranapp.presentation.search.SearchView
import com.example.multitranapp.ui.BaseFragment
import com.example.multitranapp.ui.search.adapter.TranslationsAdapter
import kotlinx.android.synthetic.main.fragment_search.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter


class SearchFragment : BaseFragment(), SearchView {
    override val layoutRes = R.layout.fragment_search

    @InjectPresenter
    lateinit var presenter : SearchPresenter

    @ProvidePresenter
    fun providePresenter() : SearchPresenter =
        scope.getInstance(SearchPresenter::class.java)

    private val adapterTranslations: TranslationsAdapter by lazy {
        TranslationsAdapter()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupUI()
        setupRecyclerView()
    }

    private fun setupUI() {
        word_edit_text.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                onPressSearchEvent()
            }

            return@setOnEditorActionListener false
        }
        first_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {}

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, selectedId: Long) {
                presenter.onFirstSpinnerItemSelected(position, second_spinner.selectedItemPosition)
            }

        }
        second_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {}

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, selectedId: Long) {
                presenter.onSecondSpinnerItemSelected(first_spinner.selectedItemPosition, position)
            }

        }
        translate_button.setOnClickListener {
            onPressSearchEvent()
        }
    }

    private fun onPressSearchEvent() {
        val word = word_edit_text.text.toString().trim()
        presenter.onTranslateButtonClick(word, first_spinner.selectedItemPosition, second_spinner.selectedItemPosition)
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = RecyclerView.VERTICAL
        translation_recycler_view.layoutManager = layoutManager
        translation_recycler_view.adapter = adapterTranslations
        translation_recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == 1) {
                    presenter.onRecyclerViewScrolled()
                }
            }
        })
    }

    override fun setupSpinnersPositions(firstSpinnerPosition: Int, secondSpinnerPosition: Int) {
        first_spinner.setSelection(firstSpinnerPosition)
        second_spinner.setSelection(secondSpinnerPosition)
    }

    override fun setFirstSpinnerPosition(position: Int) {
        first_spinner.setSelection(position)
    }

    override fun setSecondSpinnerPosition(position: Int) {
        second_spinner.setSelection(position)
    }

    override fun showSnackBarMessage(messageId: Int) {
        showSnackMessage(getString(messageId))
    }

    override fun showProgressBar(visible: Boolean) {
        progress_bar.visible(visible)
    }

    override fun showQuery(query: String) {
        val text = "${getString(R.string.your_query)} $query"
        query_tv.text = text
    }

    override fun showLanguage(language: String) {
        val text = "${getString(R.string.target_language)} $language"
        language_tv.text = text
    }

    override fun showTranslations(listOfTranslations: List<String>) {
        adapterTranslations.updateData(listOfTranslations)
    }

    override fun showEmptyResult(visible: Boolean) {
        empty_result_tv.visible(visible)
    }

    override fun removeKeyboard() {
        activity?.hideKeyboard()
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }

}