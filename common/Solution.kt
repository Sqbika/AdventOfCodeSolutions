import java.io.File
import java.util.*

abstract class Solution(path:String) {

    val input:List<String> by lazy {File(path+"input.txt").readLines()}
    val test:List<String> by lazy {File(path+"test.txt").readLines()}

    abstract fun part1(input: List<String>)

    abstract fun part2(input: List<String>)
}


fun main(args : Array<String>) {
    if (args.contains("today")) {
        val today = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        runDay(today, args.contains("test"))
    } else {
        runDay(Integer.parseInt(args[0].replace("day", "")), args.contains("test"))
    }
}

fun runDay(today:Int, doTest:Boolean) {
    val day = Class.forName("y${Calendar.getInstance().get(Calendar.YEAR)}.kt.day$today.Day$today")
    val solution:Solution = day.getDeclaredConstructor(String::class.java).newInstance("years/y${Calendar.getInstance().get(Calendar.YEAR)}/kt/day$today/") as Solution
    if (doTest) {
        solution.part1(solution.test)
        println("Part1 test")
        solution.part2(solution.test)
        println("Part2 test")
        return
    }
    solution.part1(solution.input)
    println("Part1 done")
    solution.part2(solution.input)
    println("Part2 done")
}
