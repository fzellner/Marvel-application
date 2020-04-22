package com.fzellner.heroes.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.utils.model.Listing
import com.example.common.utils.model.UseCase
import com.fzellner.heroes.domain.model.Hero
import com.fzellner.heroes.interactor.GetAll
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class HeroesViewModel(private val getAll: GetAll) : ViewModel() {

    val repositoryResult = MutableLiveData<Listing<Hero>>()

    val heroList = switchMap(repositoryResult) {
        it.pagedList
    }

    val networkState = switchMap(repositoryResult) {
        it.networkState
    }

    init {
        getHeroes()
    }

    fun getHeroes() {

        getAll(UseCase.None)
            .onEach {
                repositoryResult.value = it
            }
            .launchIn(viewModelScope)
    }

}

