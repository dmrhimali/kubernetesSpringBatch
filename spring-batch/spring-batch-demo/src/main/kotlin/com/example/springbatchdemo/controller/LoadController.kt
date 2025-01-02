package com.example.springbatchdemo.controller

import org.springframework.batch.core.*
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException
import org.springframework.batch.core.repository.JobRestartException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class LoadController {
    @Autowired
    var jobLauncher: JobLauncher? = null

    @Autowired
    @Qualifier("single-proess-multi-thread")
    var singleProcessMultiThreadJob: Job? = null

    @GetMapping
    @RequestMapping("/load")
    @Throws(JobParametersInvalidException::class, JobExecutionAlreadyRunningException::class, JobRestartException::class, JobInstanceAlreadyCompleteException::class)
    fun load(): BatchStatus {
        val maps: MutableMap<String, JobParameter> = HashMap()
        maps["time"] = JobParameter(System.currentTimeMillis())
        val parameters = JobParameters(maps)
        val jobExecution = jobLauncher!!.run(singleProcessMultiThreadJob!!, parameters)

        println("JobExecution: " + jobExecution.status)
        println("Batch is Running...")

        while (jobExecution.isRunning) {
            println("...")
        }
        return jobExecution.status
    }
}