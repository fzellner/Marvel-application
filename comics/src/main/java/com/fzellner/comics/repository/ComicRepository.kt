package com.fzellner.comics.repository

import com.fzellner.comics.domain.Comic
import kotlinx.coroutines.flow.Flow

interface ComicRepository {

    fun get(heroId:Long): Flow<Comic?>

}