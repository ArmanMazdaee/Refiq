package com.eth.refiq.domain

interface Web3Repository {
    suspend fun createWallet(password: String): String
    suspend fun isWalletCreated():Boolean
    suspend fun loadWallet(password: String)

    suspend fun getBalance():String

    suspend fun getAddress():String

    suspend fun saveWallet()
    suspend fun importWallet(secretPhrase: String, password: String)
}