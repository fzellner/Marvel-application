package com.fzellner.character.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fzellner.character.interactor.GetHeroes
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class HeroesListViewModel(private val getHeroes: GetHeroes) : ViewModel() {

    companion object {
        const val LIMIT = 20
    }

    val total = MutableLiveData<Int>()


    fun get() {
        getHeroes(GetHeroes.Params(LIMIT, 20))
            .onEach {
                Log.d("FOI", "FOI")
                total.value = it.size
            }.launchIn(viewModelScope)


    }

}
