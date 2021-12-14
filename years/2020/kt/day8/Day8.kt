package day8

import Solution

fun main(args : Array<String>) {
    val solution = kt.day8.Day8()
    solution.part1()
    println("Part1 done")
    solution.part2()
    println("Part2 done")
}

class Day8 : Solution("kt/day8/") {

    override fun part1() {
        var acc = 0; var ptr = 0
        val visited = mutableListOf<Int>()
        while (!visited.contains(ptr)) {
            visited.add(ptr)
            val op = input[ptr].split(' ')
            when (op[0]) {
                "acc" -> { acc += Integer.parseInt(op[1]); ptr++ }
                "jmp" -> ptr += Integer.parseInt(op[1])
                "nop" -> ptr++
            }
        }
        println("Part 1: Acc: $acc")
    }

    override fun part2() {
        var acc = 0; var ptr = 0
        val visited = mutableListOf<Int>()
        while (!visited.contains(ptr)) {
            if (ptr == input.size) {
                //Last OP, we return
                break;
            }
            visited.add(ptr)
            val op = input[ptr].split(' ')
            when (op[0]) {
                "acc" -> { acc += Integer.parseInt(op[1]); ptr++ }
                "jmp" -> {
                    val newPtr = ptr + Integer.parseInt(op[1])
                    if (visited.contains(newPtr)) {
                        println("NewPTR Assigned. Replaced ACC to NOP: $ptr")
                        ptr++
                    } else {
                        ptr = newPtr
                    }
                }
                "nop" -> ptr++
            }
        }
        println("Part 2: Acc: $acc")
    }

}
