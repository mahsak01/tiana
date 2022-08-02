package com.example.tianaserver.common

import java.io.File

fun readFile(src:String) {
    val file = File(src)
    val bufferedReader = file.bufferedReader()
    var serverAddress: String? = bufferedReader.readLine()
    var reportingServer: String? = bufferedReader.readLine()

}