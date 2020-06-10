package com.mml.onceapplication.adapter

import android.app.Application
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.AndroidViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.mml.onceapplication.R
import com.mml.onceapplication.db.TestDatabase
import com.mml.onceapplication.db.User
import kotlinx.android.synthetic.main.item_paging_recycle_view.view.*

/**
 * @projectName：OnceApplication
 * @author: Created by Menglong Ma
 * @email：mml2015@126.com
 * @date: 2019/8/25 13:24.
 * @description:
 */
   class UserHolder(parent:ViewGroup):RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(
    R.layout.item_paging_recycle_view,parent,false)){
    private var root:View=itemView.rootView
    private var name=root.userName
    private var sex=root.userSex
    private var age=root.userAge
    private var user: User?=null
    fun bindTo(user: User){
        this.user=user
        name.text=user.name
        sex.text=user.sex
        age.text= user.age.toString()
    }
}

class UserAdapter: PagedListAdapter<User, UserHolder>(diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {

              return UserHolder(parent )
    }

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        getItem(position)?.let { holder.bindTo(it) }
    }
      companion object{
          private val diffCallback by lazy {
              object : DiffUtil.ItemCallback<User>() {
                  override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                     return oldItem.id==newItem.id
                  }

                  override fun areContentsTheSame(oldItem: User, newItem: User): Boolean =
                        oldItem==newItem
              }
          }
      }
}

class PageViewModel(app:Application):AndroidViewModel(app){
    val userDao=TestDatabase.getInstance(app).userDao()

    val allUsers = LivePagedListBuilder(userDao.getAllUser(), PagedList.Config.Builder()
        .setPageSize(PAGE_SIZE)                         //配置分页加载的数量
        .setEnablePlaceholders(ENABLE_PLACEHOLDERS)     //配置是否启动PlaceHolders
        .setInitialLoadSizeHint(PAGE_SIZE)              //初始化加载的数量
        //.setPrefetchDistance(-1)                      //设置距离最后还有多少个item时，即寻呼库开始加载下一页的数据
        .build()
    )   
        .build()
    
    companion object {
        private const val PAGE_SIZE = 15

        private const val ENABLE_PLACEHOLDERS = false
    }
}

abstract class BaseRVAdapter<T,R:ViewBinding>(): RecyclerView.Adapter<BaseRVAdapter<T,R>.ViewHolder<R>>() {
    val dataList = mutableListOf<T>()
    abstract fun getViewHolder(parent: ViewGroup, viewType: Int):R
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRVAdapter<T,R>.ViewHolder<R> {
        return ViewHolder(getViewHolder(parent,viewType))
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: BaseRVAdapter<T,R>.ViewHolder<R>, position: Int) {

    }

    inner class ViewHolder<S:ViewBinding>(viewBinding: S):RecyclerView.ViewHolder(viewBinding.root)
}
