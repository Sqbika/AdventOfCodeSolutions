import java.io.File
import java.io.FileReader

abstract class Solution(path:String) {

    val input:List<String> by lazy {File(path+"input.txt").readLines()}

    abstract fun part1()

    abstract fun part2()
}
