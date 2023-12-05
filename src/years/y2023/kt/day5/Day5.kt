package years.y2023.kt.day5

import common.Solution
import common.debugPrint

class Day5 : Solution() {

    override val tests: List<Pair<String, String>>
        get() = listOf(
            "35" to "46"
        )

    fun parseInput(input: List<String>) =
        input.joinToString("\n").split("\n\n").map {
            it.split(":", limit = 2)[1].trim().split("\n").map {
                ConversionEntry(it.trim().split(" "))
            }
        }

    override fun part1(input: List<String>): String = input[0]
        .split(":")[1]
        .trim()
        .split(" ")
        .minOf {
            parseInput(input.drop(2))
                .fold(it.toLong()) { acc, cur ->
                    ((cur.find { it.inRange(acc) }?.convert ?: 0) + acc)
                }
        }.toString()

    override fun part2(input: List<String>): String = input[0]
    .split(":")[1]
    .trim()
    .split(" ")
    .map { it.toLong()}
    .chunked(2)
    .map { LongRange(it[0], it[0] + it[1]-1) }
    .minOf { seedRange ->
        parseInput(input.drop(2))
            .fold(listOf(seedRange)) { curRange, mapRanges ->
                val ranges = curRange.toMutableList()
                val output = mutableListOf<List<LongRange>>()

                for (mapRange in mapRanges) {
                    val matches = ranges.filter {mapRange.range.hasOverlap(it)}
                    ranges.removeAll {
                        mapRange.range.hasOverlap(it)
                    }
                    output.add(mapRange.translateAll(matches))
                }
                output.add(ranges)

                return@fold output.flatten().collapse().debugPrint()

            }.debugPrint("[Output] -> ").minOf { it.first }
    }
    .toString()
}

class ConversionEntry(line: List<String>) {

    val range: LongRange
    val from: Long
    val to: Long
    val convert: Long

    init {
        to = line[0].toLong()
        from = line[1].toLong()
        convert = to - from

        range = LongRange(from, from+line[2].toLong())
    }

    override fun toString(): String = "($range [$convert])"

    fun inRange(number: Long) = range.contains(number)

    fun translateAll(from: List<LongRange>): List<LongRange> = from.map { translate(it) }.flatten().collapse()

    fun translate(from: LongRange): List<LongRange> {
        if (range.contains(from)) {
            return listOf(from.shift(convert))
        }

        if (from.contains(range)) {
            return listOf(
                LongRange(from.first, range.first-1),
                range.shift(convert),
                LongRange(range.last+1, from.last)
            )
        }

        if (range.intersectsRight(from)) {
            return listOf(
                LongRange(from.first, range.last).shift(convert),
                LongRange(range.last+1, from.last)
            )
        }

        if (from.intersectsRight(range)) {
            return listOf(
                LongRange(from.first, range.first-1),
                LongRange(range.first, from.last).shift(convert)
            )
        }

        return listOf(from)
    }

}

fun LongRange.hasOverlap(other: LongRange): Boolean = contains(other) || intersects(other)
fun LongRange.contains(other: LongRange): Boolean = first <= other.first && other.last <= last
fun LongRange.intersectsRight(other: LongRange): Boolean = first < other.first && other.first < last && last < other.last
fun LongRange.intersects(other: LongRange): Boolean {
    if (contains(other)) return false

    if (first < other.first)
        return intersectsRight(other)
    else
        return other.intersectsRight(this)
}

fun LongRange.combine(other: LongRange): LongRange = LongRange(kotlin.math.min(first, other.first), kotlin.math.max(last, other.last))
fun LongRange.shift(amount: Long): LongRange = LongRange(first + amount, last + amount).also {
    if (it.first < 0) {
        throw Error("It became negative? $this + $amount")
    }
}

fun List<LongRange>.collapse() : List<LongRange> = sortedBy { it.first }.run {
    val result = mutableListOf<LongRange>()
    if (isEmpty()) return@run result

    var head = get(0)
    if (size == 1) return@run listOf(head)

    for (cursor in drop(1)) {
        head = when {
            head == cursor -> head
            head.contains(cursor) -> head
            cursor.contains(head) -> cursor
            head.intersects(cursor) -> head.combine(cursor)
            else -> {
                result.add(head)
                cursor
            }
        }
    }

    result.add(head)
    return@run result
}