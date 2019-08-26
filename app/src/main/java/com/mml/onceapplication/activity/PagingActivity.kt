package com.mml.onceapplication.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mml.onceapplication.R
import com.mml.onceapplication.adapter.PageViewModel
import com.mml.onceapplication.adapter.UserAdapter
import com.mml.onceapplication.db.TestDatabase.Companion.CHEESE_DATA
import com.mml.onceapplication.db.User
import kotlinx.android.synthetic.main.activity_paging.*

class PagingActivity : AppCompatActivity() {
    private val viewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T = PageViewModel(application) as T
        }).get(PageViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paging)
        val adapter=UserAdapter()
        recyclerView.adapter=adapter
        val linear=LinearLayoutManager(this)
        linear.orientation=LinearLayoutManager.VERTICAL
        recyclerView.layoutManager=linear
       
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        viewModel.allUsers.observe(this, Observer { adapter.submitList(it) })
        initData()
    }

    private fun initData(){
       val data= CHEESE_DATA.map { User(id=0,name = it,sex = "boy",age = 22) }
         viewModel.userDao.insert(*data.toTypedArray())
    }
}
