package com.example.springbatchdemo.config

import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.client.RestClient
import org.apache.http.HttpHost
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

object ElasticsearchConfiguration {
    /**
     * Creates a Elasticsearch client from config
     *
     * @return Elasticsearch client
     */
    fun client(eshost: String): RestHighLevelClient {
        return RestHighLevelClient(
                RestClient.builder(HttpHost(eshost, 9200, "http")))
    }
}