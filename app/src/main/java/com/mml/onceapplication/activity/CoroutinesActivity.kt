package com.mml.onceapplication.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import com.mml.onceapplication.R
import com.mml.onceapplication.http.API
import com.mml.onceapplication.http.adapter.CAdapter
import com.mml.onceapplication.http.data.Data
import com.mml.onceapplication.log
import kotlinx.android.synthetic.main.activity_coroutines.*
import kotlinx.android.synthetic.main.item_activity_coroutines.view.*
import kotlinx.coroutines.*

class CoroutinesActivity : AppCompatActivity() {
     val TAG="CoroutinesActivity"
    private val list = mutableListOf<Data>()
    private lateinit var adapter:CAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutines)
       // dos()
        initView()
    }

    private fun initData() {
        GlobalScope.launch {
           val result= API.reqApi.getDatas()
            list.clear()
            list.addAll(result.data)
            withContext(Dispatchers.Main){
                srl_root.finishRefresh()
                adapter.notifyDataSetChanged()
            }
        }
    }

    private fun initView() {
        adapter=CAdapter().apply {
            listData=list
            onConvert={view, pos ->
                view.tv.text=listData[pos].name
            }
        }
        recyclerView.adapter=adapter
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        srl_root.setOnRefreshListener {
            initData()
        }
    }

    fun dos(){
        val deferred = (1..1_000_000).map { n ->
            GlobalScope.async {
                n
            }
        }
        runBlocking {
            val sum = deferred.sumBy {
              //println("it: ${it.await()}")
                it.await()
            }
            println("Sum: $sum")
        }
    }
    
}
