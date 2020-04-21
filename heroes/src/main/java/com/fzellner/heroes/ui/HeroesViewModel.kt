package com.fzellner.heroes.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.commom.utils.model.Listing
import com.example.commom.utils.model.UseCase
import com.fzellner.heroes.domain.model.Hero
import com.fzellner.heroes.interactor.GetHeroes
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class HeroesViewModel(private val repository: GetHeroes) : ViewModel() {

    val repositoryResult = MutableLiveData<Listing<Hero>>()

    val statementList = switchMap(repositoryResult) {
        it.pagedList
    }

    val networkState = switchMap(repositoryResult) {
        it.networkState
    }

    init {
        getHeroes()
    }

    fun getHeroes() {

        repository(UseCase.None)
            .onEach {
                repositoryResult.value = it
            }
            .launchIn(viewModelScope)
    }

}

