package com.fzellner.character.repository

import com.fzellner.character.domain.model.Hero
import kotlinx.coroutines.flow.Flow

interface HeroesListRepository {

    fun get(limit:Int, offset:Int): Flow<List<Hero>>
}