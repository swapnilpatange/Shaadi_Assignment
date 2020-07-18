package com.shaadi.shaadi_assignment.model

import io.realm.RealmObject

open class NameModel(var title: String="", var first: String="", var last: String="") : RealmObject()