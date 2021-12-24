package y2021.kt.day24

import Solution
import toDigits
import kotlin.collections.ArrayDeque

class Day24 : Solution() {

    override fun part1(input: List<String>): String {
        val instructions = input.map {
            it.split(" ")
        }

        for (i in 99999999999999 downTo 0) {
            val inpQueue = i.toDigits().toMutableList()
            val cpu = CPU()

            instructions.forEach { ins ->
                if (ins[0] == "ins") {
                    cpu.process("ins", ins[1], inpQueue.removeFirst().toString())
                } else {
                    cpu.process(ins[0], ins[1], ins[2])
                }
            }

            if (cpu.z.getValue() == 0) {
                return i.toString()
            } else {
                println("$i was invalid. Z: ${cpu.z.getValue()}")
            }
        }

        return "-1"
    }

    override fun part2(input: List<String>): String {

        return ""
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
}
