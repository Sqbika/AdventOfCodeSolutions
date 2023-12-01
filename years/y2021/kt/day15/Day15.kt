package years.y2021.kt.day15

import common.Solution
import java.util.*

class Day15: Solution() {

    override val tests = listOf(
        Pair("40", "315"),
        Pair("","")
    )

    override fun part1(input: List<String>): String {
        val field = input.map{it.toCharArray().map{it.digitToInt()}}

        val pathLengths = mutableListOf<Int>()

        val nodes = createNodeGraph(field)

        val startNode = nodes.find {
            it.x == 0 && it.y == 0
        }!!

        //StartNode is 0?
        startNode.minDisVal = 0
        startNode.weigth = 0

        calculateWeightGraph(startNode)

        val destNode = nodes.find {
            it.x == input.size-1 && it.y == input.last().length-1
        }!!

        return destNode.minDisVal.toString()
    }

    override fun part2(input: List<String>): String {
        val field = input.map{
            it.toCharArray().map{
                it.digitToInt()
            }.run {
                (0..4).map { n ->
                    this.map {
                        (it+n-1) % 9 + 1
                    }
                }.flatten()
            }
        }.run {
            (0..4).map { n ->
                this.map {
                    it.map {(it+n-1) % 9 + 1}
                }
            }.flatten()
        }

        val nodes = createNodeGraph(field)

        val startNode = nodes.find {
            it.x == 0 && it.y == 0
        }!!

        //StartNode is 0?
        startNode.minDisVal = 0
        startNode.weigth = 0

        calculateWeightGraph(startNode)

        val destNode = nodes.find {
            it.x == (input.size*5)-1 && it.y == (input.last().length*5)-1
        }!!

        return destNode.minDisVal.toString()
    }

    fun createNodeGraph(input: List<List<Int>>): List<Node> {
        val nodes = input.mapIndexed { yIdx, y  ->
            y.mapIndexed { xIdx, x  ->
                Pair(yIdx, xIdx) to Node(
                    x,
                    yIdx,
                    xIdx
                )
            }.toMap()
        }.fold(mutableMapOf<Pair<Int,Int>, Node>()) { acc, map ->
            acc.also { it.putAll(map)}
        }

        for (pair in nodes.entries) {
            for (j in listOf(Pair(0,1), Pair(1,0), Pair(-1,0), Pair(0, -1))) {
                val pos = Pair(pair.key.first + j.first, pair.key.second + j.second)
                if (nodes.containsKey(pos)) {
                    pair.value.nodes.add(nodes[pos]!!)
                }
            }
        }

        return nodes.values.toList()
    }

    fun calculateWeightGraph(startNode: Node) {
        val nodeQueue: Deque<Node> = LinkedList(mutableListOf(startNode))

        while (nodeQueue.isNotEmpty()) {
            val node = nodeQueue.pop()
            node.visited = true

            node.nodes.forEach {
                if (node.minDisVal + it.weigth < it.minDisVal) {
                    it.minDisVal = node.minDisVal + it.weigth
                    nodeQueue.add(it)
                } else if (!nodeQueue.contains(it) && !it.visited)
                    nodeQueue.add(it)
            }
        }
    }
}

data class Node(
    var weigth: Int,
    val x: Int,
    val y: Int,
    val nodes: MutableList<Node> = mutableListOf(),
    var minDisVal: Int = Int.MAX_VALUE,
    var visited: Boolean = false
) {

    override fun toString(): String {
        return "[$x,$y] - $weigth -> $minDisVal"
    }
}
