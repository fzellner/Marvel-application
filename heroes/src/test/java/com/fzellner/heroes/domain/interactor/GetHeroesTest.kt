package com.fzellner.heroes.domain.interactor

import com.example.commom.utils.model.Listing
import com.example.commom.utils.model.UseCase
import com.fzellner.heroes.interactor.GetHeroes
import com.fzellner.heroes.repository.HeroesListRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flow
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.any

class GetHeroesTest {

    @MockK
    lateinit var repository: HeroesListRepository

    lateinit var useCase: GetHeroes


    @Before
    fun setup() {
        MockKAnnotations.init(this)
        useCase = GetHeroes(repository)
        coEvery {
            repository.get()
        } returns flow {
            emit(
                Listing(
                    any(),
                    any(),
                    any(),
                    any(),
                    any()
                )
            )
        }

    }


    @Test
    fun `usecase should call repository`() {

        useCase(UseCase.None)

        coVerify {
            repository.get()
        }

    }


}