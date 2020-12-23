package day20

import Solution
import day20.SIDES.*
import java.math.BigInteger
import kotlin.math.max
import kotlin.math.min
import kotlin.math.round
import kotlin.math.sqrt

class Day20(path:String) : Solution(path) {

    override fun part1() {
        val start = System.currentTimeMillis()
        println("Input1: ${solvePart1(input)}")
        println("Input1 took: ${System.currentTimeMillis() - start} ms")
    }

    override fun part2() {
        TODO("Not yet implemented")
    }

    override fun test() {
        /*val start = System.currentTimeMillis()
        println("Test1: ${solvePart1(test)}")
        println("Test1 took: ${System.currentTimeMillis() - start} ms")*/
        val start2 = System.currentTimeMillis()
        println("Test2: ${solvePart2(test)}")
        println("Test2 took: ${System.currentTimeMillis() - start2} ms")
    }

    private fun listOfMaps(input:List<String>) : List<Pair<Int, List<String>>> = input.joinToString("\n").split("\n\n").map {
            val lines = it.split("\n")
            val tile = Regex("Tile ([0-9]*):").find(lines[0])!!.groupValues[1].toInt()
            Pair(tile, lines.subList(1, lines.size))
        }

    private fun solvePart1(input: List<String>) : BigInteger {
        return getCompleteMap(input).solution()
    }

    private fun solvePart2(input: List<String>): BigInteger {
        val map = getCompleteMap(input)
        val sajt = map.stitch()

        return 0.toBigInteger()
    }

    private fun getCompleteMap(input:List<String>) : Grid {
        val maps = listOfMaps(input).map { mapPair ->
            listOf (mapPair.second, mapPair.second.rotate(),mapPair.second.rotate().rotate(),mapPair.second.rotate().rotate().rotate())
                    .map {
                        listOf(
                                FourSides(it, mapPair.first),
                                FourSides(it.flipVertical(), mapPair.first),
                                FourSides(it.flipHorizontal(), mapPair.first),
                                FourSides(it.flipVertical().flipHorizontal(), mapPair.first),
                        )
                    }.flatten()
        }.flatten()

        val mapCount = maps.size / (4*4)
        val mapSize = round(sqrt(mapCount.toDouble())).toInt()

        val sideCandidates = maps.map {
            Pair(it, it.possibleSides(maps) )
        }

        var resultMaps: List<Grid> = maps.map {
            val grid = Grid(mapSize)
            grid[0, 0] = it
            grid
        }

        for (x in 0 until mapSize) {
            for (y in 0 until mapSize) {
                if (x == 0 && y == 0) continue
                resultMaps = resultMaps.map { grid ->
                    when {
                        y == 0 -> {
                            val upper = grid[x-1, y]!!
                            maps.filter {upper.bottom == it.top && upper.id != it.id && !grid.contains(it.id)}.map {
                                grid.copyAndSet(x,y,it)
                            }
                        }
                        x == 0 -> {
                            val left = grid[x, y-1]!!
                            maps.filter {left.right == it.left && left.id != it.id  && !grid.contains(it.id)}.map {
                                grid.copyAndSet(x,y,it)
                            }
                        }
                        else -> {
                            maps.filter { map ->
                                grid.isValidForSpot(map, x, y)
                            }.map {
                                grid.copyAndSet(x,y,it)
                            }
                        }
                    }
                }.flatten()
            }
        }

        return resultMaps[0]
    }
}

class Grid(val size: Int) {

    private var grid:Array<Array<FourSides?>> = Array(size) {
        Array(size) {
            null
        }
    }

    override fun toString(): String = grid.joinToString("\n") { sajt ->
            sajt.map {
                it?.id ?: "----"
            }.joinToString(", ")
        }

    fun copy():Grid {
        val res = Grid(size)
        res.grid = grid.clone()
        return res
    }

    fun copyAndSet(x:Int,y:Int, fs:FourSides):Grid {
        val copy = copy()
        copy[x,y] = fs
        return copy
    }

    fun contains(id:Int):Boolean = grid.any{it.any { node -> node?.id == id}}

    operator fun get(x:Int,y:Int) = grid[x][y]

    operator fun set (x:Int, y:Int, fs: FourSides) {
        grid[x][y] = fs
    }

    fun solution() = this[0,0]!!.id.toBigInteger() *
            this[0,size-1]!!.id.toBigInteger() *
            this[size-1,size-1]!!.id.toBigInteger() *
            this[size-1,0]!!.id.toBigInteger()

    /*
    -1 0
    0 1
    1 0
    0 -1
     */

    fun getXYBounds(x:Int,y:Int): List<Pair<Int,Int>> = listOf(
                Pair(-1+x, 0+y),
                Pair(0+x, 1+y),
                Pair(1+x, 0+y),
                Pair(0+x, -1+y)
        ).filter {it.first in 0 until size && it.second in 0 until size}

    fun getBounds(x:Int,y:Int) = getXYBounds(x,y).mapNotNull {
        get(it.first,it.second)
    }

    fun getBoundsWithDir(x:Int,y:Int):List<Pair<SIDES, FourSides>> = getXYBounds(x,y).mapNotNull {
        val map = get(it.first,it.second)

        if (map != null)
            Pair(SIDES.getDir(x,y,it.first,it.second)!!, map)
        else
            null
    }

    fun isValidForSpot(target: FourSides, x:Int, y:Int):Boolean {
        if (contains(target.id)) return false

        return getBoundsWithDir(x,y).all {
            target.matchSide(it.first, it.second)
        }
    }

    fun stitch() = grid.toList().map { row ->
        row.toList().map { piece ->
            piece!!.input.subList(1, 10).map {
                it.substring(1, 10)
            }
        } //List<List<List<String>>>
    }.map { row ->
        val rowRes:MutableList<String> = mutableListOf()
        (0..8).forEach { y ->
            rowRes.add(row.joinToString("") { it[y] })
        }
        rowRes
    }.flatten()
}

class FourSides(val input: List<String>, val id: Int) {
    val top: Int = input.first().fromMapToInt()
    val left: Int = input.map { str -> str[0]}.joinToString("").fromMapToInt()
    val right: Int = input.map { str -> str.last()}.joinToString("").fromMapToInt()
    val bottom: Int = input.last().fromMapToInt()

    fun possibleSides(list: List<FourSides>): Map<SIDES, List<FourSides>> = listOf(TOP, BOTTOM, LEFT, RIGHT).map {side ->
            Pair(side, possibleSide(list, side))
        }.toMap()

    fun possibleSide(list: List<FourSides>, side: SIDES): List<FourSides> = list.filter { possible -> possible.get(side.opposite()) == get(side) && possible.id != id }

    fun matchSide(side: SIDES, it: FourSides) = get(side) == it.get(side.opposite())


    fun get(side: SIDES) = when(side) {
            TOP -> top
            BOTTOM -> bottom
            LEFT -> left
            RIGHT -> right
        }

    override fun equals(other: Any?): Boolean {
        if (other !is FourSides) return false
        return other.id == id &&
                other.left == left &&
                other.right == right &&
                other.top == top &&
                other.bottom == bottom
    }
}

enum class SIDES {
    TOP,
    BOTTOM,
    LEFT,
    RIGHT;

    fun opposite() = when (this) {
            TOP -> BOTTOM
            BOTTOM -> TOP
            LEFT -> RIGHT
            RIGHT -> LEFT
        }

    companion object {
        fun sideFromString(input: String) =
                when (input) {
                    "top" -> TOP
                    "bottom" -> BOTTOM
                    "left" -> LEFT
                    "right" -> RIGHT
                    else -> null
                }

        fun getDir(x:Int, y:Int, xD:Int, yD:Int):SIDES? {
            val xDif = x.compareTo(xD) // x > xD -> 1 else -1 // x == xD -> 0
            val yDif = y.compareTo(yD) // y > yD -> 1 else -1 // y == yD -> 0

            return when ((xDif*10) + yDif) {
                10 -> TOP
                -10 -> BOTTOM
                1 -> LEFT
                -1 -> RIGHT
                else -> null
            }
        }
    }
}

fun String.fromMapToInt() : Int = this.replace(".", "0").replace("#", "1").toInt(2)

fun List<String>.flipHorizontal() : List<String> = this.asReversed()
fun List<String>.flipVertical() : List<String> = this.map {it.reversed()}
fun List<String>.rotate() : List<String> =
    (this.indices).map { y ->
        (this.indices).map { x ->
            this[x][y]
        }.joinToString("")
    }
