package flashcards
import java.io.File
val cards = mutableMapOf<String, String>()
val reCards = mutableMapOf<String, String>()
val mistake = mutableMapOf<String, Int>()
val log = mutableListOf<String>()
fun magic(i: String) {
    log.add(i)
    println(i)
}
fun readMagic(): String {
    log.add(readln())
    return log.last()
}
fun ask() {
    magic("How many times to ask?")
    for (i in 1..readMagic().toInt()) {
        val key = cards.keys.shuffled().first()
        val value = cards[key] ?: key
        magic("Print the definition of \"$key\":")
        val k = reCards[readMagic()] ?: ""
        if (k != key) mistake[key] = (mistake[key] ?: 0) + 1
        magic(if (k == key)"Correct!" else "Wrong. The right answer is \"$value\"" + if (k == "") "." else ", but your definition is correct for \"$k\".")
    }
}
fun exp(link: String) {
    val file = File(link)
    val str = mutableListOf<String>()
    cards.forEach { (k, v) -> str.add("$k:$v:${mistake[k] ?: 0}")}
    file.writeText(str.joinToString("\n"))
    magic("${cards.size} cards have been saved.")
}
fun imp(link: String) {
    val file = File(link)
    if (file.exists()) {
        val lines = file.readLines()
        lines.forEach {
            val (k, v, m) = it.split(":")
            reCards.remove(cards[k] ?: "")
            cards.remove(k)
            mistake.remove(k)
            cards[k] = v
            reCards[v] = k
            mistake[k] = m.toInt()
        }
        magic("${lines.size} cards have been loaded.")
    } else magic("File not found.")
}
fun remove() {
    magic("Which card?")
    val key = readMagic()
    val value = cards[key]
    if (value != null) {
        cards.remove(key)
        reCards.remove(value)
        magic("The card has been removed.")
    } else magic("Can't remove \"$key\": there is no such card.")
}
fun add() {
    magic("The card:")
    val card = readMagic()
    if (card in cards.keys) {
        magic("The card \"$card\" already exists.")
        return
    }
    magic("The definition of the card:")
    val def = readMagic()
    if (def in cards.values) {
        magic("The definition \"$def\" already exists.")
        return
    }
    magic("The pair (\"$card\":\"$def\") has been added.")
    cards[card] = def
    reCards[def] = card
}
fun log() {
    magic("File name:")
    File(readMagic()).writeText(log.joinToString("\n"))
    magic("The log has been saved.")
}
fun hardestCard() {
    if (mistake.isEmpty()){
        magic("There are no cards with errors.")
        return
    }
    var hMistake = 0
    for ((_, v) in mistake) if (v > hMistake) hMistake = v
    val wrongs = mistake.filter { it.value == hMistake }
    val (verb, extra) = if (wrongs.size == 1) "card is" to "it" else "cards are" to "them"
    magic("The hardest $verb ${wrongs.keys.joinToString("\", \"", "\"", "\"")}.  You have ${wrongs.values.sum()} errors answering $extra")
}
fun main(args: Array<String>) {
    val im = args.indexOf("-import").let { if (it == -1) "" else args[it + 1]}
    val ex = args.indexOf("-export").let { if (it == -1) "" else args[it + 1]}
    if (im != "") imp(im)
    while (true) {
        magic("Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):")
        when (readMagic()) {
            "exit" -> break
            "add" -> add()
            "remove" -> remove()
            "import" -> {
                magic("File name:")
                imp(readMagic())
            }
            "export" -> {
                magic("File name:")
                exp(readMagic())
            }
            "ask" -> ask()
            "log"-> log()
            "hardest card" -> hardestCard()
            "reset stats" -> {
                magic("Card statistics have been reset.")
                mistake.clear()
            }
        }
    }
    if (ex != "") exp(ex)
    magic("Bye bye!")
}
