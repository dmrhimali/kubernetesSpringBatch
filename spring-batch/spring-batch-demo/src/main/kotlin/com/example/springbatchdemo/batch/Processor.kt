package com.example.springbatchdemo.batch

import com.example.springbatchdemo.model.User
import org.springframework.batch.item.ItemProcessor
import org.springframework.stereotype.Component
import java.lang.Exception
import java.util.*
import kotlin.collections.HashMap


class Processor : ItemProcessor<User, User> { //input user, output:user
    @Throws(Exception::class)
    override fun process(user: User): User {
        println("Member Id = $user")
        //val user = User(memberId, listOf<String>("Tom", "Bob", "John", "Scott").random(), listOf<String>("001", "002", "003", "004").random() )
        val deptCode: String? = user.dept
        val dept = DEPT_NAMES[deptCode]
        user.dept = dept

        println(String.format("Converted from [%s] to [%s]", deptCode, dept))
        return user
    }

    companion object {
        private val DEPT_NAMES: MutableMap<String, String> = HashMap()
    }

    init {
        DEPT_NAMES["001"] = "Technology"
        DEPT_NAMES["002"] = "Operations"
        DEPT_NAMES["003"] = "Accounts"
        DEPT_NAMES["004"] = "HR"
    }
}