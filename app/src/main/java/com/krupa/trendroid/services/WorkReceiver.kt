package com.krupa.trendroid.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

class WorkReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val myWorkBuilder =
            PeriodicWorkRequest.Builder(RepoWorker::class.java, 15, TimeUnit.MINUTES)

        val myWork = myWorkBuilder.build()
        val mWorkManager = WorkManager.getInstance(context!!)
        mWorkManager.enqueueUniquePeriodicWork(
            "fetch_repo",
            ExistingPeriodicWorkPolicy.KEEP, myWork
        )
    }
}