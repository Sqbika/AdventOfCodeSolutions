package y2021.kt.day16

import Solution
import dropTake
import productOf

class Day16 : Solution() {

    override val tests: List<Pair<String, String>> = listOf(
        Pair("16", ""),
        Pair("12", ""),
        Pair("23", ""),
        Pair("31", ""),
        Pair("", "3"),
        Pair("", "54"),
        Pair("", "7"),
        Pair("", "9"),
        Pair("", "1"),
        Pair("", "0"),
        Pair("", "0"),
        Pair("", "1")
    )


    override fun part1(input: List<String>): String {
        val bytes = input[0].map {
            Integer.toBinaryString(it.digitToInt(16)).padStart(4, '0')
        }.joinToString("").toCharArray().map { it.toString() }.toMutableList()

        val packet = Packet(bytes)

        return packet.sumChild().toString()
    }

    override fun part2(input: List<String>): String {
        val bytes = input[0].map {
            Integer.toBinaryString(it.digitToInt(16)).padStart(4, '0')
        }.joinToString("").toCharArray().map { it.toString() }.toMutableList()

        val packet = Packet(bytes)

        return packet.sumChild(true).toString()
    }

}

class Packet(
    input: MutableList<String>
) {
    val packetVersion: Long
    val typeID: Long

    val subPackets = mutableListOf<Packet>()

    var literalValue: Long = 0

    init {
        packetVersion = input.dropTake(3).hexToLong()
        typeID = input.dropTake(3).hexToLong()

        if (typeID != 4L) {
            if (input.dropTake(1)[0] == "0") {
                //15
                val subPacketLength = input.dropTake(15).hexToLong()

                val subPacketInput = input.dropTake(subPacketLength.toInt())

                while (subPacketInput.isNotEmpty()) // 6 is a safe bet, right?
                    subPackets.add(Packet(subPacketInput))
            } else {
                //11
                val packetCount = input.dropTake(11).hexToLong()
                repeat(packetCount.toInt()) {
                    if (input.size == 0) {
                        throw RuntimeException("We have size 0 yet we are counting? what")
                    }
                    subPackets.add(Packet(input))
                }
            }
        } else {
            var literalValueString = ""

            while (input.size > 0) {
                val packet = input.dropTake(5)

                packet.drop(1).forEach {
                    literalValueString += it
                }

                if (packet[0] == "0") {
                    break
                }
            }

            literalValue = literalValueString.toLong(2)
        }
    }

    fun sumChild(part2: Boolean = false): Long {
        return if (!part2) {
            packetVersion + subPackets.sumOf { it.sumChild() }
        } else {
            when (typeID) {
                0L -> subPackets.sumOf { it.sumChild(true) }
                1L -> subPackets.productOf { it.sumChild(true) }
                2L -> subPackets.minOf {it.sumChild(true) }
                3L -> subPackets.maxOf {it.sumChild(true) }
                5L -> if (subPackets[0].sumChild(true) > subPackets[1].sumChild(true)) 1 else 0
                6L -> if (subPackets[0].sumChild(true) < subPackets[1].sumChild(true)) 1 else 0
                7L -> if (subPackets[0].sumChild(true) == subPackets[1].sumChild(true)) 1 else 0
                else -> literalValue
            }
        }
    }
}

fun List<String>.hexToLong(): Long = this.joinToString("").toLong(2)
