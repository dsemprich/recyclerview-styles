package com.example.recyclerviewlab.mockData

import android.content.Context
import android.content.res.Resources
import android.content.res.loader.ResourcesLoader
import android.content.res.loader.ResourcesProvider
import android.util.Log
import androidx.core.content.res.ResourcesCompat
import com.example.recyclerviewlab.BaseApplication
import com.example.recyclerviewlab.R
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.FileNotFoundException
import java.lang.Exception
import java.nio.file.Files

object PeopleMockData {

    fun peopleMockResponse(context: Context): String =
        try {
            context.resources.openRawResource(R.raw.faker_api_female_2021).reader().readText()
        } catch (e: Exception) {
            throw FileNotFoundException("fakerApi not found")
        }
}
