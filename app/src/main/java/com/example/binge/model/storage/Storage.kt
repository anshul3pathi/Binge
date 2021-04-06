package com.example.binge.model.storage

interface Storage {
    fun setLong(value: Long)
    fun getLong(): Long

    fun setBoolean(value: Boolean)
    fun getBoolean(): Boolean
}