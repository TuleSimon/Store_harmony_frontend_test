package com.simon.storeharmonytest.data.models

import com.google.gson.Gson

data class UserProfile(
    val firstName: String,
    val lastName: String,
    val email: String,
    val country:String,
    val city:String,
    val state:String,
    val phone:String
) {
    fun toGsonString(): String {
        return Gson().toJson(this)
    }


}


fun String.toUserProfile():UserProfile?{
    return try {
        Gson().fromJson(this, UserProfile::class.java)
    }
    catch (e:Exception){
        return null
    }
}
