object DayOne {
    private val digitNumbers = mapOf(
        "one" to "1",
        "two" to "2",
        "three" to "3",
        "four" to "4",
        "five" to "5",
        "six" to "6",
        "seven" to "7",
        "eight" to "8",
        "nine" to "9"
    )

    fun solve() {
        var sum = 0

        readFile("day1.txt").forEachLine {
           sum += getDigits(it)
        }

        println(sum)
    }

    private fun getDigits(line: String): Int {
        var firstDigit: String? = null
        var lastDigit: String? = null

        line.forEachIndexed { index, c ->
            var num: String? = null

            if (c.isDigit()) {
                num = c.toString()
            } else if ((line.length - index) >= 3 && digitNumbers.containsKey(line.substring(index, index + 3))) {
                num = digitNumbers[line.substring(index, index + 3)]
            } else if ((line.length - index) >= 4 && digitNumbers.containsKey(line.substring(index, index + 4))) {
                num = digitNumbers[line.substring(index, index + 4)]
            } else if ((line.length - index) >= 5 && digitNumbers.containsKey(line.substring(index, index + 5))) {
                num = digitNumbers[line.substring(index, index + 5)]
            }

            if (num != null) {
                if (firstDigit == null) {
                    firstDigit = num.toString()
                }

                lastDigit = num.toString()
            }
        }

        return (firstDigit!! + lastDigit!!).toInt()
    }
}