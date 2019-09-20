package com.mml.updatelibrary.ui

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.gson.responseObject

import com.github.kittinunf.fuel.httpGet
import com.mml.updatelibrary.data.UpdateInfo
import com.mml.updatelibrary.data.UpdateUrl
import com.mml.updatelibrary.log
import java.io.File
import java.nio.charset.Charset

/**
 * Author: Menglong Ma
 * Email: mml2015@126.com
 * Date: 19-9-17 下午3:32
 * Description: This is UpdateDialog
 * Package: com.mml.updatelibrary.data
 * Project: OnceApplication
 */
class UpdateDialog {

    fun checkUpdate() {
        val httpAsync = UpdateUrl().url.httpGet()

            /*  .responseString { request, response, result ->
                  val input = response.body().toStream()
                  val string = input.bufferedReader().readLines().toString()
                  println(string)
                  log(msg = "content:${string}", tag = "UpdateDialog")
                  log(msg = "content:${result.component1()}", tag = "UpdateDialog")
              }*/
            .responseObject<UpdateInfo> { response, _, result ->
                log(msg = "content:${response.body}", tag = "UpdateDialog")
                result.fold(success = { updateInfo ->
                    log(msg = "content:$updateInfo", tag = "UpdateDialog")

                }, failure = { fuelError ->
                    log(msg = "content:$fuelError", tag = "UpdateDialog")
                })
            }
        httpAsync.join()
    }

    fun sss() {
        val http = Fuel.download("http://elf.static.maibaapp.com/butterfly/pure/vc_examine.json")
            /*   .streamDestination { response, request ->
                   val file=File("config.json")
                  Pair(FileOutputStream(file),{FileInputStream(file)})

               }*/
            .fileDestination { response, url ->
                File.createTempFile("temp", ".json")
            }
            .response { result ->
                val aa = result.component1()!!.toString(Charset.defaultCharset())
                log(msg = "content:$aa", tag = "UpdateDialog")
            }
    }
}