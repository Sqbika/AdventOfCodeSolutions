import java.io.File
import java.time.Instant
import java.util.*
import kotlin.concurrent.fixedRateTimer

abstract class Solution {

    val classPath: String by lazy {
        this.javaClass.`package`.toString()
    }

    val path : String by lazy {
        this.javaClass.getResource(".")?.path ?: ""
    }

    val input:List<String> by lazy {File(path+"input.txt").readLines()}

    open val tests = listOf<Pair<String, String>>()

    abstract fun part1(input: List<String>): String

    abstract fun part2(input: List<String>): String

    fun getTest(idx :Int): List<String> = File(path+"test$idx.txt").readLines()

    fun doTests() {
        tests.forEachIndexed { idx, test ->
            println("=".repeat(25))
            println("Running test $idx:")
            try {
                val part1 = part1(getTest(idx))
                if (test.first.isNotBlank()) {
                    if (test.first == part1) {
                        println("Part 1 PASS: $part1")
                    } else {
                        println("Error: Part 1 FAIL: Expected: \"${test.first}\", Actual: \"${part1}\"")
                    }
                } else {
                    println("Part 1: $part1")
                }

                val part2 = part2(getTest(idx))
                if (test.second.isNotBlank()) {
                    if (test.second == part2) {
                        println("Part 2 PASS: $part2")
                    } else {
                        println("Error: Part 2 FAIL: Expected: \"${test.second}\", Actual: \"${part2}\"")
                    }
                } else {
                    println("Part 2: $part2")
                }
            } catch (e: Exception) {
                System.err.println("Failed to run test${idx}.")
                e.printStackTrace()
            }
            println("=".repeat(25))
        }
    }
}


fun main(args : Array<String>) {
    if (args.isEmpty() || args.contains("today")) {
        val today = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        runDay("y${Calendar.getInstance().get(Calendar.YEAR)}.kt.day$today.Day$today", args.contains("test"))
    } else {
        runDay(args[0], args.contains("test"))
    }
}

fun runDay(clazz: String, doTest:Boolean) {
    val day = Class.forName(clazz)
    val solution:Solution = day.getDeclaredConstructor().newInstance() as Solution

    if (doTest) {
        solution.doTests()
    } else {
        println("=".repeat(25))

        val part1 = solution.part1(solution.input)

        println("\nPart 1: $part1")


        println("\n" + "=".repeat(25))

        println("Part 2: \"${solution.part2(solution.input)}\"")

        println("=".repeat(25))
    }
}
