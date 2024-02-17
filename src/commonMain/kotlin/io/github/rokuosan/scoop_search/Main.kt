package io.github.rokuosan.scoop_search

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.int
import com.github.ajalt.mordant.animation.animation
import com.github.ajalt.mordant.rendering.AnsiLevel
import com.github.ajalt.mordant.table.RowHolderBuilder
import com.github.ajalt.mordant.table.grid
import com.github.ajalt.mordant.table.horizontalLayout
import com.github.ajalt.mordant.table.table
import com.github.ajalt.mordant.terminal.Terminal
import com.github.ajalt.mordant.widgets.Spinner
import io.github.rokuosan.scoop_search.entity.Search
import io.github.rokuosan.scoop_search.entity.SearchResult
import io.ktor.client.*
import io.ktor.client.engine.winhttp.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlin.math.abs

const val API_URL = "https://scoopsearch.search.windows.net/indexes/apps/docs/search?api-version=2020-06-30"

fun main(args: Array<String>) = ScoopSearchCommand().main(args)

class ScoopSearchCommand : CliktCommand() {
    private val text: String by argument()
    private val isTable: Boolean by option("-t", "--table").flag()
    private val count: Int by option("-n").int().default(10)

    private val json = Json {
        ignoreUnknownKeys = true
        prettyPrint = true
        encodeDefaults = true
    }
    private val client = HttpClient(WinHttp) {
        install(ContentNegotiation) {
            json(json = json)
        }
    }

    private val terminal = Terminal(AnsiLevel.TRUECOLOR)

    override fun run() = runBlocking {
        val spin = Spinner.Dots()
        val ani = terminal.animation<Int> {
            horizontalLayout {
                cell("Searching ")
                cell(spin)
            }
        }
        val upd = launch {
            while(true) {
                ani.update(spin.advanceTick())
                delay(125)
            }
        }

        val ch = Channel<HttpResponse>()

        val search = Search(
            search = text,
            top = abs(count)
        )
        launch {
            val resp = client.post(API_URL) {
                headers {
                    append("Api-Key", "DC6D2BBE65FC7313F2C52BBD2B0286ED")
                    append("Content-Type", ContentType.Application.Json)
                }
                setBody(search)
            }
            ch.send(resp)
        }

        val resp = ch.receive()
        ch.close()
        upd.cancelAndJoin()
        ani.clear()

        val rawResponse = resp.bodyAsText()
        val data: SearchResult = json.decodeFromString(rawResponse)

        val filtered = data.value
            .filter { it.name != null }

        val printList = fun RowHolderBuilder.(){
            for (item in filtered) {
                row {
                    cell(item.name ?: "")
                    cell(item.version ?: "")
                    cell(item.homepage ?: "")
                }
            }
        }

        terminal.println(
            when(isTable) {
                false -> {
                    grid {
                        row("Name", "Version", "Homepage")
                        printList()
                    }
                }
                true -> {
                    table {
                        header{ row("Name", "Version", "Homepage") }
                        body {
                            printList()
                        }
                    }
                }
            }
        )
    }
}