package com.krupa.trendroid.ui.home

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.work.ListenableWorker
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.testing.TestListenableWorkerBuilder
import androidx.work.testing.WorkManagerTestInitHelper
import com.krupa.trendroid.services.RepoWorker
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class TrendingRepoActivityTest {
    private lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun testWork() {
        runBlocking {
            val worker = TestListenableWorkerBuilder<RepoWorker>(context).build()
            val result = worker.doWork()
            assertEquals(result, ListenableWorker.Result.success())
        }
    }

    @Test
    fun testPeriodicWork() {
        val request = PeriodicWorkRequestBuilder<RepoWorker>(15, TimeUnit.MINUTES)
            .build()

        val workManager = WorkManager.getInstance(context)
        val testDriver = WorkManagerTestInitHelper.getTestDriver(context)
        workManager.enqueue(request).result.get()
        testDriver?.setPeriodDelayMet(request.id)
        val workInfo = workManager.getWorkInfoById(request.id).get()

        assertEquals(workInfo.state, WorkInfo.State.ENQUEUED)
    }
}