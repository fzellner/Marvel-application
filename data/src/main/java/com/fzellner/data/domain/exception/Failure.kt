package com.fzellner.data.domain.exception

open class Failure : Throwable(){

    object ServerFailure: Failure()

}