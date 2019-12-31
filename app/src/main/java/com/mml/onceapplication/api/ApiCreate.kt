package com.mml.onceapplication.api

import com.mml.onceapplication.log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.KeyStore
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.*
import java.util.concurrent.TimeUnit
import javax.net.ssl.*


/**
 * Author: Menglong Ma
 * Email: mml2015@126.com
 * Date: 19-12-19 上午11:33
 * Description: This is ApiCreate
 * Package: com.m.l.tran.avatar.api
 * Project: TranAvatar
 */
fun apiCreate(block: ApiCreate.()->Unit): ApiCreate {
   return ApiCreate.apply(block)
}
object ApiCreate {
    var CONNECT_TIMEOUT: Long = 20L
    private val client= getUnsafeOkHttpClient()!!//OkHttpClient.Builder()
    val retrofitBuilder=  Retrofit.Builder().apply {
        client(client.build())
        addConverterFactory(GsonConverterFactory.create())
    }
    var interceptorLog:(msg:String)->Unit={
        log("ApiCreate","retrofitBack = $it")
    }
    private var interceptor: HttpLoggingInterceptor.Logger =object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                //打印retrofit日志
                interceptorLog.invoke(message)
            }
        }

    init {
        client.apply {
            addInterceptor(HttpLoggingInterceptor(interceptor))
            connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            /* sslSocketFactory(SSLSocketClient.getFactory(),SSLSocketClient.getTrustManager1())
             hostnameVerifier(SSLSocketClient.getHostnameVerifier())*/
        }
    }

    inline fun <reified T> create(): T = run {
     val baseUrl=when(T::class){
         APIList::class->{
             APIList.BASE_URL
         }
         else -> {
               ""
         }
     }
     return@run   retrofitBuilder
            .baseUrl(baseUrl)
            .build()
            .create(T::class.java)

    }
    private fun getUnsafeOkHttpClient(): OkHttpClient.Builder? {
        return try {

            val trustManagerFactory =
                TrustManagerFactory.getInstance(
                    TrustManagerFactory.getDefaultAlgorithm()
                )
            trustManagerFactory.init(null as KeyStore?)
            val trustManagers =
                trustManagerFactory.trustManagers
            check(!(trustManagers.size != 1 || trustManagers[0] !is X509TrustManager)) {
                ("Unexpected default trust managers:"
                        + Arrays.toString(trustManagers))
            }
            val trustManager =
                trustManagers[0] as X509TrustManager

            val sslContext = SSLContext.getInstance("TLS")
            sslContext.init(null, arrayOf<TrustManager>(trustManager), null)
            val sslSocketFactory = sslContext.socketFactory

            val builder = OkHttpClient.Builder()
            builder.sslSocketFactory(sslSocketFactory,trustManager)
            builder.hostnameVerifier(HostnameVerifier { hostname, session -> true })
            builder
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}