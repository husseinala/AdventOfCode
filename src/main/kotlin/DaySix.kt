object DaySix {

    fun solve() {
        val data = processInput()

        part1(data)
        part2(data)
    }

    private fun part1(data: List<Pair<Int, Int>>) {
        var sum = 1

        data.forEach {
            val (time, distance) = it

            val lowerBand = (1 until time).first { s -> (time - s) * s > distance }
            val upperBand = time - lowerBand

            sum *= ((upperBand - lowerBand) + 1)
        }

        println("Part 1: $sum")
    }

    private fun part2(data: List<Pair<Int, Int>>) {
        val (time, distance) = data.fold("" to "") { a, v -> a.first + v.first to a.second + v.second }.let {
            it.first.toLong() to it.second.toLong()
        }

        val lowerBand = (1 until time).first { s -> (time - s) * s > distance }
        val upperBand = time - lowerBand

        val pos = ((upperBand - lowerBand) + 1)

        println("Part 2: $pos")
    }

    private fun processInput(): List<Pair<Int, Int>> {
        var times = emptyList<Int>()
        var distances = emptyList<Int>()

        readFile("day6.txt").forEachLine {
            if (it.startsWith("Time")) {
                times = it.substring(5, it.length).trim().split(" ").filter { it.isNotBlank() }.map { it.toInt() }
            } else if (it.startsWith("Distance")) {
                distances = it.substring(9, it.length).trim().split(" ").filter { it.isNotBlank() }
                    .map { it.toInt() }
            }
        }

        return times.zip(distances)
    }

}