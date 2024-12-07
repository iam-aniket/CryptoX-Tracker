package com.example.cryptoxtracker.model

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

object SerializationUtil {
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    fun <T> toJson(data: T, clazz: Class<T>): String {
        val adapter = moshi.adapter(clazz)
        return adapter.toJson(data)
    }

    fun <T> fromJson(json: String, clazz: Class<T>): T? {
        val adapter = moshi.adapter(clazz)
        return adapter.fromJson(json)
    }
}