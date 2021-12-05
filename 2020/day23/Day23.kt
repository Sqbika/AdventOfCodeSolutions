package day23

import Solution
import kotlin.math.abs
import kotlin.reflect.cast
import kotlin.reflect.full.isSuperclassOf

class Day23(path: String) : Solution(path) {

    override fun part1() {

    }

    override fun part2() {

    }

    override fun test() {
        val start = System.currentTimeMillis()
        println("Test1: ${solvePart1(test, 10)}")
        println("Test1 took: ${System.currentTimeMillis() - start} ms")
        /*val start2 = System.currentTimeMillis()
        println("Test2: ${solvePart2(test)}")
        println("Test2 took: ${System.currentTimeMillis() - start2} ms")*/
    }

    private fun solvePart1(input:List<String>, moves: Int): String {
        /*var cups = input[0].toCharArray().map{it.toString().toInt()}.circleList()
        val max = cups.max
        val min = cups.min
        val size = cups.size
        var idx = 0
        for (move in 0..moves) {
            var destination = cups[idx]-1
            //Wrap around
            val selected = (cups).subList(idx+1, idx+4)
            cups.removeAll(selected)
            while (selected.contains(destination)) {
                destination--
                if (destination < min) destination = max
            }
            val cupSize = cups.size
            val tempList = (cups + cups + cups).toMutableList()
            tempList.addAll(cups.indexOf(destination)+1+cupSize, selected)
            cups = tempList.subList(cupSize, cupSize + size).toMutableList()
            idx = (idx+1) % size
        }
        return cups.subList(cups.indexOf(1)+1, size).joinToString("") + cups.subList(0, cups.indexOf(1)).joinToString("")*/
        return ""
    }

    private fun solvePart2(input:List<String>) {

    }
}

class CircleList(init: List<Int> = listOf()) {
    var internalList: MutableList<Int> = init.toMutableList()

    val size: Int get() = internalList.size

    val max: Int get() = internalList.maxOrNull()!!

    val min: Int get() = internalList.minOrNull()!!

    private fun safeIdx(idx:Int) : Int = (idx + abs(((idx / size) * size))) % size

    operator fun get(pos: Int) = internalList[safeIdx(pos)]

    fun add(pos: Int, it: Int) {
        internalList.add(safeIdx(pos), it)
    }

    fun addAll(pos: Int, them: List<Int>) {
        them.forEachIndexed { idx, it ->
            add(pos + idx, it)
        }
    }

    fun removeAll(them: List<Int>) = internalList.removeAll(them)

    fun indexOf(it: Int) = internalList.indexOf(it)

    fun subList(startIdx: Int, endIdx: Int) = internalList.subList(startIdx, endIdx)
}

fun List<Int>.circleList():CircleList = CircleList(this)

