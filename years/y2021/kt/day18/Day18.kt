package years.y2021.kt.day18

import common.Solution

class Day18 : Solution() {

    override val tests: List<Pair<String, String>> = listOf(
        Pair("143", ""),
        Pair("1384", ""),
        Pair("445", ""),
        Pair("791", ""),
        Pair("1137", ""),
        Pair("3488", ""),
        Pair("4140", "3993"),
        Pair("", ""),
    )

    override fun part1(input: List<String>): String {
        val fishes = input.map { match(it)}.onEach {
            if (it is FishPair) {
            }
        }

        println("=".repeat(40))
        println("Start: ${fishes[0]}")

        val reducedFish: FishPair = fishes.reduce { acc, it ->
            val fish = (acc as FishPair).add(it as FishPair)
            println("Add: $fish")
            while(true) {
                val explode = fish.doExplosions(0)
                if (explode) {
                    //println("Explode: $fish")
                    continue
                }
                val split = fish.doSplits()
                if (split) {
                    //println("Split: $fish")
                    continue
                }
                println("End: $fish")
                break
            }
            fish
        } as FishPair

        return reducedFish.sumPair().toString()
    }

    override fun part2(input: List<String>): String {
        val fishes = input.map { left ->
            input.mapNotNull { right ->
                if (left != right) {
                    val fish = FishPair(match(left), match(right))
                    while(true) {
                        val explode = fish.doExplosions(0)
                        if (explode) {
                            //println("Explode: $fish")
                            continue
                        }
                        val split = fish.doSplits()
                        if (split) {
                            //println("Split: $fish")
                            continue
                        }
                        println("End: $fish")
                        break
                    }
                    fish
                } else {
                    null
                }
            }
        }
            .flatten()
            .map {
                it.sumPair()
            }

        return fishes.maxOf {it}.toString()
    }

    fun match(input: String): Any {
        if (!input.contains(Regex("[\\[\\],]"))) return input.toInt()

        var depth = 0
        var splitIdx = 0
        for (i in input.indices) {
            val chr = input[i]
            if (chr == '[') depth++
            if (chr == ']') depth--

            if (chr == ',' && depth == 1) {
                splitIdx = i
                break;
            }
        }

        return FishPair(
            match(input.substring(1, splitIdx)),
            match(input.substring(splitIdx+1, input.length-1))
        )
    }
}

class FishPair(
    var left: Any,
    var right: Any,
) {

    companion object {
        val colors = listOf(
            "\u001B[0;31m",
            "\u001B[0;32m",
            "\u001B[0;33m",
            "\u001B[0;34m",
            "\u001B[0;35m",
            "\u001B[0;36m",
            "\u001B[0;37m",
        )

        const val reset = "\u001B[0m"

        const val num = "\u001B[33m"
        const val bignum = "\u001B[91m"
    }

    constructor(pair: Pair<Int, Int>) : this(pair.first, pair.second)

    var parent: FishPair? = null

    init {
        if (left is FishPair) {
            (left as FishPair).parent = this
        }

        if (right is FishPair) {
            (right as FishPair).parent = this
        }
    }

    fun isLeftFish() = left is FishPair

    fun left() = left as FishPair

    fun leftInt() = left as Int

    fun isRightFish() = right is FishPair

    fun right() = right as FishPair

    fun rightInt() = right as Int



    fun addToLeft(num: Int, callee: FishPair) {
        if (callee == left) {
            parent?.addToLeft(num, this)
        } else {
            if (isLeftFish()) {
                if (callee == parent)
                    left().addToLeft(num, this)
                else
                    left().addToRight(num, this)
            } else {
                left = num + leftInt()
            }
        }
    }

    fun addToRight(num: Int, callee: FishPair) {
        if (callee == right) {
            parent?.addToRight(num, this)
        } else {
            if (isRightFish()) {
                if (callee == parent)
                    right().addToRight(num, this)
                else
                    right().addToLeft(num, this)
            } else {
                right = num + rightInt()
            }
        }
    }

    fun doExplosions(depth: Int): Boolean {
        if (isLeftFish()) {
            if (depth >= 3) {
                if (left().isLeftFish() || left().isRightFish()) {
                    left().doExplosions(depth+1)
                } else {
                    parent?.addToLeft(left().leftInt(), this)
                    addToRight(left().rightInt(), this)

                    left = 0
                }
                return true
            } else {
                if (left().doExplosions(depth + 1)) return true
            }
        }
        if (isRightFish()) {
            if (depth >= 3) {
                if (right().isLeftFish() || right().isRightFish()) {
                    right().doExplosions(depth+1)
                } else {
                    addToLeft(right().leftInt(), this)
                    parent?.addToRight(right().rightInt(), this)

                    right = 0
                }
                return true
            } else {
                if (right().doExplosions(depth + 1)) return true
            }
        }
        return false
    }

    fun doSplits(): Boolean {
        if (isLeftFish() && left().doSplits())
            return true

        if (!isLeftFish() && leftInt() > 9) {
            left = FishPair(numSplit(leftInt()))
            left().parent = this
            return true
        }

        if (isRightFish() && right().doSplits())
            return true

        if (!isRightFish() && rightInt() > 9) {
            right = FishPair(numSplit(rightInt()))
            right().parent = this
            return true
        }

        return false
    }

    fun add(other: FishPair): FishPair = FishPair(this, other)

    fun sumPair(): Long =
        (if (left is FishPair) (left as FishPair).sumPair() else (left as Int).toLong()) * 3 +
        (if (right is FishPair) (right as FishPair).sumPair() else (right as Int).toLong()) * 2

    fun print(depth: Int = 0): String {
        return  depthColor(depth, "[") +
                (if (isLeftFish()) left().print(depth+1) else charColor(leftInt())) +
                depthColor(depth, ",") +
                (if (isRightFish()) right().print(depth+1) else charColor(rightInt())) +
                depthColor(depth, "]")
    }

    private fun depthColor(depth:Int, text: String): String {
        return colors[depth] + text + reset
    }

    private fun charColor(char: Int): String {
        if (char > 9) {
            return bignum + char + reset
        }
        return "" + char
    }

    override fun toString() = print()

    fun numSplit(num: Int): Pair<Int, Int> = Pair(num/2, num/2 + num%2)
}
