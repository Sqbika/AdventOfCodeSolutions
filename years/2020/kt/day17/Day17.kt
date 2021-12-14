package day17

import Solution

class Day17(path:String) : Solution(path) {

    override fun part1() {
        val start = System.currentTimeMillis()
        println("Part1: ${solveDay1(input, 6)}")
        println("Part1 took: ${System.currentTimeMillis() - start} ms")
    }

    override fun part2() {
        val start = System.currentTimeMillis()
        println("Part1: ${solveDay2(input, 6)}")
        println("Part1 took: ${System.currentTimeMillis() - start} ms")
    }

    override fun test() {
        val start = System.currentTimeMillis()
        println("Test1: ${solveDay1(test, 6)}")
        println("Test1 took: ${System.currentTimeMillis() - start} ms")
        val start2 = System.currentTimeMillis()
        println("Test2: ${solveDay2(test, 6)}")
        println("Test2 took: ${System.currentTimeMillis() - start2} ms")
    }

    private fun solveDay1(input:List<String>, cycles:Int) : Double {
        val grid = Grid(input, false)
        for (i in 0 until cycles) {
            println("Doing Iter $i")
            val start = System.currentTimeMillis()
            grid.iter()
            println("Iter $i took: ${System.currentTimeMillis() - start} ms")
        }
        return grid.activeCount()
    }

    private fun solveDay2(input:List<String>, cycles:Int) : Double {
        val grid = Grid(input, true)
        for (i in 0 until cycles) {
            println("Doing Iter $i")
            val start = System.currentTimeMillis()
            grid.iter()
            println("Iter $i took: ${System.currentTimeMillis() - start} ms")
        }
        return grid.activeCount()
    }
}

class Grid(val input: List<String>, val doWYes: Boolean) {

    val i:Int = input.size/2
    val ni:Int = i * -1

    private var nodes : List<Matrix3Dand4D> = run {
        val cube = generateSquare(if (doWYes) (-1..1) else listOf(0), (-1..1), (ni - 1)..(i + 1), (ni - 1)..(i + 1))
        input.forEachIndexed { x, line ->
            line.toCharArray()
                    .forEachIndexed { y, char ->
                        if (char == '#')
                            cube.find { it.x == (x-i) && it.y == (y-i) && it.z == 0 && it.w == 0}?.active = true
                    }
        }
        cube
    }.sorted()

    var curSize = i+1
    var zSize = 1

    fun get(x:Int, y:Int, z:Int, w:Int = 0):Matrix3Dand4D? = get(Quad(w, z, x, y))
    //fun get(pos:Quad<Int,Int,Int,Int>):Matrix3Dand4D? = nodes.find { it.compareTo(pos) == 0 }
    fun get(pos:Quad<Int,Int,Int,Int>):Matrix3Dand4D? = nodes.getOrNull(nodes.binarySearch { it.compareTo(pos) })

    fun getNeighbours(point: Matrix3Dand4D): List<Matrix3Dand4D> {
        val coords = point.neighbourCords(doWYes)
        return coords.mapNotNull { get(it) }
    }

    private fun expandItOnce() {
        curSize++
        zSize++
        val negCurSize = curSize*-1
        val negZed = zSize * -1
        val wVal = if (doWYes) (negZed+1 until zSize) else listOf(0)
        nodes = nodes +
                generateSquare(wVal, listOf(negZed, zSize), (negCurSize..curSize), (negCurSize..curSize)) + //Top and Bottom
                generateSquare(wVal, (negZed+1 until zSize), listOf(negCurSize, curSize), (negCurSize..curSize)) + // Front and Back
                generateSquare(wVal, (negZed+1 until zSize), (negCurSize+1 until curSize), listOf(negCurSize, curSize)) // Left and Right
        if (doWYes)
            nodes = nodes + generateSquare(listOf(negZed, zSize), (negZed..zSize), (negCurSize..curSize), (negCurSize..curSize))
        nodes = nodes.sorted()
    }

    private fun copyNodeList() = nodes.toList().map{it.copy()}.sorted()

    fun iter() {
        val newNodes = copyNodeList()
        newNodes.forEach { node ->
            val activeCount = node.countActiveNeighbours(this)
            if (!node.active && activeCount == 3)
                node.active = true
            else if (node.active && activeCount != 2 && activeCount != 3)
                node.active = false
        }
        nodes = newNodes
        expandItOnce()
    }

    override fun toString(): String = toString(nodes)

    fun toString(nodeList : List<Matrix3Dand4D>): String =
            nodeList.groupBy { it.z }.toSortedMap().map { zGroup: Map.Entry<Int, List<Matrix3Dand4D>> ->
                if (doWYes) {
                    zGroup.value.groupBy { it.w }.toSortedMap().map { wGroup: Map.Entry<Int, List<Matrix3Dand4D>> ->
                        var result = "Z=${zGroup.key}, W=${wGroup.key}\n"
                        wGroup.value.groupBy { it.x }.toSortedMap().map { xGroup: Map.Entry<Int, List<Matrix3Dand4D>> ->
                            result += xGroup.value.sortedBy { it.y }.map { if (it.active) '#' else '.' }.joinToString("")
                            result += "\n"
                        }
                        result
                    }.joinToString("\n\n")
                } else {
                    var result = "Z=${zGroup.key}\n"
                    zGroup.value.groupBy { it.x }.toSortedMap().map { xGroup: Map.Entry<Int, List<Matrix3Dand4D>> ->
                        result += xGroup.value.sortedBy { it.y }.map { if (it.active) '#' else '.' }.joinToString("")
                        result += "\n"
                    }
                    result
                }
            }.joinToString("\n\n")

    private fun generateSquare(w: Iterable<Int> = listOf(0), z: Iterable<Int>, x: Iterable<Int>, y: Iterable<Int>, active: Boolean = false):List<Matrix3Dand4D> =
            w.map { wP ->
                z.map { zP ->
                    x.map { xP ->
                        y.map { yP ->
                            Matrix3Dand4D(wP, zP, xP, yP, active)
                        }
                    }.flatten()
                }.flatten()
            }.flatten()

    fun activeCount() = nodes.count { it.active }.toDouble()

    fun groupBePls(nodeee:List<Matrix3Dand4D>) =
    nodeee.groupBy { it.getWValue() }.toSortedMap().map { wGroup: Map.Entry<Int, List<Matrix3Dand4D>> ->
        wGroup.value.groupBy {it.getZValue() }.toSortedMap().map { zGroup: Map.Entry<Int, List<Matrix3Dand4D>> ->
            zGroup.value.groupBy {it.getXValue() }.toSortedMap().map { xGroup: Map.Entry<Int, List<Matrix3Dand4D>> ->
                xGroup.value.sortedBy { it.getYValue() }
            }
        }
    }

    fun getNodes() = nodes
}

//X: Forward
//Z: Up'n'Down
//Y: Side to side like a roller coaster
//W: this does not bring joy

data class Matrix3Dand4D(
        public val w: Int = 0,
        public val z: Int = 0,
        public val x: Int = 0,
        public val y: Int = 0,
        public var active: Boolean = true
) : Comparable<Matrix3Dand4D> {

    fun copy(active:Boolean = this.active) = Matrix3Dand4D(w, z, x, y, active)

    fun countActiveNeighbours(grid:Grid) = grid.getNeighbours(this).count { it.active }

    fun neighbourCords(doW:Boolean = false): List<Quad<Int, Int, Int, Int>> =
            (if (doW) listOf(w -1, w, w +1) else listOf(0)).map { wP ->
                listOf(z -1, z, z +1).map { zP ->
                    listOf(x -1, x, x +1).map { xP ->
                        listOf(y -1, y, y +1).map { yP ->
                            Quad(wP, zP, xP, yP)
                        }
                    }.flatten()
                }.flatten()
            }.flatten().filter { !(it.w == w && it.z == z && it.x == x && it.y == y) }

    fun toQuad(): Quad<Int,Int,Int,Int> = quadFromMatrix(this)

    public fun getZValue() = z
    public fun getYValue() = y
    public fun getXValue() = x
    public fun getWValue() = w


    override fun compareTo(other: Matrix3Dand4D): Int {
        if (other == this) return 0
        return compareTo(other.toQuad())
    }

    fun compareTo(other: Quad<Int,Int,Int,Int>): Int {
        listOf(Pair(w, other.w), Pair(z, other.z), Pair(x, other.x), Pair(y, other.y))
            .forEach { nums ->
                if (nums.first.compareTo(nums.second) != 0) return nums.first.compareTo(nums.second)
            }
        return 0
    }
}

data class Quad<A,B,C,D>(
        val w:A,
        val z:B,
        val x:C,
        val y:D,
)

fun quadFromMatrix(m: Matrix3Dand4D) = Quad(m.w, m.z, m.x, m.y)
