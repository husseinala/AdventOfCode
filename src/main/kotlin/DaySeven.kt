object DaySeven {

    private const val FIVE_OF_A_KIND = 6
    private const val FOUR_OF_A_KIND = 5
    private const val FULL_HOUSE = 4
    private const val THREE_OF_A_KIND = 3
    private const val TWO_PAIR = 2
    private const val ONE_PAIR = 1
    private const val HIGH_CARD = 0

    fun solve() {
        val numbers = mutableListOf<Pair<String, Int>>()
        readFile("day7.txt").forEachLine {
            val (hand, bids) = it.split(" ")
            numbers.add(hand to bids.toInt())
        }

        part1(numbers)
        part2(numbers)
    }

    private fun part1(numbers: MutableList<Pair<String, Int>>) {
        numbers.sortWith(::part1Comparator)

        val sum = numbers.mapIndexed { index, pair ->
            (index + 1) * pair.second
        }.sum()

        println("Part 1: $sum")
    }

    private fun part2(numbers: MutableList<Pair<String, Int>>) {
        numbers.sortWith(::part2Comparator)

        val sum = numbers.mapIndexed { index, pair ->
            (index + 1) * pair.second
        }.sum()

        println("Part 2: $sum")
    }

    private fun part1Comparator(hand1: Pair<String, Int>, hand2: Pair<String, Int>): Int {
        val cardStrength = setOf('A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2')
        return comparator(hand1.first, hand2.first, cardStrength, ::getPart1HandType)
    }

    private fun part2Comparator(hand1: Pair<String, Int>, hand2: Pair<String, Int>): Int {
        val cardStrength = setOf('A', 'K', 'Q', 'T', '9', '8', '7', '6', '5', '4', '3', '2', 'J')
        return comparator(hand1.first, hand2.first, cardStrength, ::getPart2HandType)
    }

    private fun comparator(hand1: String, hand2: String, cardStrength: Set<Char>, typeProvider: (String) -> Int): Int {
        val hand1Type = typeProvider(hand1)
        val hand2Type = typeProvider(hand2)

        return if (hand1Type == hand2Type) {
            hand1.zip(hand2)
                .firstOrNull { it.first != it.second }
                ?.let { cardStrength.indexOf(it.second) - cardStrength.indexOf(it.first) } ?: 0
        } else {
            hand1Type - hand2Type
        }
    }

    private fun getPart1HandType(hand: String): Int {
        val matches = hand.groupingBy { it }.eachCount().values.sortedDescending()
        return getHandType(
            firstSet = matches[0],
            secondSet = matches.getOrNull(1) ?: 0
        )
    }

    private fun getPart2HandType(hand: String): Int {
        val matches =
            hand.groupingBy { it }.eachCount().entries.sortedByDescending { it.value }.toMutableList()

        val jIndex = matches.indexOfFirst { it.key == 'J' }
        var jCount = 0

        if (jIndex >= 0 && matches.size > 1) {
            jCount = matches[jIndex].value
            matches.removeAt(jIndex)
        }

        return getHandType(
            firstSet = matches[0].value + jCount,
            secondSet = matches.getOrNull(1)?.value ?: 0
        )
    }

    private fun getHandType(firstSet: Int, secondSet: Int): Int = when {
        firstSet == 5 -> FIVE_OF_A_KIND
        firstSet == 4 -> FOUR_OF_A_KIND
        firstSet == 3 && secondSet == 2 -> FULL_HOUSE
        firstSet == 3 -> THREE_OF_A_KIND
        firstSet == 2 && secondSet == 2 -> TWO_PAIR
        firstSet == 2 -> ONE_PAIR
        else -> HIGH_CARD
    }

}