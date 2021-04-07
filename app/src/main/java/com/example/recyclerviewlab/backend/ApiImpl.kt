package com.example.recyclerviewlab.backend

import com.example.recyclerviewlab.mockData.PeopleMockData
import com.example.recyclerviewlab.sectionList.PeopleResult
import com.google.gson.Gson
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.callbackFlow
import java.lang.Exception
import java.lang.RuntimeException
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@OptIn(ExperimentalCoroutinesApi::class)
suspend fun Backend.sendAwait() = suspendCoroutine<PeopleResult>{  cont ->

    registerCallBack(object : BackendCallback {
        override fun onReceive(json: String) {
            try {
                val people = Gson().fromJson(json, PeopleResult::class.java)
                cont.resume(people)
            } catch (e: Exception) {
                throw RuntimeException("something went wrong")
            } finally {
                unRegisterCallBack(this)
            }
        }
    })
    send("")
}