package indigo
import kotlin.random.Random
class Deck {
    var turn = mutableListOf<Int>()
    init {
        println("Indigo Card Game")
        while(turn.isEmpty()) {
            println("Play first?")
            val cmd = readln().lowercase()
            if (cmd == "no") turn.add(1)
            else if (cmd == "yes") turn.add(0)
        }
    }
    var last = turn.last()
    val suits = mutableListOf("♦", "♥", "♠", "♣")
    val ranks = "A, 2, 3, 4, 5, 6, 7, 8, 9, 10, J, Q, K".split(", ").toMutableList()
    val cards = mutableListOf<String>()
    val score = MutableList(2) { 0 }
    val wonCards = MutableList(2) { mutableListOf<String>() }
    fun reset() {
        cards.clear()
        suits.forEach { s -> ranks.forEach { r -> cards.add("$r$s") } }
    }
    fun divi(it: String) = listOf(it.substring(0, it.lastIndex), it.last().toString())
    fun shuffle() { cards.shuffle() }
    init { reset(); shuffle() }
    val table = getCard(4)
    val players = MutableList(2) { getCard(6) }
    fun show(x: MutableList<String>) { println(x.joinToString(" ")) }
    fun getCard(n: Int): MutableList<String> = MutableList(n) { cards.removeFirst() }
    fun result() { println("Score: Player ${score[0]} - Computer ${score[1]}\nCards: Player ${wonCards[0].size} - Computer ${wonCards[1].size}") }
    fun scoreFixer(list: MutableList<String>, n: Int) { for (i in list) score[n] += if (i.substring(0, i.lastIndex) in listOf("10", "J", "K", "Q", "A")) 1 else 0 }
    fun reger(x: String) = divi(x).map { Regex(if (it in ranks) "$it[♦♥♠♣]" else "[1-9AJKQ10]$it") }.toList()
    fun take() {
        if (table.size > 1) {
            val (a, b) = listOf(divi(table.last()), divi(table[table.lastIndex - 1]))
            if (a.first() == b.first() || a.last() == b.last()) {
                println((if (turn.last() == 0) "Player" else "Computer") + " wins cards")
                last = turn.last()
                wonCards[turn.last()].addAll(table)
                scoreFixer(table, turn.last())
                table.clear()
                result()
            }
        }
    }
    fun aiMove(): Int {
        val my = (if (table.isEmpty()) players[1] else players[1].filter {
            val (a, b) = divi(table.last())
            val (c, d) = divi(it)
            c == a || b == d
        })
        val ok = mutableMapOf<String, List<String>> ()
        (if (my.isEmpty()) players[1] else my).forEach {
            val (a, b) = divi(it)
            ok[a] = (ok[a] ?: listOf<String>()) + b
            ok[b] = (ok[b] ?: listOf<String>()) + a
        }
        for (what in listOf(suits, ranks)) {
            for ((i, j) in ok) {
                if (i in what && j.size > 1) {
                    val x = if (i in suits) j.first() + i else i + j.first()
                    if (x in my || my.isEmpty()) return players[1].indexOf(x)
                }
            }
        }
        return if (my.isNotEmpty()) players[1].indexOf(my.first()) else 0
    }
    fun action() {
        print("Initial cards on the table: ")
        show(table)
        done@while(true) {
            println()
            println(if (table.isEmpty()) "No cards on the table" else "${table.size} cards on the table, and the top card is ${table.last()}")
            if (players[0].isEmpty() && players[1].isEmpty()) {
                if (cards.size >= 12) for (i in 0..1) players[i].addAll(getCard(6))
                else {
                    wonCards[last].addAll(table)
                    scoreFixer(table, last)
                    val winner = if (wonCards[0].size > wonCards[1].size) 0 else if (wonCards[0].size == wonCards[1].size) last else 1
                    score[winner] += 3
                    for (i in 0..1) scoreFixer(players[i], i)
                    result()
                    break
                }
            }
            if (turn.last() == 1) {
                show(players[1])
                table.add(players[1].removeAt(aiMove()))
                println("Computer plays ${table.last()}")
                
            } else {
                print("Cards in hand: ")
                show(MutableList(players[0].size) { "${it + 1})${players[0][it]}" })
                while(true) {
                    println("Choose a card to play (1-${players[0].size}):")
                    val cmd = readln()
                    if (Regex("[1-9]|[1-4][0-9]|5[0-2]").matches(cmd) && cmd.toInt() in 1..players[0].size) {
                        table.add(players[0].removeAt(cmd.toInt() - 1))
                        break
                    } else if (cmd == "exit") break@done
                }
            }
            take()
            turn.add((turn.last() + 1) % 2)
        }
    println("Game Over")
    }
}

fun main() {
    val deck = Deck()
    deck.action()
}
