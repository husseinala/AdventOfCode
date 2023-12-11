object DayTen {

    private val pointMapper = mapOf(
        'S' to { p: Pair<Int, Int> ->
            listOf(
                p.y - 1 to p.x,
                p.y + 1 to p.x,
                p.y to p.x - 1,
                p.y to p.x + 1
            )
        },
        '|' to { p -> listOf(p.y - 1 to p.x, p.y + 1 to p.x) },
        '-' to { p -> listOf(p.y to p.x - 1, p.y to p.x + 1) },
        'L' to { p -> listOf(p.y - 1 to p.x, p.y to p.x + 1) },
        'J' to { p -> listOf(p.y - 1 to p.x, p.y to p.x - 1) },
        '7' to { p -> listOf(p.y + 1 to p.x, p.y to p.x - 1) },
        'F' to { p -> listOf(p.y + 1 to p.x, p.y to p.x + 1) },
        '.' to { emptyList() },
    )

    fun solve() {
        val map = processInput()

        val startPoint = getStartingPoint(map)
        val loopPath = getLoopPath(map, startPoint)

        part1(loopPath)
        part2(map, loopPath)
    }

    private fun part1(loopPath: Set<Pair<Int, Int>>) {
        println("Part 1: ${loopPath.size / 2}")
    }

    private fun part2(map: List<List<Char>>, loopPath: Set<Pair<Int, Int>>) {
        val containedPoints = mutableSetOf<Pair<Int, Int>>()

        for (y in map.indices) {
            for (x in map[0].indices) {
                if (loopPath.contains(y to x)) continue

                var linesCrossed = 0

                for (x1 in x until map[0].size) {
                    val c = map[y][x1]
                    if ((c == 'J' || c == 'L' || c == '|') && loopPath.contains(y to x1)) {
                        linesCrossed++
                    }
                }

                if (linesCrossed % 2 != 0) {
                    containedPoints.add(y to x)
                }
            }
        }

        println("Part 2: ${containedPoints.size}")
    }

    private fun getStartingPoint(map: List<List<Char>>): Pair<Int, Int> {
        var startPoint = 0 to 0
        for (y in map.indices) {
            for (x in map[y].indices) {
                if (map[y][x] == 'S') {
                    startPoint = y to x
                    break
                }
            }
        }

        return startPoint
    }

    private fun getLoopPath(map: List<List<Char>>, startPoint: Pair<Int, Int>): Set<Pair<Int, Int>> {
        val pointsToCheck = mutableListOf(Pair(startPoint, listOf(startPoint)))

        var loopPath = setOf<Pair<Int, Int>>()

        while (pointsToCheck.isNotEmpty()) {
            val (currentPoint, previousPoints) = pointsToCheck.removeFirst()
            val currentPipe = map.get(currentPoint)

            if (currentPipe == 'S' && previousPoints.size > 1) {
                loopPath = previousPoints.toSet()
                break
            }

            pointMapper[currentPipe]!!(currentPoint)
                .filter { point ->
                    previousPoints.last() != point &&
                            point.y in map.indices && point.x in map[0].indices
                            && pointMapper[map.get(point)]!!(point).any { it == currentPoint }
                }.map {
                    Pair(it, previousPoints + currentPoint)
                }.also {
                    pointsToCheck.addAll(it)
                }
        }

        return loopPath
    }

    private fun processInput(): List<List<Char>> {
        val map = mutableListOf<List<Char>>()

        readFile("day10.txt").forEachLine {
            map.add(it.toList())
        }

        return map
    }

    private fun List<List<Char>>.get(point: Pair<Int, Int>): Char = get(point.y)[point.x]

    private val Pair<Int, Int>.y get() = first

    private val Pair<Int, Int>.x get() = second

}