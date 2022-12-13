package day16

import common.Solution

class Day16(path:String) : Solution(path) {

    override fun part1() {
        println("Part1 : ${solvePart1(input)}")
    }

    override fun part2() {
        println("Part2 : ${solvePart2(input)}")
    }

    override fun test() {
        println("Test1 : ${solvePart1(test)}")
        println("Test2 : ${solvePart2(test)}")
    }

    private fun solvePart1(input:List<String>): Int {
        val rules = input
                .subList(0, input.indexOf("your ticket:")-1)
                .map { ruleLine:String ->
                    ruleLine
                            .split(": ")[1]
                            .split(" or ")
                            .map { rule ->
                                val nums = rule.split("-")
                                IntRange(nums[0].toInt(), nums[1].toInt())
                            }
                }.flatten()
        val nums = input
                .subList(input.indexOf("nearby tickets:")+1, input.size)
                .joinToString(",")
                .split(",")
                .map{it.toInt()}
                .sorted()
        return nums.filter {num -> rules.none {range -> range.contains(num)}}.sum()
    }

    private fun solvePart2(input:List<String>): Long {
        val rules = input
                .subList(0, input.indexOf("your ticket:")-1)
                .map { ruleLine:String ->
                    val ruleparts = ruleLine.split(": ")
                    val intranges = ruleparts[1].split(" or ")
                            .map { rule ->
                                val nums = rule.split("-")
                                IntRange(nums[0].toInt(), nums[1].toInt())
                            }
                    Triple(ruleparts[0], intranges, mutableListOf(-1))
                }

        val tickets = input
                .subList(input.indexOf("nearby tickets:")+1, input.size)
                .map {
                    it.split(",").map{num -> num.toInt()}
                }
                .filter {nums ->
                    nums.all { num ->
                        rules.any {rule ->
                            rule.second.any { range ->
                                range.contains(num)
                            }
                        }
                    }
                }

        val occupiedNums = mutableListOf<Int>()

        val possibleRanges = rules.map { rule ->
            Pair(
                    rule.first,
                    (tickets[0].indices).asSequence().map{ idx ->
                        tickets.all{ ticket ->
                            rule.second.any { range ->
                                range.contains(ticket[idx])
                            }
                        }
                    }
                    .mapIndexed{ idx, bool -> Pair(idx, bool)}
                    .filter{it.second}.map{it.first}.toMutableList()
            )
        }.sortedBy { it.second.size }

        possibleRanges.forEach { ranges ->
            occupiedNums.forEach { num -> ranges.second.remove(num)}
            occupiedNums.add(ranges.second.first())
        }

        val myticket = input.subList(input.indexOf("your ticket:")+1,input.indexOf("your ticket:")+2)[0].split(",").map{it.toInt()}
        return possibleRanges.filter {it.first.startsWith("departure")}.map{myticket[it.second.first()].toLong()}.reduce(Long::times)
    }
}
