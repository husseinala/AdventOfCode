object DayThree {

    fun solve() {
        val symbolMap = mutableSetOf<String>()
        val numbers = mutableListOf<NumberPosition>()

        val gears = mutableMapOf<String, List<Int>>()

        var y = 0

        readFile("day3.txt").forEachLine { line ->
            var num = ""
            var x = 0

            line.forEach {
                if (it.isDigit()) {
                    num += it.toString()
                } else {
                    if (num.isNotEmpty()) {
                        numbers.add(NumberPosition(num, y, x - num.length, x - 1))
                        num = ""
                    }

                    if (it != '.') {
                        symbolMap.add("$y:$x")

                        if (it == '*') {
                            gears["$y:$x"] = emptyList()
                        }
                    }
                }

                x++
            }

            if (num.isNotEmpty()) {
                numbers.add(NumberPosition(num, y, x - num.length, x - 1))
                num = ""
            }

            y++
        }

        partOneSum(numbers, symbolMap)
        partTwoSum(numbers, gears)
    }

}

private fun partOneSum(numbers: List<NumberPosition>, symbolMap: Set<String>) {
    var sum = 0

    for (number in numbers) {
        if (symbolMap.contains("${number.y}:${number.startX - 1}")
            || symbolMap.contains("${number.y}:${number.endX + 1}")
            || (number.startX - 1..number.endX + 1).any { x ->
                symbolMap.contains("${number.y - 1}:$x") || symbolMap.contains(
                    "${number.y + 1}:$x"
                )
            }
        ) {
            sum += number.number.toInt()
        }
    }

    println("Part 1: $sum")
}

private fun partTwoSum(numbers: List<NumberPosition>, gears: MutableMap<String, List<Int>>) {
    var sum = 0

    for (number in numbers) {
        for (x in number.startX - 1..number.endX + 1) {
            for (y in number.y - 1..number.y + 1) {
                if (gears.containsKey("$y:$x")) {
                    gears["$y:$x"] = gears["$y:$x"]!! + number.number.toInt()
                }
            }
        }
    }

    gears.filter { it.value.size == 2 }.forEach {
        sum += it.value.reduce { acc, i -> acc * i }
    }

    println("Part 2: $sum")
}

private data class NumberPosition(
    val number: String,
    val y: Int,
    val startX: Int,
    val endX: Int,
)