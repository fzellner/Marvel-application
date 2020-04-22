package com.fzellner.heroes.interactor

import com.example.common.utils.model.Listing
import com.example.common.utils.model.UseCase
import com.fzellner.heroes.domain.model.Hero
import com.fzellner.heroes.repository.HeroesRepository
import kotlinx.coroutines.flow.Flow

class GetAll(private val repository: HeroesRepository) :
    UseCase<Listing<Hero>, UseCase.None>() {

    override fun run(params: None): Flow<Listing<Hero>> =
        repository.getAll()


}