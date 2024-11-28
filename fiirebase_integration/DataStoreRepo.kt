package com.orange.flowchecklist.data

import kotlinx.coroutines.flow.Flow

interface DataStoreRepo {

    suspend fun saveUserMobileNumber(mobileNumber:String)

    fun getUserMobileNumber(): Flow<String>

}