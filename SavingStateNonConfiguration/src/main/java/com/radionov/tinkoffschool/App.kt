package com.radionov.tinkoffschool

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.os.StrictMode
import android.util.Log

/**
 * @author Andrey Radionov
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder()
                .detectActivityLeaks()
                .penaltyDeath()
                .build())

        registerCallbacks()
    }

    private fun registerCallbacks() {
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityPaused(activity: Activity) {
                Log.d(activity.javaClass.simpleName, "onActivityPaused")
            }

            override fun onActivityResumed(activity: Activity) {
                Log.d(activity.javaClass.simpleName, "onActivityResumed")
            }

            override fun onActivityStarted(activity: Activity) {
                Log.d(activity.javaClass.simpleName, "onActivityStarted")
            }

            override fun onActivityDestroyed(activity: Activity) {
                Log.d(activity.javaClass.simpleName, "onActivityDestroyed")
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
                Log.d(activity.javaClass.simpleName, "onActivitySaveInstanceState")
            }

            override fun onActivityStopped(activity: Activity) {
                Log.d(activity.javaClass.simpleName, "onActivityStopped")
            }

            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                Log.d(activity.javaClass.simpleName, "onActivityCreated")
            }
        })
    }
}