package com.fzellner.heroes.interactor

import com.example.commom.utils.model.Listing
import com.example.commom.utils.model.UseCase
import com.fzellner.heroes.domain.model.Hero
import com.fzellner.heroes.repository.HeroesListRepository
import kotlinx.coroutines.flow.Flow

class GetHeroes(private val repository: HeroesListRepository) :
    UseCase<Listing<Hero>, UseCase.None>() {

    override fun run(params: None): Flow<Listing<Hero>> =
        repository.get()


}