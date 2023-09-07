package com.simon.storeharmonytest.data.sources.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class DataStoreUtils @Inject constructor(private val prefsDataStore: DataStore<Preferences>) {

    /**
     * get user profile from datastore
     */
    val profile: Flow<String?> = prefsDataStore.data.map { preferences ->
        preferences[PreferenceKeys.PROFILE_KEY]?:null
    }

    /**
     * Edit user profile
     * @param newProfile the json representation of the user profile object
     */
    suspend fun editProfile(newProfile: String) {
        prefsDataStore.edit { prefs ->
            prefs[PreferenceKeys.PROFILE_KEY] = newProfile
        }
    }

}

object PreferenceKeys {
    var PROFILE_KEY = stringPreferencesKey("profile")
}
