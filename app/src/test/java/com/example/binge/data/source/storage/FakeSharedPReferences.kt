package com.example.binge.data.source.storage

import com.example.binge.model.storage.Storage

class FakeSharedPReferences : Storage {

    private var _bool = true

    private var _long = 0L

    override fun setLong(value: Long) {
        _long = value
    }

    override fun getLong() = _long

    override fun setBoolean(value: Boolean) {
        _bool = value
    }

    override fun getBoolean() = _bool

}