object DayFive {

    fun solve() {
        val seedData = processInput()

        part1(seedData)
        part2(seedData)
    }

    private fun part1(seedData: SeedData) {
        var lowestLocation = Long.MAX_VALUE

        seedData.seeds.forEach { seed ->
            val soil = seedData.seedToSoilMap.findMappedValue(seed)
            val fertilizer = seedData.soilToFertilizerMap.findMappedValue(soil)
            val water = seedData.fertilizerToWaterMap.findMappedValue(fertilizer)
            val light = seedData.waterToLightMap.findMappedValue(water)
            val temp = seedData.lightToTemperatureMap.findMappedValue(light)
            val humidity = seedData.temperatureToHumidityMap.findMappedValue(temp)
            val location = seedData.humidityToLocationMap.findMappedValue(humidity)

            if (lowestLocation > location) {
                lowestLocation = location
            }
        }

        println("Part 1: $lowestLocation")
    }

    private fun part2(seedData: SeedData) {
        val seedRanges = seedData.seeds
            .chunked(2).map { it[0]..(it[0] + it[1]) }

        var location = 0L

        while (true) {
            val humidity = seedData.humidityToLocationMap.findPrevMappingFromNumber(location)
            val temp = seedData.temperatureToHumidityMap.findPrevMappingFromNumber(humidity)
            val light = seedData.lightToTemperatureMap.findPrevMappingFromNumber(temp)
            val water = seedData.waterToLightMap.findPrevMappingFromNumber(light)
            val fertilizer = seedData.fertilizerToWaterMap.findPrevMappingFromNumber(water)
            val soil = seedData.soilToFertilizerMap.findPrevMappingFromNumber(fertilizer)
            val seed = seedData.seedToSoilMap.findPrevMappingFromNumber(soil)
            if (seedRanges.any { it.contains(seed) }) {
                break
            }

            location++
        }

        println("Part 2: $location")
    }

    private fun processInput(): SeedData {
        val seedData = SeedData()

        var currentlyProcessing = ""

        readFile("day5.txt").forEachLine {
            if (it.isBlank()) return@forEachLine

            if (it.startsWith("seeds:")) {
                seedData.seeds.addAll(
                    it.substring(7, it.length).split(" ").map { it.toLong() }
                )
            } else if (it.contains("map")) {
                currentlyProcessing = it
            } else if (currentlyProcessing == "seed-to-soil map:") {
                val numbers = it.split(" ").map { it.toLong() }

                seedData.seedToSoilMap[numbers[0]] = numbers[1]..numbers[1] + numbers[2]
            } else if (currentlyProcessing == "soil-to-fertilizer map:") {
                val numbers = it.split(" ").map { it.toLong() }

                seedData.soilToFertilizerMap[numbers[0]] = numbers[1]..numbers[1] + numbers[2]
            } else if (currentlyProcessing == "fertilizer-to-water map:") {
                val numbers = it.split(" ").map { it.toLong() }

                seedData.fertilizerToWaterMap[numbers[0]] = numbers[1]..numbers[1] + numbers[2]
            } else if (currentlyProcessing == "water-to-light map:") {
                val numbers = it.split(" ").map { it.toLong() }

                seedData.waterToLightMap[numbers[0]] = numbers[1]..numbers[1] + numbers[2]
            } else if (currentlyProcessing == "light-to-temperature map:") {
                val numbers = it.split(" ").map { it.toLong() }

                seedData.lightToTemperatureMap[numbers[0]] = numbers[1]..numbers[1] + numbers[2]
            } else if (currentlyProcessing == "temperature-to-humidity map:") {
                val numbers = it.split(" ").map { it.toLong() }

                seedData.temperatureToHumidityMap[numbers[0]] = numbers[1]..numbers[1] + numbers[2]
            } else if (currentlyProcessing == "humidity-to-location map:") {
                val numbers = it.split(" ").map { it.toLong() }

                seedData.humidityToLocationMap[numbers[0]] = numbers[1]..numbers[1] + numbers[2]
            }

        }

        return seedData
    }

    private fun Map<Long, LongRange>.findMappedValue(num: Long): Long {
        return entries.firstOrNull { it.value.contains(num) }?.let {
            it.key + (num - it.value.first)
        } ?: num
    }

    private fun Map<Long, LongRange>.findPrevMappingFromNumber(num: Long): Long {
        return filter {
            (it.key..(it.key + (it.value.last - it.value.first))).contains(num)
        }.entries.firstOrNull()?.let { (num - it.key) + it.value.first } ?: num
    }

}

private class SeedData(
    val seeds: MutableList<Long> = mutableListOf(),
    val seedToSoilMap: MutableMap<Long, LongRange> = mutableMapOf(),
    val soilToFertilizerMap: MutableMap<Long, LongRange> = mutableMapOf(),
    val fertilizerToWaterMap: MutableMap<Long, LongRange> = mutableMapOf(),
    val waterToLightMap: MutableMap<Long, LongRange> = mutableMapOf(),
    val lightToTemperatureMap: MutableMap<Long, LongRange> = mutableMapOf(),
    val temperatureToHumidityMap: MutableMap<Long, LongRange> = mutableMapOf(),
    val humidityToLocationMap: MutableMap<Long, LongRange> = mutableMapOf(),
)