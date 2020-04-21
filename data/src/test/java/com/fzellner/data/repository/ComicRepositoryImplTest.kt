package com.fzellner.data.repository

import com.fzellner.comics.domain.exception.ComicException
import com.fzellner.data.api.MarvelApi
import com.fzellner.data.domain.exception.ForbiddenException
import com.fzellner.data.domain.exception.InvalidApiKeyException
import com.fzellner.data.domain.exception.MissingApiKeyException
import com.fzellner.data.domain.exception.NotAllowedException
import com.fzellner.data.domain.model.*
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class ComicRepositoryImplTest {


    companion object {
        private const val HERO_ID = 1000L
        private const val DESIRED_VALUE = 50F
        private const val DESIRED_THUMBNAIL = "path.ext"
        private val mostExpensiveComic = ComicResult(
            2,
            "Comic 3",
            "Comic description",
            ComicThumbnail("path", "ext"),
            mutableListOf(
                ComicPrice(50f),
                ComicPrice(40f)
            )
        )

        private val comicResponse = ComicResponse(
            ComicData(
                mutableListOf(
                    mostExpensiveComic,

                    ComicResult(
                        0,
                        "Comic 1",
                        "Comic description",
                        ComicThumbnail("path", "ext"),
                        mutableListOf(
                            ComicPrice(10f)
                        )
                    ),
                    ComicResult(
                        1,
                        "Comic 2",
                        "Comic description",
                        ComicThumbnail("path", "ext"),
                        mutableListOf(
                            ComicPrice(30f)
                        )
                    )
                )
            )
        )

        private const val MEDIA_TYPE = "application/json"
        private const val MISSING_API_ERROR_CODE = 409
        private const val INVALID_API_ERROR_CODE = 401
        private const val METHOD_NOT_ALLOWED_API_ERROR_CODE = 405
        private const val FORBIDDEN_API_ERROR_CODE = 403
    }

    @MockK
    lateinit var marvelApi: MarvelApi

    lateinit var repository: ComicRepositoryImpl


    @Before
    fun setup() {
        MockKAnnotations.init(this)
        repository = ComicRepositoryImpl(marvelApi)

    }


    @Test
    fun `repositoryShould call service`() {
        coEvery {
            marvelApi.getComicsByHero(HERO_ID.toString())
        } returns CompletableDeferred(Response.success(200, comicResponse))

        repository.get(HERO_ID)

        coVerify {
            marvelApi.getComicsByHero(HERO_ID.toString())
        }
    }


    @Test
    fun `get should emit the most expensive Comic`() = runBlockingTest {
        coEvery {
            marvelApi.getComicsByHero(HERO_ID.toString())
        } returns CompletableDeferred(Response.success(200, comicResponse))

        repository.get(HERO_ID).collect {
            assert(it?.price == DESIRED_VALUE)
        }
    }


    @Test
    fun `get should emit ComicException on null value`() = runBlockingTest {
        coEvery {
            marvelApi.getComicsByHero(HERO_ID.toString())
        } returns CompletableDeferred(Response.success(200, ComicResponse(null)))

        repository.get(HERO_ID).catch {
            assert(it is ComicException)
        }.collect()
    }


    @Test
    fun `get should map the image path and extension`() = runBlockingTest {
        coEvery {
            marvelApi.getComicsByHero(HERO_ID.toString())
        } returns CompletableDeferred(Response.success(200, comicResponse))

        repository.get(HERO_ID).collect {
            assert(it?.imageUrl == DESIRED_THUMBNAIL)
        }
    }


    @Test
    fun `get on error should be MissingApiKeyException`() = runBlocking {
        coEvery {
            marvelApi.getComicsByHero(HERO_ID.toString())
        } returns CompletableDeferred(
            Response.error(
                MISSING_API_ERROR_CODE, ResponseBody.create(
                    MediaType.get(
                        MEDIA_TYPE
                    ), ""
                )
            )
        )
        repository.get(HERO_ID).catch {
            assert(it is MissingApiKeyException)
        }.collect()
    }

    @Test
    fun `get on error should be InvalidAPIKeyException`() = runBlocking {
        coEvery {
            marvelApi.getComicsByHero(0.toString())
        } returns CompletableDeferred(
            Response.error(
                INVALID_API_ERROR_CODE, ResponseBody.create(
                    MediaType.get(
                        MEDIA_TYPE
                    ), ""
                )
            )
        )
        repository.get(0).catch {
            assert(it is InvalidApiKeyException)
        }.collect()
    }

    @Test
    fun `get on error should be ForbiddenException`() = runBlocking {
        coEvery {
            marvelApi.getComicsByHero(0.toString())
        } returns CompletableDeferred(
            Response.error(
                FORBIDDEN_API_ERROR_CODE, ResponseBody.create(
                    MediaType.get(
                        MEDIA_TYPE
                    ), ""
                )
            )
        )
        repository.get(0).catch {
            assert(it is ForbiddenException)
        }.collect()
    }

    @Test
    fun `get on error should be NotAllowedException`() = runBlocking {
        coEvery {
            marvelApi.getComicsByHero(0.toString())
        } returns CompletableDeferred(
            Response.error(
                METHOD_NOT_ALLOWED_API_ERROR_CODE, ResponseBody.create(
                    MediaType.get(
                        MEDIA_TYPE
                    ), ""
                )
            )
        )
        repository.get(0).catch {
            assert(it is NotAllowedException)
        }.collect()
    }


}