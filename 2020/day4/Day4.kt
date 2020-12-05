package day4

import Solution

fun main(args : Array<String>) {
    val day4 = Day4()
    day4.part1()
    println("Part1 done")
    day4.part2()
    println("Part2 done")
}

class Day4 : Solution("day4/") {

    fun preParse(input:List<String>):List<String> =
        input.joinToString("\n").split("\n\n").map { x -> x.replace('\n', ' ') }


    private fun parsePort(input:String, validFields:List<String>):Boolean =
        validFields.all { field -> input.contains("$field:") }


    override fun part1() {
        var valid = 0
        val validFields = listOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")
        println("Valid passports: " + preParse(input).filter {
            line ->
            parsePort(line, validFields)
        }.size)
    }

    override fun part2() {
        TODO("Not yet implemented")
    }

}
