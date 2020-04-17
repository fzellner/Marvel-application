package com.fzellner.character.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fzellner.character.interactor.GetHeroes
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class CharacterListViewModel(private val getHeroes: GetHeroes) : ViewModel() {

    companion object {
        const val LIMIT = 20
    }


    fun get() {
        getHeroes(GetHeroes.Params(LIMIT, 20))
            .onEach {
                Log.d("FOI", "FOI")
            }.launchIn(viewModelScope)


    }

}
