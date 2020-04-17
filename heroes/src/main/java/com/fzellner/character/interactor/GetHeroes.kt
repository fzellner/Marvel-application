package com.fzellner.character.interactor

import com.fzellner.character.domain.model.Hero
import com.fzellner.character.repository.HeroesListRepository
import kotlinx.coroutines.flow.Flow

class GetHeroes(private val repository: HeroesListRepository) :
    UseCase<List<Hero>, GetHeroes.Params>() {

    override fun run(params: Params): Flow<List<Hero>> =
        repository.get(params.limit, params.offset)


    data class Params(val limit: Int, val offset: Int)

}