object DayNine {

    fun solve() {
        part1()
        part2()
    }

    private fun part1() {
        var sum = 0
        processInput { nums ->
            val numsToCombine = mutableListOf(nums.last())
            var currNums = nums

            while (true) {
                currNums = currNums.zipWithNext { a, b -> b - a }
                numsToCombine.add(currNums.last())

                if (currNums.all { it == 0 }) break
            }

            sum += numsToCombine.sum()
        }

        println("Part 1: $sum")
    }

    private fun part2() {
        var sum = 0
        processInput { nums ->
            val numsToCombine = mutableListOf(nums.first())
            var currNums = nums

            while (true) {
                currNums = currNums.zipWithNext { a, b -> b - a }
                numsToCombine.add(currNums.first())

                if (currNums.all { it == 0 }) break
            }

            sum += numsToCombine.reversed().reduce { acc, i -> i - acc }
        }

        println("Part 2: $sum")
    }

    private fun processInput(action: (List<Int>) -> Unit) {
        readFile("day9.txt").forEachLine { line ->
            action(line.split(" ").map { it.toInt() })
        }
    }
}