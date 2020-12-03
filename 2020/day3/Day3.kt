package day3

import Solution

fun main(args : Array<String>) {
    val day3 = Day3()
    day3.part1()
    println("Part1 done")
    day3.part2()
    println("Part2 done")
}

class Day3: Solution("day3/") {

    private fun slopeTraverse(delta_x:Int, delta_y:Int):Int {
        var x = 0
        var y = 0
        var trees = 0

        while(true) {
            x += delta_x
            y += delta_y
            val char = input[y][x % input[y].length]
            if (char == '#') trees++;
            if (y == input.size - 1) break;
        }

        return trees
    }

    override fun part1() {
        println("Tree amount:" + slopeTraverse(3, 1))
    }

    override fun part2() {
        println("All trees: " + arrayOf(arrayOf(1,1), arrayOf(3,1), arrayOf(5,1), arrayOf(7,1), arrayOf(1,2)).fold(1) { acc, e ->
            acc * slopeTraverse(e[0], e[1])
        })
    }

}
