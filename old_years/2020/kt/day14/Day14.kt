package day14

import common.Solution
import java.lang.Math.pow
import kotlin.math.pow

class Day14(path:String) : Solution(path) {

    override fun part1() {
        println("Part1: ${solveDay1(input).toBigDecimal().toPlainString()}")
    }

    override fun part2() {
        println("Part2: ${solveDay2(input).toBigDecimal().toPlainString()}")
    }

    override fun test() {
        println("Test1: ${solveDay1(test).toBigDecimal().toPlainString()}")
        println("Test2: ${solveDay2(test).toBigDecimal().toPlainString()}")
    }

    private fun solveDay1(input:List<String>):Double {
        var mask = input[0].split(" = ")[1].mapIndexed { index, c -> Pair(index, c) }.filter {it.second != 'X'}
        val resultMap: MutableMap<Int, String> = mutableMapOf()
        input.subList(1, input.size).forEach { line ->
            if (line.startsWith("mask")) {
                mask = line.split(" = ")[1].mapIndexed { index, c -> Pair(index, c) }.filter {it.second != 'X'}
            } else {
                val parts = line.split(" = ")
                val idx = parts[0].filter { it.isDigit() }.toInt()
                val thirtySixBinary = parts[1].toInt().toString(2).padStart(36, '0').toCharArray().toMutableList()
                mask.forEach {
                    thirtySixBinary[it.first] = it.second
                }
                resultMap[idx] = thirtySixBinary.joinToString("")
            }
        }
        return resultMap.values.map {
            it.reversed().toDoubleFromBin()
        }.sum()
    }

    private fun solveDay2(input: List<String>) : Double {
        var mask = input[0].split(" = ")[1].mapIndexed { index, c -> Pair(index, c) }
        val resultMap: MutableMap<Double, Double> = mutableMapOf()
        input.subList(1, input.size).forEach { line ->
            if (line.startsWith("mask")) {
                mask = line.split(" = ")[1].mapIndexed { index, c -> Pair(index, c) }
            } else {
                val parts = line.split(" = ")
                val idx = parts[0].filter { it.isDigit() }.toInt().toString(2).padStart(36, '0').toCharArray().toMutableList()
                mask.forEach {
                    if (it.second == '1' || it.second == 'X')
                        idx[it.first] = it.second
                }
                var addrs:List<MutableList<Char>> = listOf(idx)
                do {
                    addrs = addrs.map { charList ->
                        val charIndex = charList.indexOf('X')
                        if (charIndex != -1)
                            listOf(charList.toMutableList().apply { this[charIndex] = '1' }, charList.toMutableList().apply {this[charIndex] = '0'})
                        else
                            listOf(charList)
                    }.flatten()
                } while(addrs.any {it.contains('X')})
                addrs.forEach { addr ->
                    resultMap[addr.joinToString("").toDoubleFromBin()] = parts[1].toDouble()
                }
            }
        }
        return resultMap.values.sum()
    }
}

fun String.toDoubleFromBin(): Double = this.reversed().mapIndexed {idx, char ->
        if (char == '1')
            2.0.pow(idx.toDouble())
        else 0.0
    }.sum()
