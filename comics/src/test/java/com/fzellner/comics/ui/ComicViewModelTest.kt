package com.fzellner.comics.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fzellner.comics.domain.Comic
import com.fzellner.comics.interactor.GetMostExpensiveComic
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ComicViewModelTest {

    companion object {
        const val UI_THREAD = "UI THREAD"
        const val HERO_ID = 1000L
        private val comic = Comic(
            "Title 1",
            "Description 1",
            "path.ext",
            10F

        )

    }

    lateinit var viewModel: ComicViewModel

    @MockK
    private lateinit var useCase: GetMostExpensiveComic

    private val mainThreadSurrogate = newSingleThreadContext(UI_THREAD)

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        Dispatchers.setMain(mainThreadSurrogate)
        MockKAnnotations.init(this)
        viewModel = ComicViewModel(useCase)

        coEvery {
            useCase(GetMostExpensiveComic.Params(HERO_ID))
        } returns flow {
            emit(comic)
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }


    @Test
    fun `comic properties should not be empty`() {
        viewModel.comic.value = comic
        viewModel.comic.observeForever {
            assert(it.title.isNotEmpty())
            assert(it.description.isNotEmpty())
            assert(it.imageUrl.isNotEmpty())
        }

    }

    @Test
    fun `comic price should be greather than zero`() {
        viewModel.comic.value = comic
        viewModel.comic.observeForever {
            assert(it.price > 0)
        }
    }

    @Test
    fun `comic price should be set`() {
        viewModel.comic.value = comic
        viewModel.comic.observeForever {
            assert(it.price == comic.price)
        }
    }

    @Test
    fun `comic title should be set`() {
        viewModel.comic.value = comic
        viewModel.comic.observeForever {
            assert(it.title == comic.title)
        }
    }

    @Test
    fun `comic description should be set`() {
        viewModel.comic.value = comic
        viewModel.comic.observeForever {
            assert(it.description == comic.description)
        }
    }

    @Test
    fun `comic imageUrl should be set`() {
        viewModel.comic.value = comic
        viewModel.comic.observeForever {
            assert(it.imageUrl == comic.imageUrl)
        }
    }

    @Test
    fun `loading should be set`() {
        viewModel.loading.value = true
        viewModel.loading.observeForever {
            assert(it)
        }
    }

    @Test
    fun `get should call iteractor`() {
        viewModel.get(HERO_ID)
        coVerify {
            useCase(GetMostExpensiveComic.Params(HERO_ID))
        }
    }

}