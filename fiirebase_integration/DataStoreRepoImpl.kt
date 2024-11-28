package com.orange.flowchecklist.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.orange.flowchecklist.utils.constant.AppConstants
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreRepoImpl @Inject constructor(@ApplicationContext private val context: Context) : DataStoreRepo {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        name = AppConstants.APP_DATA_STORE
    )

    companion object {
        val REGISTER_MOBILE = stringPreferencesKey(AppConstants.REGISTERED_MOBILE)
    }

    override suspend fun saveUserMobileNumber(mobileNumber: String) {
        context.dataStore.edit { preferences ->
            preferences[REGISTER_MOBILE] = mobileNumber
        }
    }

    override fun getUserMobileNumber() = context.dataStore.data.map {
        it[REGISTER_MOBILE] ?: ""
    }

}