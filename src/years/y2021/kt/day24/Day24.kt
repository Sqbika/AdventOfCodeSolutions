package years.y2021.kt.day24

import common.Solution
import common.toDigits
import java.lang.Math.pow
import kotlin.collections.ArrayDeque
import kotlin.math.absoluteValue
import kotlin.math.pow

class Day24 : Solution() {

    override fun part1(input: List<String>): String {
        return asd(input, false).toString()

        /*val instructions = input.map {
            it.split(" ")
        }

        var curTry: Long = 99999999999999
        val cpu = CPU()

        do {
            while (curTry.toString().contains("0")) {
                curTry -= 10.0.pow((13 - curTry.toString().indexOf("0"))).toLong()
            }

            val inpQueue = curTry.toDigits().toMutableList()
            instructions.forEach { ins ->
                if (ins[0] == "inp") {
                    cpu.process(ins[0], ins[1], inpQueue.removeFirst().toString())
                } else {
                    cpu.process(ins[0], ins[1], ins[2])
                }
            }

            if (cpu.z.getValue() != 0) {
                //println("Code $curTry failed. Gap: ${cpu.z.getValue()}")
                curTry--;
            }
        } while(cpu.z.getValue() != 0)


        return curTry.toString()*/
    }

    override fun part2(input: List<String>): String {

        return ""
    }

    fun asd(lines: List<String>, part2: Boolean = false): Long {
        val blocks = lines.chunked(18)
        val result = MutableList(14) { -1 }
        val buffer = ArrayDeque<Pair<Int, Int>>()
        fun List<String>.lastOf(command: String) = last { it.startsWith(command) }.split(" ").last().toInt()
        val best = if (part2) 1 else 9
        blocks.forEachIndexed { index, instructions ->
            if ("div z 26" in instructions) {
                val offset = instructions.lastOf("add x")
                val (lastIndex, lastOffset) = buffer.removeFirst()
                val difference = offset + lastOffset
                if (difference >= 0) {
                    result[lastIndex] = if (part2) best else best - difference
                    result[index] = if (part2) best + difference else best
                } else {
                    result[lastIndex] = if (part2) best - difference else best
                    result[index] = if (part2) best else best + difference
                }
            } else buffer.addFirst(index to instructions.lastOf("add y"))
        }

        return result.joinToString("").toLong()
    }
}

class CPU() {

    var w = IntHolder()
    var x = IntHolder()
    var y = IntHolder()
    var z = IntHolder()

    fun process(inst: String, a: String, b: String) {
        when (inst) {
            "inp" -> reg(a).setValue(reg(b))
            "add" -> reg(a) += reg(b)
            "mul" -> reg(a) *= reg(b)
            "div" -> reg(a) /= reg(b)
            "mod" -> reg(a) %= reg(b)
            "eql" -> reg(a) == reg(b)
        }
    }

    private fun reg(reg: String): IntHolder {
        return when (reg) {
            "w" -> w
            "x" -> x
            "y" -> y
            "z" -> z
            else -> IntHolder(reg.toInt())
        }
    }

    fun reset() {
        w.setValue(0)
        x.setValue(0)
        y.setValue(0)
        z.setValue(0)
    }

    override fun toString(): String = "İnt($w,$x,$y,$z)"
}

data class IntHolder(
    private var value: Int = 0
) {

    fun setValue(newValue: Int) {
        value = newValue
    }

    fun setValue(newValue: IntHolder) = setValue(newValue.getValue())

    fun getValue() = value

    fun add(other: IntHolder) {
        value += other.getValue()
    }

    operator fun plusAssign(y: IntHolder) {
        this.add(y)
    }

    operator fun timesAssign(other: IntHolder) {
        value *= other.getValue()
    }

    operator fun divAssign(other: IntHolder) {
        value /= other.getValue()
    }

    operator fun remAssign(other: IntHolder) {
        value %= other.getValue()
    }

    override operator fun equals(other:Any?): Boolean {
        if (other == null || other !is IntHolder) return false

        if (other.getValue() == value) {
            value = 1
            return true
        } else {
            value = 0
            return false
        }
    }

    override fun toString(): String = value.toString()
}
