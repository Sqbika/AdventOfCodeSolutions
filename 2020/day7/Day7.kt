package day7

import Solution
import java.util.*
import kotlin.collections.HashMap

fun main(args : Array<String>) {
    val solution = Day7()
    solution.part1()
    println("Part1 done")
    solution.part2()
    println("Part2 done")
}

class Day7 : Solution("day7/") {

    fun lineToNode(line:String): MutableList<List<String>> {
        val parts = line.replace("bags", "bag").replace(".", "").split("contain")
        val result:MutableList<List<String>> = mutableListOf(listOf("1", parts[0].trim().split(' ').joinToString("")))
        parts[1].split(',').forEach {elem ->
            val elemParts = elem.trim().split(' ')
            if (elemParts[0] != "no")
                result.add(listOf(elemParts[0], elemParts.subList(1, elemParts.size).joinToString("")))
        }
        return result
    }

    private fun getNodeMap(): MutableMap<String, Node> {
        val nodes = input.map{lineToNode(it)}
        val nodeMap:MutableMap<String, Node> = mutableMapOf()
        nodes.forEach { node ->
            val mainNode = nodeMap[node[0][1]] ?: Node(node[0][1])

            if (!nodeMap.containsKey(node[0][1]))
                nodeMap[node[0][1]] = mainNode

            if (node.size > 0) {
                node.subList(1, node.size).forEach { child ->
                    val childNode = nodeMap[child[1]] ?: Node(child[1])
                    mainNode.children[childNode.name] = Integer.parseInt(child[0])
                    if (!nodeMap.containsKey(child[1]))
                        nodeMap[child[1]] = childNode
                }
            }
        }
        return nodeMap
    }

    override fun part1() {
        val nodeMap = getNodeMap()

        val validBags = mutableListOf<String>()
        val bagQueue:Queue<String> = LinkedList(listOf("shinygoldbag"))
        do {
            val head = bagQueue.poll()
            nodeMap.forEach {
                if (it.value.children.containsKey(head) && !validBags.contains(it.value.name) && it.value.name != "shinygoldbag") {
                    validBags.add(it.value.name)
                    bagQueue.add(it.value.name)
                }
            }
        } while(bagQueue.isNotEmpty())
        println("Valid bags: " + validBags.distinct().size)
    }

    override fun part2() {
        val nodeMap = getNodeMap()

        println("Bag count: " + (nodeMap["shinygoldbag"]!!.countBag(nodeMap)-1))
    }

}

data class Node(val name:String, val children:MutableMap<String, Int> = HashMap()) {

    override fun toString(): String {
        return name + " Child: [" + children.map{entry -> "${entry.key}: ${entry.value}"}.joinToString(",") + "]"
    }

    fun countBag(nodeMap: MutableMap<String, Node>):Int {
        if (children.isEmpty()) return 1
        return children.map {
            nodeMap[it.key]!!.countBag(nodeMap) * it.value
        }.sum() + 1
    }
}
