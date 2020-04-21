package com.fzellner.heroes.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.commom.utils.model.UseCase
import com.fzellner.heroes.interactor.GetHeroes
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.mockito.ArgumentMatchers.any


class HeroesViewModelTest {

    companion object {
        const val UI_THREAD = "UI THREAD"
    }

    @MockK
    private lateinit var useCase: GetHeroes
    lateinit var viewModel: HeroesViewModel
    private val mainThreadSurrogate = newSingleThreadContext(UI_THREAD)
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()


    @Before
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(mainThreadSurrogate)
        viewModel = HeroesViewModel(useCase)

        coEvery {
            useCase(UseCase.None)
        } returns flow {
            emit(
               any()
            )
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }


//    @Test
//    fun `getHeroes should call usecase`(){
//        viewModel.getHeroes()
//        coVerify {
//            useCase(UseCase.None)
//        }
//    }
}
