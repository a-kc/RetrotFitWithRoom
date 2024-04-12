package com.example.retrofitwithroom.retorifit

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException


class HeaderInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val requestBuilder = original.newBuilder()
        return chain.proceed(requestBuilder.build())
    }
}
