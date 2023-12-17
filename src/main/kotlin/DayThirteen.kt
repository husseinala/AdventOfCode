import kotlin.math.min

object DayThirteen {

    fun solve() {
        val map = processInput()

        part1(map)
        part2(map)
    }

    private fun part1(maps: List<List<List<Char>>>) {
        var sum = 0
        maps.forEach { map ->
            var reflection = findVerticalReflection(map, errors = 0)

            reflection = if (reflection != -1) {
                reflection
            } else {
                findHorizontalReflection(map, errors = 0) * 100
            }

            sum += reflection
        }

        println("Part 1: $sum")

    }

    private fun part2(maps: List<List<List<Char>>>) {
        var sum = 0
        maps.forEach { map ->
            var reflection = findVerticalReflection(map, errors = 1)

            reflection = if (reflection != -1) {
                reflection
            } else {
                findHorizontalReflection(map, errors = 1) * 100
            }

            sum += reflection
        }

        println("Part 1: $sum")
    }

    private fun findVerticalReflection(map: List<List<Char>>, errors: Int): Int {
        return (map[0].indices.toList().dropLast(1).firstOrNull { x ->
            var errorCount = map.indices.sumBy { y -> if (map[y][x] == map[y][x + 1]) 0 else 1 }

            errorCount += if ((x == 0 || x > map[0].lastIndex - 1)) {
                0
            } else {
                (1..min(
                    map[0].lastIndex - (x + 1),
                    x
                )).sumBy { i ->
                    map.indices.sumBy { y -> if (map[y][x - i] == map[y][x + 1 + i]) 0 else 1 }
                }
            }

            errorCount == errors
        } ?: -2) + 1
    }

    private fun findHorizontalReflection(map: List<List<Char>>, errors: Int): Int {
        return (map.indices.toList().dropLast(1).firstOrNull { y ->
            var errorCount = map[y].indices.sumBy { x -> if (map[y][x] == map[y + 1][x]) 0 else 1 }

            errorCount += if (y == 0 || y > map.lastIndex - 1) {
                0
            } else {
                (1..min(
                    map.lastIndex - (y + 1),
                    y
                )).sumBy { i ->
                    map[0].indices.sumBy { x -> if (map[y - i][x] == map[y + 1 + i][x]) 0 else 1 }
                }
            }

            errorCount == errors
        } ?: -2) + 1
    }

    private fun processInput(): List<List<List<Char>>> {
        val maps = mutableListOf<List<List<Char>>>()

        var map = mutableListOf<List<Char>>()
        readFile("day13.txt").forEachLine {
            if (it.isEmpty()) {
                maps.add(map)
                map = mutableListOf()
            } else {
                map.add(it.toList())
            }
        }

        maps.add(map)

        return maps
    }
}