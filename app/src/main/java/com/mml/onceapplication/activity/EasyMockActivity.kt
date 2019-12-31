package com.mml.onceapplication.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.github.kittinunf.fuel.coroutines.awaitObjectResult
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mml.onceapplication.R
import com.mml.onceapplication.api.APIList
import com.mml.onceapplication.api.ApiCreate
import com.mml.onceapplication.api.IconItem
import kotlinx.android.synthetic.main.activity_easy_mock.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

object IpDeserializer : ResponseDeserializable<List<IconItem>> {
    override fun deserialize(content: String):List<IconItem> = Gson().fromJson(content,object :
        TypeToken<List<IconItem>>() {}.type)
}
class EasyMockActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_easy_mock)


        runBlocking {
        Fuel.get("https://www.easy-mock.com/mock/5dff194056438735d227bbe3/tran/img/list/")
            .awaitObjectResult(IpDeserializer)
            .fold(
                { data -> println(data) /* 127.0.0.1 */ },
                { error -> println("An error of type ${error.exception} happened: ${error.message}") }
            )
    }

       /* GlobalScope.launch(Dispatchers.IO) {
            val list =ApiCreate.create<APIList>().getIconList()
            launch {
                tv_test.text=list.toString()
            }

        }*/
    }
}
