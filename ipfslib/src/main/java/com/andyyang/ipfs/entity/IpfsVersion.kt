package com.andyyang.ipfs.entity

/**
 *Created by liuyang on 2018/8/27.
 */

data class IpfsVersion(
    val Version: String,
    val Commit: String,
    val Repo: String,
    val System: String,
    val Golang: String
)