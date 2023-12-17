object DayFifteen {

    fun solve() {
        val input = processInput()

        part1(input)
        part2(input)
    }

    private fun part1(input: List<String>) {
        val sum = input.sumOf { it.asciiHash() }

        println("Part 1: $sum")
    }

    private fun part2(input: List<String>) {
        val labelsMap = mutableMapOf<Int, MutableSet<String>>()
        val focal = mutableMapOf<String, Int>()

        input.forEach { s ->
            if (s.contains("=")) {
                val (label, f) = s.split("=")
                val boxId = label.asciiHash()

                focal[label] = f.toInt()
                labelsMap.getOrPut(boxId) { mutableSetOf() }.add(label)
            } else {
                val label = s.dropLast(1)
                val boxId = label.asciiHash()

                labelsMap[boxId]?.remove(label)
            }
        }

        val sum = labelsMap.entries.sumOf {
            val box = it.key + 1

            it.value.indices.sumOf { index ->
                val label = it.value.elementAt(index)
                box * (index + 1) * focal[label]!!
            }
        }

        println("Part 2: $sum")
    }

    private fun processInput(): List<String> {
        return readFile("day15.txt").readText().split(",")
    }

    private fun String.asciiHash() = fold(0) { acc, c -> ((acc + c.code) * 17) % 256 }

}