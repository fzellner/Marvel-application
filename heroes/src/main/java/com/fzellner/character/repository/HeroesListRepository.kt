package com.fzellner.character.repository

import com.example.commom.utils.model.Listing
import com.fzellner.character.domain.model.Hero
import kotlinx.coroutines.flow.Flow

interface HeroesListRepository {

    fun get(): Flow<Listing<Hero>>
}