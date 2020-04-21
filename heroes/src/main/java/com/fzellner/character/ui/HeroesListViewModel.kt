package com.fzellner.character.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.commom.utils.model.Listing
import com.fzellner.character.domain.model.Hero
import com.fzellner.character.interactor.GetHeroes
import com.example.commom.utils.model.UseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class HeroesListViewModel(private val repository: GetHeroes) : ViewModel() {

    val selectedHero = MutableLiveData<Hero>()

    val offset = MutableLiveData<Int>()
    val result = MutableLiveData<Listing<Hero>>()

    val statementList = switchMap(result){
        it.pagedList
    }

    val networkState = switchMap(result){
        it.networkState
    }

      init {
           getHeroes()
       }
       fun getHeroes() {

           repository(UseCase.None)
               .onEach {
                   result.value = it
               }
               .launchIn(viewModelScope)
       }

}

