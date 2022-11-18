package wordsvirtuoso
import java.io.File
fun isInvalid(word: String): Boolean {
    for (i in word.uppercase()) if (i !in 'A'..'Z') return true
    return false
}
fun hasDup(word:String): Boolean {
    val magic = word.toMutableList().sorted()
    for (i in 0 until magic.lastIndex) if (magic[i] == magic[i + 1]) return true
    return false
}
fun invalids(file: File): MutableList<String> {
    val invalidWords = mutableListOf<String>()
    file.readLines().forEach {
        if (isInvalid(it) || it.length != 5 || hasDup(it)) invalidWords.add(it)
    }
    return invalidWords
}
fun game(candy: List<String>, words: List<String>) {
    val ans = candy.shuffled().first()
    val start = System.currentTimeMillis()
    var turn = 0
    val hints = mutableListOf<String> ()
    val wrongs = mutableListOf<Char> ()
    while (true) {
        println("Input a 5-letter word:")
        turn++
        val word = readln().uppercase()
        if (word in listOf("EXIT", ans)) {
            if (word == "EXIT") println("The game is over.")
            else {
                val time = (System.currentTimeMillis() - start)
                println(hints.joinToString("\n"))
                word.forEach { print("\u001B[48:5:10m$it\u001B[0m") }
                println("\nCorrect!\n" + if (turn == 1) "Amazing luck! The solution was found at once." else "The solution was found after $turn tries in $time seconds.")
            }
            return
        }
        if (word.length != 5) println("The input isn't a 5-letter word.")
        else if (isInvalid(word)) println("One or more letters of the input aren't valid.")
        else if (hasDup(word)) println("The input has duplicated letters.")
        else if (word !in words) println("The input word isn't included in my words list.")
        else {
            hints.add(word.uppercase().mapIndexed { i, x ->
                val col = if (x !in ans) "\u001B[48:5:7m$x\u001B[0m" else if (x == ans[i]) "\u001B[48:5:10m$x\u001B[0m" else 	"\u001B[48:5:11m$x\u001B[0m"
                if (x !in ans && x !in wrongs) wrongs.add(x)
                col
            }.joinToString(""))
            println(hints.joinToString("\n"))
            println("\u001B[48:5:14m${wrongs.sorted().joinToString("")}\u001B[0m")
        }
    }
}
fun main(args: Array<String>) {
    if (args.size != 2) {
        println("Error: Wrong number of arguments.")
        return
    }
    val wordFile = File(args[0])
    val candidateFile = File(args[1])
    if (wordFile.exists()) {
        if (candidateFile.exists()) {
            val invalidWord = invalids(wordFile)
            val invalidCandidates = invalids(candidateFile)
            if (invalidWord.isEmpty()) {
                if (invalidCandidates.isEmpty()) {
                    val words = wordFile.readLines().map { it.uppercase() }
                    var notInside = 0
                    val candies = candidateFile.readLines().map { it.uppercase() }
                    candies.forEach {
                        if (it !in words) notInside++
                    }
                    if (notInside == 0) {
                        println("Words Virtuoso")
                        game(candies, words)
                    } else println("Error: $notInside candidate words are not included in the ${args[0]} file.")
                } else println("Error: ${invalidCandidates.size} invalid words were found in the ${args[1]} file.")
            } else println("Error: ${invalidWord.size} invalid words were found in the ${args[0]} file.")
        } else println("Error: The candidate words file ${args[1]} doesn't exist.")
    } else println("Error: The words file ${args[0]} doesn't exist.")
}
