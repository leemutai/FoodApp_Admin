package com.example.adminwaveoffood.model

import android.location.Address
import android.provider.ContactsContract.CommonDataKinds.Phone

data class UserModel(
    val name: String? = null,
    val nameOfRestaurant: String? = null,
    val email: String? = null,
    val password: String? = null,
    var address:String? = null,
    var phone: String? = null,

)
