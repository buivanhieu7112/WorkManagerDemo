package com.example.workmanagerdemo.workers

import android.content.Context
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.workmanagerdemo.utils.Constant

class GetNameWorker(appContext: Context, workerParams: WorkerParameters) : Worker(appContext, workerParams) {
    override fun doWork(): Result {
        val name = inputData.getString(Constant.INPUT_NAME)
        val email = inputData.getString(Constant.INPUT_EMAIL)

        val outputData = Data.Builder().putString(Constant.OUTPUT_NAME, name).putString(Constant.OUTPUT_EMAIL, email).build()

        return Result.success(outputData)
    }
}
