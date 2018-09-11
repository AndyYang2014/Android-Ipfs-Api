package com.andyyang.ipfs.core

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import com.andyyang.ipfs.entity.IpfsVersion
import com.andyyang.ipfs.net.IpfsApi
import com.andyyang.ipfs.net.Network
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

/**
 *Created by liuyang on 2018/9/7.
 */
class IpfsCore private constructor() : IpfsImpl {

    private lateinit var baseUrl: String

    private val api: IpfsApi by lazy {
        Network.createApi(IpfsApi::class.java)
    }

    override fun init(baseUrl: String) {
        this.baseUrl = baseUrl
    }

    override fun init(host: String, port: String) {
        this.baseUrl = creatUrl(host, port)
    }

    override fun creatUrl(host: String, port: String): String {
        return "http://$host:$port/api/v0/"
    }

    override fun download(token: String): Single<ByteArray> {
        val url = baseUrl + "cat?arg=$token"
        return api.download(url)
                .map {
                    it.bytes()
                }
                .subscribeOn(Schedulers.io())
    }

    override fun upload(file: File): Single<String> {
        val body = creatBody(file)
        val url = baseUrl + "add?"
        return api.upload(url, body)
                .map {
                    it.Hash
                }
                .subscribeOn(Schedulers.io())
    }

    override fun checkVersion(host: String, port: String): Single<IpfsVersion> {
        val url = creatUrl(host, port) + "version?number=false&commit=false&repo=false&all=false"
        return api.getVersion(url)
                .subscribeOn(Schedulers.io())
    }

    private fun creatBody(file: File): MultipartBody {
        val requestBody = MultipartBody.Builder().setType(MultipartBody.FORM)
        requestBody.addFormDataPart("file", file.name, RequestBody.create(null, file))
        return requestBody.build()
    }

    companion object {
        private var instance: IpfsCore? = null

        @Synchronized
        fun getInstance(): IpfsImpl {
            return if (null == instance) {
                instance = IpfsCore()
                instance!!
            } else {
                instance!!
            }

        }
    }

}