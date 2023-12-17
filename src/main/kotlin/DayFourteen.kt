object DayFourteen {

    fun solve() {
        val map = processInput()

        part1(map)
        part2(map)
    }

    private fun part1(map: List<List<Char>>) {
        var sum = 0

        for (x in map[0].indices) {
            var position = 0
            for (y in map.indices) {
                if (map[y][x] == 'O') {
                    sum += map.size - position
                    position++
                } else if (map[y][x] == '#') {
                    position = y + 1
                }
            }
        }

        println("Part 1: $sum")
    }

    private fun part2(map: List<List<Char>>) {
        var updatedMap = mutableMapOf<Pair<Int, Int>, Char>()

        for (x in map[0].indices) {
            for (y in map.indices) {
                updatedMap[y to x] = map[y][x]
            }
        }

        val cycleSums = mutableMapOf<Int, Int>()
        var expectedPosition = -1


        for (i in 0 until 1000000000) {
            updatedMap = moveUp(updatedMap, map[0].size, map.size).toMutableMap()
            updatedMap = moveLeft(updatedMap, map[0].size, map.size).toMutableMap()
            updatedMap = moveDown(updatedMap, map[0].size, map.size).toMutableMap()
            updatedMap = moveRight(updatedMap, map[0].size, map.size).toMutableMap()

            val sum = getCount(updatedMap, map.size)

            if (cycleSums.containsKey(sum)) {
                expectedPosition = i + ((i - cycleSums[sum]!!) % 4)

                cycleSums.clear()
            } else if (expectedPosition == i) {
                break
            } else {
                cycleSums[sum] = i
            }
        }

        val sum = getCount(updatedMap, map.size)

        println("Part 2: $sum")
    }

    private fun moveUp(
        map: Map<Pair<Int, Int>, Char>,
        cols: Int,
        rows: Int,
    ): Map<Pair<Int, Int>, Char> {
        val updatedMap = mutableMapOf<Pair<Int, Int>, Char>()

        for (x in 0 until cols) {
            var position = 0
            for (y in 0 until rows) {
                if (map[y to x] == 'O') {
                    updatedMap[position to x] = 'O'
                    position++
                } else if (map[y to x] == '#') {
                    position = y

                    updatedMap[position to x] = '#'

                    position++
                }
            }
        }

        return updatedMap
    }

    private fun moveDown(
        map: Map<Pair<Int, Int>, Char>,
        cols: Int,
        rows: Int,
    ): Map<Pair<Int, Int>, Char> {
        val updatedMap = mutableMapOf<Pair<Int, Int>, Char>()

        for (x in 0 until cols) {
            var position = rows - 1
            for (y in rows - 1 downTo 0) {
                if (map[y to x] == 'O') {
                    updatedMap[position to x] = 'O'
                    position--
                } else if (map[y to x] == '#') {
                    position = y

                    updatedMap[position to x] = '#'

                    position--
                }
            }
        }

        return updatedMap
    }

    private fun moveLeft(
        map: Map<Pair<Int, Int>, Char>,
        cols: Int,
        rows: Int,
    ): Map<Pair<Int, Int>, Char> {
        val updatedMap = mutableMapOf<Pair<Int, Int>, Char>()

        for (y in 0 until rows) {
            var position = 0
            for (x in 0 until cols) {
                if (map[y to x] == 'O') {
                    updatedMap[y to position] = 'O'
                    position++
                } else if (map[y to x] == '#') {
                    updatedMap[y to x] = '#'
                    position = x + 1
                }
            }
        }

        return updatedMap
    }

    private fun moveRight(
        map: Map<Pair<Int, Int>, Char>,
        cols: Int,
        rows: Int,
    ): Map<Pair<Int, Int>, Char> {
        val updatedMap = mutableMapOf<Pair<Int, Int>, Char>()

        for (y in 0 until rows) {
            var position = cols - 1
            for (x in cols - 1 downTo 0) {
                if (map[y to x] == 'O') {
                    updatedMap[y to position] = 'O'
                    position--
                } else if (map[y to x] == '#') {
                    updatedMap[y to x] = '#'
                    position = x - 1
                }
            }
        }

        return updatedMap
    }

    private fun getCount(map: Map<Pair<Int, Int>, Char>, rows: Int): Int {
        val sum = map.mapValues { if (it.value == 'O') rows - it.key.first else 0 }.values.sum()

        return sum
    }

    private fun processInput(): List<List<Char>> {
        val map = mutableListOf<List<Char>>()

        readFile("day14.txt").forEachLine {
            map.add(it.toList())
        }

        return map
    }

}