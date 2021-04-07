package com.example.recyclerviewlab.backend

import android.app.Application
import android.content.Context
import com.example.recyclerviewlab.R
import com.example.recyclerviewlab.mockData.PeopleMockData
import java.util.*
import javax.security.auth.callback.Callback

class BackendImpl(
    context: Context,
    private val json: String = context.resources.openRawResource(R.raw.faker_api_female_small_2021).reader().readText()
) : Backend {

    init {
        backend = this
    }

    private val receivers = HashMap<BackendCallback, Boolean>()

    override fun registerCallBack(callBack: BackendCallback) {
        receivers[callBack] = true
    }

    override fun unRegisterCallBack(callBack: BackendCallback) {
        receivers.remove(callBack)
    }

    override fun send(call: String) {
        triggerMessage(json)
    }

    companion object {

        lateinit var backend: BackendImpl

        fun triggerMessage(json: String) {
            backend.receivers.keys.forEach { key ->
                key.onReceive(json)
            }
        }
    }

}