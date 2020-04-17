package com.fzellner.character.interactor

import kotlinx.coroutines.flow.Flow


abstract class UseCase<out Type, in Params> {
    abstract fun run(params: Params): Flow<Type>

    operator fun invoke(params: Params): Flow<Type> {
        return run(params)
    }

    object None
}