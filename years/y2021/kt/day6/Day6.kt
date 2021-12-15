package y2021.kt.day6

import Solution

class Day6: Solution() {

    override fun part1(input: List<String>): String {
        val fishes = input.get(0).split(",").map{it.toInt()}.toMutableList()

        for (i in 0 until 80) {
            val newFishQueue = mutableListOf<Int>()

             fishes.replaceAll {
                 var count = it-1
                 if (count == -1) {
                     newFishQueue.add(8)
                     count = 6
                 }
                 count
             }

            fishes.addAll(newFishQueue)
            println(i.toString() + ": " + fishes.joinToString(","))
        }

        return fishes.size.toString()
    }

    override fun part2(input: List<String>): String {
        val fishHolder = List(7) {
            Fishes(0, it)
        }

        fun getFishHolder(n:Int): Fishes {
            return fishHolder.find {
                it.day == n
            }!!
        }

        input.get(0).split(",").forEach {
            getFishHolder(it.toInt()).count++
        }

        val fish7 = Fishes(0, 7)
        val fish8 = Fishes(0, 8)

        for (i in 0..255) {
            var fishToAdd: Long = 0

            fishHolder.forEach {
                fishToAdd += it.rotate()
            }

            getFishHolder(6).count += fish7.count
            fish7.count = fish8.count
            fish8.count = fishToAdd
        }

        return (fishHolder.sumOf{it.count} + fish7.count + fish8.count).toString()

    }

    class Fishes(
        var count: Long,
        var day: Int
    ) {
        fun rotate(): Long {
            day--
            if (day == -1) {
                day = 6
                return count
            }

            return 0
        }

        override fun toString(): String = "D$day - $count"
    }
}
