package com.example.springbatchdemo.batch

import com.example.springbatchdemo.model.User
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet
import java.sql.SQLException

class CustomUserMapper : RowMapper<User> {
    @Throws(SQLException::class)
    override fun mapRow(rs: ResultSet, rowNum: Int): User? {
        return User(rs.getInt(ID_COLUMN).toLong(), rs.getString(NAME_COLUMN), rs.getString(DEPT_COLUMN))
    }

    companion object {
        const val ID_COLUMN = "id"
        const val NAME_COLUMN = "name"
        const val DEPT_COLUMN = "dept"
    }
}