package com.andyyang.ipfs.core

import io.reactivex.Single
import com.andyyang.ipfs.entity.IpfsVersion
import java.io.File

/**
 *Created by liuyang on 2018/9/7.
 */
object IPFS {

    private val ipfs by lazy {
        IpfsCore.getInstance()
    }

    fun init(baseUrl: String) {
        ipfs.init(baseUrl)
    }

    fun init(host: String, port: String) {
        ipfs.init(host, port)
    }

    private fun creatUrl(host: String, port: String): String {
        return ipfs.creatUrl(host, port)
    }

    fun download(token: String): Single<ByteArray> {
        return ipfs.download(token)
    }

    fun upload(file: File): Single<String> {
        return ipfs.upload(file)
    }

    fun checkVersion(host: String, port: String): Single<IpfsVersion> {
        return ipfs.checkVersion(host, port)
    }

}