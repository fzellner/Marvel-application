package com.fzellner.heroes.repository

import com.example.commom.utils.model.Listing
import com.fzellner.heroes.domain.model.Hero
import kotlinx.coroutines.flow.Flow

interface HeroesListRepository {

    fun get(): Flow<Listing<Hero>>
}