package com.fzellner.heroes.domain.interactor

import com.example.common.utils.model.Listing
import com.example.common.utils.model.UseCase
import com.fzellner.heroes.interactor.GetAll
import com.fzellner.heroes.repository.HeroesRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flow
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.any

class GetAllTest {

    @MockK
    lateinit var repository: HeroesRepository

    lateinit var useCase: GetAll


    @Before
    fun setup() {
        MockKAnnotations.init(this)
        useCase = GetAll(repository)
        coEvery {
            repository.getAll()
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
            repository.getAll()
        }

    }


}