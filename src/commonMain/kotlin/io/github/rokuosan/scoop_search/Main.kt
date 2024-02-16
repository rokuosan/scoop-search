package io.github.rokuosan.scoop_search

import io.github.rokuosan.scoop_search.entity.Search
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.winhttp.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.runBlocking

const val API_URL = "https://scoopsearch.search.windows.net/indexes/apps/docs/search?api-version=2020-06-30"

fun main() = runBlocking {
    val client = HttpClient(WinHttp){
        install(ContentNegotiation){
            json()
        }
    }
    val resp = client.post(API_URL){
        headers {
            append("Api-Key", "DC6D2BBE65FC7313F2C52BBD2B0286ED")
            append("Content-Type", ContentType.Application.Json)
        }
        setBody(Search(search = "nodejs"))
    }

    println(resp.bodyAsText())
}
