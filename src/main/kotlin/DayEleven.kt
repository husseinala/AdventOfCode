import kotlin.math.abs

object DayEleven {

    fun solve() {
        val map = processInput()

        part1(map)
        part2(map)
    }

    private fun part1(map: List<List<Char>>) {
        val sum = calculateShortestPathSum(map, multiplier = 2)

        println("Part 1: $sum")
    }

    private fun part2(map: List<List<Char>>) {
        val sum = calculateShortestPathSum(map, multiplier = 1_000_000)

        println("Part 2: $sum")
    }

    private fun calculateShortestPathSum(map: List<List<Char>>, multiplier: Int): Long {
        val expandedRows = map.indices.filter { y -> map[y].indices.all { x -> map[y][x] == '.' } }
        val expandedCols = map[0].indices.filter { x -> map.indices.all { y -> map[y][x] == '.' } }

        val stars = mutableListOf<Pair<Long, Long>>()
        map.forEachIndexed { y, line ->
            line.forEachIndexed { x, c ->
                if (c == '#') {
                    stars +=
                        y + (expandedRows.count { it < y } * (multiplier.toLong() - 1)) to
                                x + (expandedCols.count { it < x } * (multiplier.toLong() - 1))
                }
            }
        }

        var sum = 0L

        while (stars.isNotEmpty()) {
            val pos = stars.removeFirst()
            sum += stars.fold(0L) { inc, b -> inc + (abs(pos.first - b.first) + abs(pos.second - b.second)) }
        }

        return sum
    }

    private fun processInput(): List<List<Char>> {
        val map = mutableListOf<List<Char>>()
        readFile("day11.txt").forEachLine {
            map.add(it.toList())
        }

        return map
    }


}