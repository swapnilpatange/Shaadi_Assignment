package com.shaadi.shaadi_assignment.application

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class ShaadiApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        Realm.init(getApplicationContext())


        val config = RealmConfiguration.Builder()
            .deleteRealmIfMigrationNeeded()
            .build()

        Realm.setDefaultConfiguration(config)
    }
}