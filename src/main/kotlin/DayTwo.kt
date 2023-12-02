import java.io.File

object DayTwo {

    fun solve() {
        partTwo(readFile("day2.txt"))
    }

    private fun partTwo(input: File) {
        var sum = 0

        input.forEachLine { line ->
            val (id, gems) = line.split(":")

            val sets = gems.split(";")
                .map { it.split(",") }

            val minCubes = mutableMapOf<String, Int>()

            sets.forEach { set ->
                set.forEach { gem ->
                    val (value, key) = gem.stripLeading().split(" ")

                    if (!(minCubes.containsKey(key) && minCubes[key]!! > value.toInt())) {
                        minCubes[key] = value.toInt()
                    }
                }
            }

            sum += minCubes.values.reduce { acc, i -> acc * i }
            minCubes.clear()
        }

        println(sum)
    }

    private fun partOne(input: File) {
        val maxCubes = mapOf("red" to 12, "green" to 13, "blue" to 14)
        var sum = 0

        input.forEachLine { line ->
            val (id, gems) = line.split(":")
            val gameNumber = id.substring(5, id.length).toInt()

            val sets = gems.split(";")
                .map { it.split(",") }

            var isPossible = true

            mainLoop@ for (set in sets) {
                for (gem in set) {
                    val (value, key) = gem.stripLeading().split(" ")

                    if (maxCubes[key]!! < value.toInt()) {
                        isPossible = false
                        break@mainLoop
                    }
                }
            }

            if (isPossible) sum += gameNumber
        }

        println(sum)
    }

}