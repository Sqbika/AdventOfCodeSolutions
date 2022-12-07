package y2022.kt.day7

import Solution
import kotlin.math.sign

class Day7 : Solution() {

    override val tests: List<Pair<String, String>>
        get() = listOf(
            "95437" to "24933642"
        )

    override fun part1(input: List<String>): String {

        return createDirectory(input).allDirsPart1().sum().toString()
    }

    override fun part2(input: List<String>): String {
        val root = createDirectory(input)
        val freeSpace = 70_000_000 - root.sum()

        val sizes = root.largestDirPart2()

        var smallest = 99_999_999

        for (size in sizes) {
            if (freeSpace + size > 30_000_000 && size < smallest) {
                smallest = size
            }
        }

        return smallest.toString()
    }

    fun createDirectory(input: List<String>): Directory {
        val root = Directory()

        var cursorDir:Directory = root

        var lsMode = false

        for (line in input.drop(1)) {
            if (line.startsWith("$")) {
                if (line == "$ ls") {
                    lsMode = true
                    continue
                } else {
                    lsMode = false
                }

                if (line.startsWith("$ cd")) {
                    if (line == "$ cd ..") {
                        if (cursorDir.parent == null) throw Exception("uh, something went wrong.")
                        cursorDir = cursorDir.parent!!
                        continue
                    } else {
                        val dirName = line.split(" ")[2]
                        if (!cursorDir.directories.containsKey(dirName)) throw Exception("uh, I didn't find dir $dirName")

                        cursorDir = cursorDir.directories[dirName]!!
                    }
                }
            }

            if (lsMode) {
                val parts = line.split(" ")
                when(parts[0]) {
                    "dir" -> cursorDir.directories[parts[1]] = Directory(cursorDir)
                    else -> cursorDir.files[parts[1]] = parts[0].toInt()
                }
            }
        }

        return root
    }
}

class Directory(
    val parent: Directory? = null,
    val files: MutableMap<String, Int> = mutableMapOf(),
    val directories: MutableMap<String, Directory> = mutableMapOf(),
) {

    fun sum(): Int = files.values.sum() + directories.values.sumOf { it.sum() }

    fun allDirsPart1(): List<Int> {
        if (directories.isEmpty()) return listOf()

        val dirs = mutableListOf<Int>()

        directories.values.forEach { dir ->
            val sum = dir.sum()
            if (sum < 100_000) {
                dirs.add(sum)
            }

            dirs.addAll(dir.allDirsPart1())
        }

        return dirs
    }

    fun largestDirPart2(): List<Int> {
        val toReturn = mutableListOf<Int>()
        toReturn.add(sum())
        toReturn.addAll(directories.values.map{it.sum()})
        toReturn.addAll(directories.values.map{it.largestDirPart2()}.flatten())
        return toReturn
    }
}
