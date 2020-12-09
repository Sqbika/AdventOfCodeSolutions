import day7.Day7
import day8.Day8
import java.io.File
import java.io.FileReader
import java.util.*
import kotlin.reflect.jvm.jvmName

abstract class Solution(path:String) {

    val input:List<String> by lazy {File(path+"input.txt").readLines()}
    val test:List<String> by lazy {File(path+"test.txt").readLines()}

    abstract fun part1()

    abstract fun part2()

    open fun test() {}
}


fun main(args : Array<String>) {
    if (args.contains("today")) {
        val today = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        runDay(today, args.contains("test"))
    }
}

fun runDay(today:Int, doTest:Boolean) {
    val day = Class.forName("day$today.Day$today")
    val solution:Solution = day.getDeclaredConstructor(String::class.java).newInstance("day$today/") as Solution
    if (doTest) {
        solution.test()
        println("Test done")
        return
    }
    solution.part1()
    println("Part1 done")
    solution.part2()
    println("Part2 done")
}
