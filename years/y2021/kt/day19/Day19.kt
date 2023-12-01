package years.y2021.kt.day19

import common.Solution
import kotlin.math.absoluteValue
import kotlin.math.sqrt
import kotlin.math.pow

class Day19 : Solution() {
    override val tests: List<Pair<String, String>> = listOf(
        Pair("3", ""),
        Pair("79", ""),
        Pair("4", "")
    )

    override fun part1(input: List<String>): String {
        val beacons = input.mapIndexedNotNull() { index, s ->
            if (s.startsWith("---"))
                index
            else
                null
        }.run {
            this.toMutableList().also {
                it.add(input.size + 1)
            }
        }
            .windowed(2)
            .map {
                Scanner(
                    input.subList(it[0] + 1, it[1] - 1).map {
                        it.split(",").run {
                            Triple(get(0).toInt(), get(1).toInt(), get(2).toInt())
                        }
                    }
                )
            }

        val allBeacons = beacons.subList(1, beacons.size).map {
            Scanner.allRots(it).map {
                it.beaconDistances().groupingBy {
                    it
                }.eachCount().toMutableMap()
            }
        }.fold(
            beacons
                .first()
                .beaconDistances()
                .groupingBy {
                    it
                }.eachCount()
                .toMutableMap()
        ) { acc, rotations ->
            rotations.sortedBy { rot ->
                rot.map {
                    if (acc.containsKey(it.key)) {
                        (acc[it.key]!! - it.value).absoluteValue
                    } else
                        0
                }.sum()
            }

            rotations.last().forEach {
                if (acc.containsKey(it.key)) {
                    if (acc[it.key]!! < it.value) {
                        acc[it.key] = it.value
                    }
                } else {
                    acc[it.key] = it.value
                }
            }


            acc
        }


        return allBeacons.size.toString()
    }

    override fun part2(input: List<String>): String {
        return "TODO"
    }
}

class Scanner(
    val beacons: List<Triple<Int, Int, Int>>,
    val rot: Int = 0
) {

    companion object {
        val rots = listOf(
            Pair(1, 1),
            Pair(-1, 1),
            Pair(-1, -1),
            Pair(1, -1),
        ).run {
            zip(this).map { p ->
                this.map {
                    Triple(p.first, p.second, it)
                }
            }.flatten()
        }

        fun rot(s: Scanner): Scanner {
            val rotBy = rots[(s.rot + 1) % 64]
            //What is happening?!
            return Scanner(
                s.beacons.map {
                    Triple(it.second * rotBy.first.first, it.first * rotBy.first.second, it.third)
                }.map {
                    Triple(it.third * rotBy.second.first, it.second, it.first * rotBy.second.second)
                }.map {
                    Triple(it.first, it.third * rotBy.third.first, it.second * rotBy.third.second)
                }
            )
        }

        fun allRots(s: Scanner): List<Scanner> = mutableListOf(s).also { list ->
            repeat(15) {
                list.add(rot(list.last()))
            }
        }
    }

    fun beaconDistances(): List<Double> =
        beacons.map { left ->
            beacons.mapNotNull { right ->
                if (left != right) {
                    Triple(
                        (left.first - right.first).absoluteValue,
                        (left.second - right.second).absoluteValue,
                        (left.third - right.third).absoluteValue
                    )
                } else {
                    null
                }
            }.map {
                sqrt(it.first.toDouble().pow(2) + it.second.toDouble().pow(2) + it.third.toDouble().pow(2))
            }
        }.flatten()
}
