package com.fzellner.heroes.repository

import com.example.common.utils.model.Listing
import com.fzellner.heroes.domain.model.Hero
import kotlinx.coroutines.flow.Flow

interface HeroesRepository {

    fun getAll(): Flow<Listing<Hero>>
}