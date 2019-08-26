package com.mml.onceapplication.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Author: Menglong Ma
 * Email: mml2015@126.com
 * Date: 19-8-23 上午11:13
 * Description: This is User
 * Package: com.example.test.db
 * Project: Test
 */
@Entity(tableName = "User")
data class User(

    @ColumnInfo(name = "name")//定义数据表中的字段名
    var name: String,
    var sex: String,
    var age: Int ,
    @PrimaryKey(autoGenerate = true) //定义主键
    var id: Long
)
@Entity(tableName = "WorkInfo")
data class WorkInfo(
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    var where: String,
    var status: String
)

