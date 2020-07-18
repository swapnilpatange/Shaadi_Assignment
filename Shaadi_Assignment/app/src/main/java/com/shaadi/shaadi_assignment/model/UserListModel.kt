package com.shaadi.shaadi_assignment.model

import io.realm.RealmObject
import io.realm.annotations.Ignore
import io.realm.annotations.PrimaryKey

open class UserListModel(
    var gender: String = "",
    var name: NameModel? = null,
    var picture: PictureModel? = null,
    var userStatus: Int = -1,
    @PrimaryKey var email: String = ""
) : RealmObject()