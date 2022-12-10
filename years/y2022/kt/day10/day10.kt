package y2022.kt.day10

import Solution

class Day10 : Solution() {

    override val tests: List<Pair<String, String>>
        get() = listOf(
            "13140" to "\n##..##..##..##..##..##..##..##..##..##..\n###...###...###...###...###...###...###.\n####....####....####....####....####....\n#####.....#####.....#####.....#####.....\n######......######......######......####\n#######.......#######.......#######....."
        )

    override fun part1(input: List<String>): String {
        var cycles = 0
        var xRegister = 1

        var sigSum = 0
        val cycleCheckList = listOf(20, 60, 100, 140, 180, 220)

        for (line in input) {
            val parts = line.split(" ")
            when(parts[0]) {
                "noop" ->  {
                    cycles++
                    if (cycleCheckList.contains(cycles)) sigSum += cycles * xRegister
                }
                "addx" -> {
                    cycles ++
                    if (cycleCheckList.contains(cycles)) sigSum += cycles * xRegister
                    cycles ++
                    if (cycleCheckList.contains(cycles)) sigSum += cycles * xRegister

                    xRegister += parts[1].toInt()

                }
            }
        }

        return sigSum.toString()
    }

    override fun part2(input: List<String>): String {
        var cycles = 0
        var xRegister = 1

        var image = ""

        val checkAndAppendToImage = {
            val crtPos = cycles % 40
            image += if (crtPos == xRegister-1 || crtPos == xRegister || crtPos == xRegister+1)
                "#"
            else
                "."

        }

        for (line in input) {
            val parts = line.split(" ")
            when(parts[0]) {
                "noop" ->  {
                    checkAndAppendToImage()
                    cycles++
                }
                "addx" -> {
                    checkAndAppendToImage()
                    cycles ++
                    checkAndAppendToImage()
                    cycles ++

                    xRegister += parts[1].toInt()

                }
            }
        }

        return "\n" + image.chunked(40).joinToString("\n")
    }

}