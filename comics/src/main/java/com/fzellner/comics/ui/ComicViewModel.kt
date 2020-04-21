package com.fzellner.comics.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fzellner.comics.domain.Comic
import com.fzellner.comics.interactor.GetMostExpensiveComic
import kotlinx.coroutines.flow.*

class ComicViewModel(private val getMostExpensiveComic: GetMostExpensiveComic) : ViewModel() {


    val comic = MutableLiveData<Comic>()
    val loading = MutableLiveData<Boolean>()
    val error = MutableLiveData<Boolean>()


    fun get(heroId: Long) {

        getMostExpensiveComic(GetMostExpensiveComic.Params(heroId))
            .onStart {
                loading.value = true
            }
            .onEach {
                comic.value = it
            }
            .onCompletion {
                loading.value = false
            }
            .catch{
                error.value = true
            }
            .launchIn(viewModelScope)

    }


}