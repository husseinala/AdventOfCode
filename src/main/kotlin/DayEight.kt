import kotlin.math.max

object DayEight {

    fun solve() {
        part1()
        part2()
    }

    private fun part1() {
        val (instructions, nodesMap) = processInput()

        var found = false
        var pos = 0
        var nextNode = "AAA"

        while (!found) {
            val i = instructions[pos % instructions.length]

            nextNode = when (i) {
                'L' -> nodesMap[nextNode]!!.first
                else -> nodesMap[nextNode]!!.second
            }

            found = nextNode == "ZZZ"

            pos++
        }

        println("Part 1: $pos")
    }

    private fun part2() {
        val (instructions, nodesMap) = processInput()

        val startingNodes = nodesMap.keys.filter { it.endsWith("A") }

        val nodesInfo = startingNodes.map {
            var nextNode = it

            var pos = 0L
            while (!nextNode.endsWith('Z')) {
                val i = (pos % instructions.length).toInt()
                val instruction = instructions[i]

                nextNode = when (instruction) {
                    'L' -> nodesMap[nextNode]!!.first
                    else -> nodesMap[nextNode]!!.second
                }
                pos++
            }

            pos
        }

        val ans = findLCM(nodesInfo)

        println("Part 2: $ans")
    }


    private fun processInput(): Pair<String, Map<String, Pair<String, String>>> {
        var instructions = ""
        val nodesMap = mutableMapOf<String, Pair<String, String>>()
        var lineIndex = 0

        readFile("day8.txt").forEachLine {
            if (lineIndex == 0) {
                instructions = it
            } else if (it.isNotBlank()) {
                val (value, nodes) = it.replace(" ", "").split("=")
                val (left, right) = nodes.substring(1, nodes.length - 1).split(",")

                nodesMap[value] = left to right
            }

            lineIndex++
        }

        return instructions to nodesMap
    }

    private fun findLCM(numbers: List<Long>): Long {
        return numbers.reduce { a, b ->
            val inc = max(a, b)
            var lcm = inc

            while (!(lcm % a == 0L && lcm % b == 0L)) {
                lcm += inc
            }

            lcm
        }
    }
}