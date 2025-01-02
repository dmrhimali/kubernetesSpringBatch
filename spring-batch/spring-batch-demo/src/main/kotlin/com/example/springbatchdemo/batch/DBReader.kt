package com.example.springbatchdemo.batch

import com.example.springbatchdemo.model.User
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean

import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder

import java.util.HashMap

import org.springframework.batch.item.database.JdbcPagingItemReader
import org.springframework.batch.item.database.Order
import org.springframework.batch.item.database.PagingQueryProvider
import org.springframework.batch.item.database.support.PostgresPagingQueryProvider
import javax.sql.DataSource

//https://docs.spring.io/spring-batch/docs/4.0.x/reference/html/readersAndWriters.html#pagingItemReaders
object DBReader {

    fun itemReader(dataSource: DataSource): JdbcPagingItemReader<User> {
        val parameterValues: MutableMap<String, Any> = HashMap()
        parameterValues["id"] = 500
        return JdbcPagingItemReaderBuilder<User>()
                .name("memberIdReader")
                .dataSource(dataSource)
                .queryProvider(queryProvider())
                .parameterValues(parameterValues)
                .rowMapper(CustomUserMapper())
                //.rowMapper { resultSet, _ -> resultSet.getLong(1) }
                .pageSize(100)
                .build()
    }

    fun queryProvider(): PostgresPagingQueryProvider {
        val provider = PostgresPagingQueryProvider()
        provider.setSelectClause("select id, name, dept")
        provider.setFromClause("from member")
        provider.setWhereClause("where id>:id")
        provider.sortKeys = mapOf("id" to Order.ASCENDING)
        return provider
    }
}