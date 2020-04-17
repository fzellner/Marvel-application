package com.fzellner.data.domain.exception

open class Failure : Exception(){

    object ServerFailure: Failure()

}