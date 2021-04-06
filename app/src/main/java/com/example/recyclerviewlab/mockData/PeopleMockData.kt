package com.example.recyclerviewlab.mockData

import android.content.Context
import com.example.recyclerviewlab.R
import java.io.FileNotFoundException
import java.lang.Exception

object PeopleMockData {

    fun peopleMockResponse(context: Context): String =
        try {
            context.resources.openRawResource(R.raw.faker_api_female_2021).reader().readText()
        } catch (e: Exception) {
            throw FileNotFoundException("fakerApi not found")
        }

    fun peopleMockResponseSmall(context: Context): String =
        try {
            context.resources.openRawResource(R.raw.faker_api_female_small_2021).reader().readText()
        } catch (e: Exception) {
            throw FileNotFoundException("fakerApi not found")
        }
}
