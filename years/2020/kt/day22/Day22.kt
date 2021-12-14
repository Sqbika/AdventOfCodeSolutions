package day22

import Solution
import java.math.BigInteger

class Day22(path:String) : Solution(path) {
    override fun part1() {
        val start = System.currentTimeMillis()
        println("Input1: ${solvePart1(input)}")
        println("Input1 took: ${System.currentTimeMillis() - start} ms")
    }

    override fun part2() {
        val start = System.currentTimeMillis()
        println("Input2: ${solvePart2(input)}")
        println("Input2 took: ${System.currentTimeMillis() - start} ms")
    }

    override fun test() {
        val start = System.currentTimeMillis()
        println("Test1: ${solvePart1(test)}")
        println("Test1 took: ${System.currentTimeMillis() - start} ms")
        val start2 = System.currentTimeMillis()
        println("Test2: ${solvePart2(test)}")
        println("Test2 took: ${System.currentTimeMillis() - start2} ms")
    }

    private fun inputToDecks(input:List<String>) : List<PlayerDeck> = input.joinToString("\n").split("\n\n").map { PlayerDeck(it.split("\n"))}

    private fun solvePart1(input:List<String>) : BigInteger {
        val (plr1, plr2) = inputToDecks(input)

        while (!plr1.isEmptyDeck() && !plr2.isEmptyDeck()) {
            val winner = if (plr1.compareTopdeck(plr2) == 1 ) plr1 else plr2
            val looser = if (winner == plr1) plr2 else plr1
            winner.addCardsToBottom(winner.topdeck, looser.topdeck)
            winner.removeTopdeck()
            looser.removeTopdeck()
        }

        return (if (plr1.isEmptyDeck()) plr2 else plr1).deckProd()
    }

    private fun solvePart2(input:List<String>) : BigInteger {
        val (plr1, plr2) = inputToDecks(input)

        val result = Combat(plr1, plr2).playLoop()

        return result.first.deckProd()
    }
}

class PlayerDeck(
        val input: List<String>
) {
    private var id: Int = input[0].replace(Regex("Player |:"), "").toInt()
    private var cards:List<Int> = input.subList(1, input.size).map {it.toInt()}

    constructor(subDeck: PlayerDeck, deckStart: Int = 1, deckEnd: Int = subDeck.cards.size) : this(listOf("-1", "-1")) {
        this.id = subDeck.id
        this.cards = subDeck.cards.subList(deckStart, deckEnd)
    }

    fun addCardsToBottom(vararg number:Int) {
        cards = cards + number.toList()
    }

    fun removeTopdeck() {
        cards = cards.subList(1, cards.size)
    }

    val topdeck: Int
        get() = cards[0]

    val decksize: Int
        get() = cards.size

    fun getCards() = cards

    fun getId() = id

    fun isEmptyDeck() = cards.isEmpty()

    fun compareTopdeck(player: PlayerDeck) = topdeck.compareTo(player.topdeck)

    fun deckProd():BigInteger = cards.reversed().mapIndexed {idx, i -> i.toBigInteger() * (idx + 1).toBigInteger()}.sumOf { it }

    fun subCopy(deckStart: Int = 1, deckEnd: Int = cards.size) = PlayerDeck(this, deckStart, deckEnd)
}

class Combat (
        val player1: PlayerDeck,
        val player2: PlayerDeck,
) {

    private val plr1 = player1
    private val plr2 = player2

    private val cardHistory: MutableList<Pair<List<Int>, List<Int>>> = mutableListOf()

     fun playLoop():Pair<PlayerDeck, PlayerDeck> {
         while (!plr1.isEmptyDeck() && !plr2.isEmptyDeck()) {
             if (seenState()) {
                return Pair(plr1, plr2)
             }
             cardHistory.add(Pair(plr1.getCards(), plr2.getCards()))
             val plr1TD = plr1.topdeck
             val plr2TD = plr2.topdeck
             if (plr1TD < plr1.decksize && plr2TD < plr2.decksize) {
                 val subResult = Combat(plr1.subCopy(1, plr1TD + 1), plr2.subCopy(1, plr2TD + 1)).playLoop()
                 val winner = if (subResult.first.getId() == plr1.getId() ) plr1 else plr2
                 val looser = if (winner == plr1) plr2 else plr1
                 winner.addCardsToBottom(winner.topdeck, looser.topdeck)
                 winner.removeTopdeck()
                 looser.removeTopdeck()
             } else {
                 val winner = if (plr1TD.compareTo(plr2TD) == 1 ) plr1 else plr2
                 val looser = if (winner == plr1) plr2 else plr1
                 winner.addCardsToBottom(winner.topdeck, looser.topdeck)
                 winner.removeTopdeck()
                 looser.removeTopdeck()
             }
         }

         val winner = if (plr2.isEmptyDeck()) plr1 else plr2
         val looser = if (winner == plr1) plr2 else plr1

         return Pair(winner, looser)
     }

    private fun seenState() = cardHistory.contains(Pair(plr1.getCards(), plr2.getCards()))
}


