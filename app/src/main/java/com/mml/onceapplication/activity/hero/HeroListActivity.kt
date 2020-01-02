package com.mml.onceapplication.activity.hero

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.github.kittinunf.fuel.coroutines.awaitObjectResponseResult
import com.github.kittinunf.fuel.httpGet
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mml.onceapplication.R
import com.mml.onceapplication.log
import kotlinx.android.synthetic.main.activity_hero_list.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HeroListActivity : AppCompatActivity() {
     val Tag="HeroListActivity"
    private val baseSkinUrl="http://game.gtimg.cn/images/yxzj/img201606/skin/hero-info/%d/%d-bigskin-%d.jpg"
    val url = "http://pvp.qq.com/web201605/js/herolist.json"
    private val obj=object :ResponseDeserializable<List<HeroItem>>{
        override fun deserialize(content: String): List<HeroItem>? {
            return Gson().fromJson<List<HeroItem>>(content,object :TypeToken<List<HeroItem>>(){}.type)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hero_list)
        btn.setOnClickListener {
            GlobalScope.launch {
              val result=  url.httpGet().awaitObjectResponseResult(obj)
                result.third.fold(
                    success = { it ->
                        log(it.toString(),Tag)
                        GlobalScope.launch(Dispatchers.Main) {
                            tv_old.text=Gson().toJson(it)
                        }
                        it.forEach{heroItem ->
                            heroItem.skin_name.split("|").forEachIndexed { index,name->
                                val u=String.format(baseSkinUrl,heroItem.ename,heroItem.ename,index+1)
                              heroItem.skin_info.add(SkinInfo(u,name))
                            }
                        }

                        GlobalScope.launch(Dispatchers.Main) {
                            tv_new.text=Gson().toJson(it)
                        }

                    },failure = {
                        GlobalScope.launch(Dispatchers.Main) {
                            tv_old.text=it.message
                        }
                    }
                )
        }

        }
    }
}
