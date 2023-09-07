package com.simon.storeharmonytest.utils

import com.simon.storeharmonytest.data.models.UserProfile
import javax.inject.Inject

class UserProfileInputChecker(private val userProfile: UserProfile) {

    fun isEmailValid(): Boolean{
        val emailRegex = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
        return userProfile.email.isNotEmpty() && emailRegex.matches(userProfile.email)
    }

    fun isFirstNameValid(): Boolean{
        val firstName = userProfile.firstName
        return firstName.length>3
    }

    fun isLastNameValid(): Boolean{
        val lastName = userProfile.lastName
        return lastName.length>3
    }

    fun isCountryValid(): Boolean{
        return checkLength(userProfile.country,2)
    }

    fun isCityValid(): Boolean{
        return  checkLength(userProfile.city,2)
    }

    fun isStateValid(): Boolean{
        return checkLength(userProfile.state,2)
    }

    private fun checkLength(value:String, minimum:Int):Boolean{
        return value.length > minimum
    }

    fun isPhoneValid():Boolean{
        return  checkLength(userProfile.phone,9)
    }

}