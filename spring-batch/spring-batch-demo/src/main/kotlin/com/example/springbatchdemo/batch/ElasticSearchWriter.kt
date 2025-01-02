        package com.example.springbatchdemo.batch

        import com.example.springbatchdemo.model.User
        import org.elasticsearch.client.RestHighLevelClient
        import org.springframework.batch.item.ItemWriter
        import java.util.*
        import org.elasticsearch.action.index.IndexRequest
        import org.elasticsearch.client.RequestOptions


        //https://blog.mimacom.com/import-rdb-data-elasticsearch-spring-batch/
        class ElasticSearchWriter (
                private val esClient: RestHighLevelClient
        ) : ItemWriter<User> {


            @Throws(Exception::class)
            override fun write(users: List<User>) {
                //userRepository.saveAll(users)

                for(user in users) {
                    val jsonMap = mutableMapOf<String, Any?>()
                    jsonMap.put("id", user.id)
                    jsonMap.put("name", user.name)
                    jsonMap.put("dept", user.dept)

                    val indexRequest = IndexRequest("spring-batch-es-index")
                            .id(user.id.toString())
                            .type("memberDoc")
                            .source(jsonMap)

                    esClient.index(indexRequest, RequestOptions.DEFAULT)
                }
                println("Data Saved for Users $users")
            }
        }