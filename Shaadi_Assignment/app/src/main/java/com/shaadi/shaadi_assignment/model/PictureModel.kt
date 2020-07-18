package com.shaadi.shaadi_assignment.model

import io.realm.RealmObject

open class PictureModel(var large: String="", var medium: String="", var thumbnail: String="") : RealmObject()