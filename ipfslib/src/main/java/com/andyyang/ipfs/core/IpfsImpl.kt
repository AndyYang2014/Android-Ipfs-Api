package com.andyyang.ipfs.core

import io.reactivex.Single
import com.andyyang.ipfs.entity.IpfsVersion
import java.io.File

/**
 *Created by liuyang on 2018/9/7.
 */
interface IpfsImpl {

    fun init(baseUrl: String)

    fun init(host: String, port: String)

    fun creatUrl(host: String, port: String):String

    fun download(token: String): Single<ByteArray>

    fun upload(file: File): Single<String>

    fun checkVersion(host: String, port: String): Single<IpfsVersion>

}