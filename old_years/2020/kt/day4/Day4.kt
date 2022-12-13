package day4

import common.Solution

fun main(args : Array<String>) {
    val day4 = kt.day4.Day4()
    day4.part1()
    println("Part1 done")
    day4.part2()
    println("Part2 done")
}

class Day4 : Solution("kt/day4/") {

    fun preParse(input:List<String>):List<String> =
        input.joinToString("\n").split("\n\n").map { x -> x.replace('\n', ' ') }


    private fun parsePort(input:String, validFields:List<String>):Boolean =
        validFields.all { field -> input.contains("$field:") }


    override fun part1() {
        val validFields = listOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")
        println("Valid passports: " + preParse(input).filter {
            line ->
            parsePort(line, validFields)
        }.size)
    }

    private fun validatePart(prefix:String, content:String):Boolean =
        when (prefix) {
            "byr" -> Integer.parseInt(content) in 1920..2002
            "iyr" -> Integer.parseInt(content) in 2010..2020
            "eyr" -> Integer.parseInt(content) in 2020..2030
            "hgt" ->  when {
                content.endsWith("cm") -> Integer.parseInt(content.replace("cm", "")) in 150..193
                content.endsWith("in") -> Integer.parseInt(content.replace("in", "")) in 59..76
                else -> false
            }
            "hcl" -> content.startsWith("#") && content.length == 7 && content.replace("#", "").all {
                it in '0'..'9' || it in 'a'..'f'
            }
            "ecl" -> "amb blu brn gry grn hzl oth".split(" ").contains(content)
            "pid" -> content.length == 9 && content.all { it in '0'..'9' }
            "cid" -> true
            else -> false
        }


    override fun part2() {
        val lines = preParse(input)

        println("Line count: ${lines.size}")
        println("Valid Passports: " + lines.filter {
            line ->
            val valid = line.split(" ").all {
                    val splitSegment = it.split(":")
                    val valid = validatePart(splitSegment[0], splitSegment[1])
                    if (false)
                        println("${splitSegment[0]} | Content: ${splitSegment[1]} | Valid: $valid")
                    valid
            } && listOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid").all {
                line.contains(it)
            }
            println("${if (valid) "  Valid" else "Invalid"} Line: '$line'")
            valid
        }.size)
    }

}
