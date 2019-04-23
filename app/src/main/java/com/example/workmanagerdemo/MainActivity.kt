package com.example.workmanagerdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.work.*
import com.example.workmanagerdemo.utils.Constant
import com.example.workmanagerdemo.workers.GetNameWorker
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private lateinit var getNameWorkRequest: OneTimeWorkRequest
    private lateinit var constraints: Constraints
    private lateinit var inputData: Data

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initConstraints()

        initInputData()

        setUpWorkRequest()

        WorkManager.getInstance().enqueue(getNameWorkRequest)

        observerData()
    }

    private fun initConstraints() {
        constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .build()
    }

    private fun initInputData() {
        inputData =
            Data.Builder().putString(Constant.INPUT_NAME, "analanolog").putString(Constant.INPUT_EMAIL, "anabel@bela.com").build()
    }

    private fun setUpWorkRequest() {
        getNameWorkRequest = OneTimeWorkRequestBuilder<GetNameWorker>()
            .setConstraints(constraints)
            .setInputData(inputData)
            .addTag("getName")
            .setInitialDelay(10, TimeUnit.SECONDS)
            .build()
    }

    private fun observerData() {
        WorkManager.getInstance().getWorkInfoByIdLiveData(getNameWorkRequest.id).observe(this, Observer { workInfo ->
            if (workInfo != null && workInfo.state == WorkInfo.State.SUCCEEDED) {
                textViewName.text = workInfo.outputData.getString(Constant.OUTPUT_NAME)
                textViewEmail.text = workInfo.outputData.getString(Constant.OUTPUT_EMAIL)
            }
        })
    }
}
