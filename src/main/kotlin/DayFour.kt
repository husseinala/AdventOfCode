import java.io.File

object DayFour {

    fun solve() {
        partOne(readFile("day4.txt"))
        partTwo(readFile("day4.txt"))
    }

    private fun partTwo(input: File) {
        val numberOfCards = mutableMapOf<Int, Int>()

        processCard(input) { id, winningNumbers, cardNumbers ->
            val winIncrement = numberOfCards.putIfAbsent(id, 1) ?: 1

            var winningNumber = 0
            cardNumbers.forEach {
                if (it.isNotBlank() && winningNumbers.contains(it)) {
                    winningNumber++

                    numberOfCards[winningNumber + id] = (numberOfCards[winningNumber + id] ?: 1) + winIncrement
                }
            }
        }

        println("Part 1: ${numberOfCards.values.sum()}")
    }

    private fun partOne(input: File) {
        var sum = 0

        processCard(input) { _, winningNumbers, cardNumbers ->
            var cardScore = 0

            cardNumbers.forEach {
                if (it.isNotBlank() && winningNumbers.contains(it)) {
                    if (cardScore == 0) {
                        cardScore = 1
                    } else {
                        cardScore *= 2
                    }
                }
            }

            sum += cardScore
        }

        println("Part 2: $sum")
    }

    private fun processCard(
        input: File,
        action: (cardId: Int, winningNumbers: Set<String>, cardNumbers: List<String>) -> Unit
    ) {
        input.forEachLine { line ->
            val (id, numbers) = line.split(":")
            val cardId = id.substring(4, id.length).trim().toInt()
            val (winningNumbers, cardNumbers) = numbers.split("|")

            val winningNumbersDic = winningNumbers.trim().split(" ").toSet()

            action(cardId, winningNumbersDic, cardNumbers.trim().split(" "))
        }
    }

}