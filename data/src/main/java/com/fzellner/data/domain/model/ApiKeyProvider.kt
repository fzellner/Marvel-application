package com.fzellner.data.domain.model

import java.io.UnsupportedEncodingException
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*
import kotlin.experimental.and
import kotlin.experimental.or


data class ApiKeyProvider(var apiKey: String, var privateKey: String,var hash: String = getMd5Key(apiKey, privateKey)?:"")


private fun getTimeStamp(): String = (Calendar.getInstance().timeInMillis/1000).toString()

/*
private fun getMd5Key(apiKey: String, privateKey: String): String? {
    var hash: String = ""
    val input: String =
        getTimeStamp() + privateKey + apiKey
    try {
        val md: MessageDigest = MessageDigest.getInstance("MD5")
        val md5Bytes: ByteArray = md.digest(input.toByteArray())
        val md5 = StringBuilder()
        for (i in md5Bytes.indices) {
            md5.append(
                Integer.toHexString(i).substring(
                    1,
                    3
                )
            )
        }
        hash = md5.toString()
    } catch (e: NoSuchAlgorithmException) {
        e.printStackTrace()
    }
    return hash
}*/

fun getMd5Key(apiKey: String, privateKey: String): String?  {

        val input =   getTimeStamp() + privateKey + apiKey
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
}

