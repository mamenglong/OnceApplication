package com.mml.onceapplication.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.mml.onceapplication.R
import com.mml.onceapplication.db.TestDatabase
import com.mml.onceapplication.db.User
import com.mml.onceapplication.db.Wife
import com.mml.onceapplication.db.WorkInfo
import kotlinx.android.synthetic.main.activity_room_test.*
import kotlin.random.Random

class RoomTestActivity : AppCompatActivity() {
    lateinit var database: TestDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_test)
        database=TestDatabase.getInstance(this)

        database.userDao().insert(User("梦龙","男",23,id=0))

        initView()
    }

    private fun initView() {
        btn_delete.setOnClickListener {
            database.userDao().delete(User("梦龙","男",23,id = 0))
        }
        btn_insert.setOnClickListener {
            database.userDao().insert(
                *(TestDatabase.CHEESE_DATA.map { User(id = 0, name = it,sex = "男",age = 10)}.toTypedArray()
            ) )

            Log.i("RoomTestActivity","btn_insert.setOnClickListener")
            database.userDao().insert(User("梦龙","男", Random(System.currentTimeMillis()).nextInt(1000),id=0))
            database.workInfoDao().insert(WorkInfo(where = "ss"+Random(System.currentTimeMillis()).nextInt(),status = "44",id = 6))
            database.wifeDao().insert(Wife().setName("kjkj"))
            Log.i("RoomTestActivity","btn_insert.setOnClickListener     end")

        }
        database.userDao().getAll().observeForever {
            test.text=it.toList().toString()

        }
           database.wifeDao().getAll().observeForever {
               test1.text=it.toList().toString()
           }
    }
}

