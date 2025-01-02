package com.example.springbatchdemo.config

import com.example.springbatchdemo.batch.DBReader
import com.example.springbatchdemo.batch.ElasticSearchWriter
import com.example.springbatchdemo.batch.Processor
import com.example.springbatchdemo.model.User
import com.example.springbatchdemo.repository.UserRepository
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.SimpleDriverDataSource
import javax.sql.DataSource
import org.springframework.core.task.SimpleAsyncTaskExecutor

import org.springframework.core.task.TaskExecutor
import java.net.MalformedURLException

import org.springframework.batch.item.UnexpectedInputException
import org.springframework.beans.factory.annotation.Value


@Configuration
@EnableBatchProcessing
class SpringBatchConfig (
        private val jobBuilderFactory: JobBuilderFactory,
        private val stepBuilderFactory: StepBuilderFactory,
        private val userRepository: UserRepository,
        private val dataSource : DataSource,
        private val appConfig: ApplicationConfig
) : DefaultBatchConfigurer() {

    //Had to implement DefaultBatchConfigurer and override set DataSource to point to h2 because it look for
    // batch tables (sys created) in postgres runnig in docker.
    override fun setDataSource(dataSource: DataSource) {
        val inMemoryDataSource = SimpleDriverDataSource().apply {
            driver = org.h2.Driver()
            url = "jdbc:h2:file:~/test"
            username = "sa"
            password = ""
        }
        super.setDataSource(inMemoryDataSource)
    }


    @Bean
    fun taskExecutor(): TaskExecutor {
        return SimpleAsyncTaskExecutor("spring_batch")
    }

    @Bean
    @Qualifier("single-proess-multi-thread")
    fun getJob() : Job {
        val step: Step = stepBuilderFactory.get("ETL-file-load")
                .chunk<User, User>(100)
                .reader(itemReader())
                .processor(itemProcessor())
                .writer(itemWriter())
                .taskExecutor(taskExecutor())
                .throttleLimit(10)
                .build()



        return jobBuilderFactory.get("ETL-LOAD")
                .incrementer(RunIdIncrementer())
                .start(step)
                .build()
    }

    @Bean
    open fun itemReader() = DBReader.itemReader(dataSource)

    @Bean
    open fun itemProcessor() = Processor()

    @Bean
    open fun itemWriter() = ElasticSearchWriter(ElasticsearchConfiguration.client(appConfig.eshost))
}




//
//@Configuration
//@EnableBatchProcessing
//class SpringBatchConfig (
//        private val jobBuilderFactory: JobBuilderFactory,
//        private val stepBuilderFactory: StepBuilderFactory,
//        private val userRepository: UserRepository,
//        private val dataSource : DataSource
//) : DefaultBatchConfigurer() {
//
//    //Had to implement DefaultBatchConfigurer and override set DataSource to point to h2 because it look for
//    // batch tables (sys created) in postgres runnig in docker.
//    override fun setDataSource(dataSource: DataSource) {
//        val inMemoryDataSource = SimpleDriverDataSource().apply {
//            driver = org.h2.Driver()
//            url = "jdbc:h2:file:~/test"
//            username = "sa"
//            password = ""
//        }
//        super.setDataSource(inMemoryDataSource)
//    }
//
//
//    @Bean
//    fun taskExecutor(): TaskExecutor {
//        return SimpleAsyncTaskExecutor("spring_batch")
//    }
//
//    @Bean
//    @Qualifier("single-proess-multi-thread")
//    fun getJob() : Job {
//        val step: Step = stepBuilderFactory.get("ETL-file-load")
//                .chunk<User, User>(100)
//                .reader(itemReader())
//                .processor(itemProcessor())
//                .writer(itemWriter())
//                .taskExecutor(taskExecutor())
//                .throttleLimit(10)
//                .build()
//
//
//
//        return jobBuilderFactory.get("ETL-LOAD")
//                .incrementer(RunIdIncrementer())
//                .start(step)
//                .build()
//    }
//
//    @Bean
//    open fun itemReader() = DBReader.itemReader(dataSource)
//
//    @Bean
//    open fun itemProcessor() = Processor()
//
//    @Bean
//    open fun itemWriter() = ElasticSearchWriter(ElasticsearchConfiguration.client())
//}