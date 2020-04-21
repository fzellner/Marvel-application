package com.fzellner.comics.domain.interactor

import com.fzellner.comics.domain.Comic
import com.fzellner.comics.interactor.GetMostExpensiveComic
import com.fzellner.comics.repository.ComicRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flow
import org.junit.Before
import org.junit.Test

class GetMostExpensiveComicTest {


    companion object {
        const val HERO_ID = 1000L
        private val comic = Comic(
            "Title 1",
            "Description 1",
            "path.ext",
            10F

        )

    }

    @MockK
    lateinit var repository: ComicRepository
    lateinit var useCase: GetMostExpensiveComic


    @Before
    fun setup() {
        MockKAnnotations.init(this)
        useCase = GetMostExpensiveComic(repository)

        coEvery {
            repository.get(HERO_ID)
        } returns flow {
            emit(comic)
        }

    }


    @Test
    fun `usecase should call repository`(){
        useCase(params = GetMostExpensiveComic.Params(HERO_ID))

        coVerify {
            repository.get(HERO_ID)
        }

    }

}