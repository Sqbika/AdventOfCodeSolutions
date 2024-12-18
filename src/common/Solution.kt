package common

import java.io.File
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.nio.file.Files
import java.nio.file.Path
import java.util.*
import kotlin.io.path.readText
import kotlin.io.path.writeText
import kotlin.system.measureTimeMillis

abstract class Solution {

    var isTest = false;

    val classPath: String by lazy {
        this.javaClass.`package`.toString()
    }

    val path: String by lazy {
        this.javaClass.getResource(".")?.path ?: ""
    }

    val input: List<String> by lazy { File(path + "input.txt").readLines() }

    open val tests = listOf<Pair<String, String>>()

    abstract fun part1(input: List<String>): String

    abstract fun part2(input: List<String>): String

    fun getTest(idx: Int): List<String> = File(path + "test$idx.txt").readLines()

    fun doTests() {
        this.isTest = true
        if (tests.size == 0) {
            throw Error("No tests defined.")
        }

        tests.forEachIndexed { idx, test ->
            println("=".repeat(25))
            println("Running test $idx:")
            try {
                if (test.first.isNotBlank()) {
                    val part1 = part1(getTest(idx))
                    if (test.first == part1) {
                        println("Part 1 PASS: $part1")
                    } else {
                        println("Error: Part 1 FAIL: Expected: \"${test.first}\", Actual: \"${part1}\"")
                    }
                } else {
                    println("Part 1: SKIP")
                }

                if (test.second.isNotBlank()) {
                    val part2 = part2(getTest(idx))
                    if (test.second == part2) {
                        println("Part 2 PASS: $part2")
                    } else {
                        println("Error: Part 2 FAIL: Expected: \"${test.second}\", Actual: \"${part2}\"")
                    }
                } else {
                    println("Part 2: SKIP")
                }
            } catch (e: Exception) {
                System.err.println("Failed to run test${idx}.")
                e.printStackTrace()
            } catch (e: NotImplementedError) {
                //skip
            }
            println("=".repeat(25))
        }
    }
}

fun main(args: Array<String>) {
    val day = when {
        args.isEmpty() || args.contains("today") -> Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        args.any{it.startsWith("day")} -> args.find{it.startsWith("day")}!!.replace("day", "").toInt()
        args.contains("ask") -> readln().toInt()
        else -> -1
    }

    var test = when {
        args.contains("test") -> true
        args.contains("ask") -> {
            print("Test? (y)")
            readln() == "y"
        }
        else -> false
    }

    val path = Path.of("src/years/y${Calendar.getInstance().get(Calendar.YEAR)}/kt/day$day")

    if (!Files.exists(path)) {
        Files.createDirectories(path)
    }

    if (!Files.exists(path.resolve("input.txt"))) {
        fetchInput(day, path)
    }

    if (!Files.exists(path.resolve("Day$day.kt"))) {
        createDay(day, path)
        println("Created Day $day. Skipping running.")
        return
    }

    val clazzString = "years.y${Calendar.getInstance().get(Calendar.YEAR)}.kt.day$day.Day$day"

    runDay(clazzString, test)
}

fun createDay(day: Int, path: Path) {
    val template = Path.of("src/common/template.txt").readText()
        .replace("DAY_TEMPLATE", day.toString())
        .replace("PACKAGE_TEMPLATE", "years.y${Calendar.getInstance().get(Calendar.YEAR)}.kt.day$day")

    path.resolve("Day$day.kt").writeText(template)
}

fun fetchInput(day: Int, path: Path) {
    val token = Path.of(".token").readText().trim()

    val httpClient = HttpClient.newBuilder().build()
    val request = HttpRequest.newBuilder()
        .GET()
        .uri(URI("https://adventofcode.com/${Calendar.getInstance().get(Calendar.YEAR)}/day/$day/input"))
        .header("Cookie", "session=$token")
        .build()

    val input = httpClient.send(request, HttpResponse.BodyHandlers.ofString()).body()
    if (input.contains("Puzzle inputs differ by user.  Please log in to get your puzzle input.")) {
        throw Error("AOC Cookie has expired. Please update token")
    }
    path.resolve("input.txt").writeText(input)
}

fun runDay(clazz: String, doTest: Boolean) {
    val day = Class.forName(clazz)
    val solution: Solution = day.getDeclaredConstructor().newInstance() as Solution

    if (doTest) {
        solution.doTests()
    } else {
        println("=".repeat(25))
        println(solution.path)

        println("=".repeat(25))

        val p1Time = measureTimeMillis {
            print("Part 1: ||${solution.part1(solution.input)}||")
        }
        println(" | $p1Time ms")

        val p2Time = measureTimeMillis {
            print("Part 2: ||${solution.part2(solution.input)}||")
        }
        println(" | $p2Time ms")

        println("=".repeat(25))
    }
}
