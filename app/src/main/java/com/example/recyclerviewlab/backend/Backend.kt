package com.example.recyclerviewlab.backend

interface Backend {

    fun registerCallBack(callBack: BackendCallback)
    fun unRegisterCallBack(callBack: BackendCallback)
    fun send(call: String)
}


interface BackendCallback {
    fun onReceive(json: String)
}