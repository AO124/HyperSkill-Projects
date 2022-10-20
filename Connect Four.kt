package connectfour
class CFour (val p1: String, val p2: String, val rule: Int = 4, val dim: List<Int>) {
    object score {
        var p1win = 0
        var p2win = 0
        var turn = 0
            get() {
                field %= 2
                return field
            }
    }
    val board = MutableList(dim[0]) { MutableList(dim[1]) {' '} }
    var go = true
    fun show() {
        println(List(dim[1]) { it + 1 }.joinToString(prefix = " ", separator = " "))
        for (i in board) println(i.joinToString(prefix = "║", separator = "║", postfix = "║"))
        println(List(dim[1]) { "═" }.joinToString(prefix = "╚", separator = "╩", postfix = "╝"))
    }
    fun check(who: String, what: String, cell: List<Int>) {
        var notDraw = false
        if (board.joinToString("").replace(", ", "").contains(" ")) notDraw = true
        var line1 = "|"
        var line2 = "|"
        var line3 = "|"
        var line4 = "|"
        for (n in -rule..rule) {
            if (cell[0] + n in 0 until dim[0]) line1 += board[cell[0] + n][cell[1]]
            if (cell[1] + n in 0 until dim[1]) line2 += board[cell[0]][cell[1] + n]
            if (cell[0] + n in 0 until dim[0] && cell[1] + n in 0 until dim[1]) line3 += board[cell[0] + n][cell[1] + n]
            if (cell[0] - n in 0 until dim[0] && cell[1] + n in 0 until dim[1]) line4 += board[cell[0] - n][cell[1] + n]
        }
        if (what.repeat(rule) in line1 + line2 + line3 + line4) {
            if (who == p1) score.p1win += 2 else score.p2win += 2
            println("Player $who won\nScore\n$p1: ${score.p1win} $p2: ${score.p2win}")
            go = false
            return
        }
        if (!notDraw) {
            score.p1win++
            score.p2win++
            println("It is a drawScore\n" +
                    "$p1: ${score.p1win} $p2: ${score.p2win}")
        }
        go = notDraw
    }
    fun start() {
        val who = if (score.turn == 0) p1 else p2
        val what = if (who == p1) 'o' else '*'
        var done = true
        var lastMove = emptyList<Int>()
        while(done) {
            println("$who's turn:")
            val input = readln()
            if (input == "end") {
                go = false
                return
            }
            if (!input.matches(Regex("\\d*"))) {
                println("Incorrect column number")
            } else if (!input.matches(Regex("[1-${dim[1]}]"))) {
                println("The column number is out of range (1 - ${dim[1]})")
            } else {
                for (i in dim[0] - 1 downTo 0) {
                    lastMove = listOf(i, input.toInt() - 1)
                    if (board[lastMove[0]][lastMove[1]] == ' ') {
                        board[lastMove[0]][lastMove[1]] = what
                        done = false
                        break
                    }
                }
                if (done) println("Column $input is full")
            }
        }
        score.turn++
        show()
        check(who, what.toString(), lastMove)
    }
}

fun main() {
    println("Connect Four")
    println("First player's name:")
    val p1 = readln()
    println("Second player's name:")
    val p2 = readln()
    fun howManyGame(): Int {
        while (true) {
            println("Do you want to play single or multiple games?\n" +
                    "For a single game, input 1 or press Enter\nInput a number of games:")
            val n = readln()
            if (n == "1" || n == "") return 1
            if (!n.matches(Regex("0|\\D"))) return n.toInt() else println("Invalid input")
        }
    }
    fun giveDim(): List<Int> {
        while(true) {
            println("Set the board dimensions (Rows x Columns)\nPress Enter for default (6 x 7)")
            val cmd = readln().lowercase().replace(Regex("\\s"), "")
            if (cmd == "") {
                return listOf(6, 7)
            } else if (cmd.matches(Regex("^[1-9]\\d*x[1-9]\\d*$"))) {
                if (!cmd.matches(Regex("[5-9]x.*"))) {
                    println("Board rows should be from 5 to 9")
                } else if (!cmd.matches(Regex(".*x[5-9]"))) {
                    println("Board columns should be from 5 to 9")
                } else {
                    return cmd.split("x").map { it.toInt() }
                }
            } else println("Invalid input")
        }
    }
    val dimen = giveDim()
    val nGame = howManyGame()
    println("$p1 VS $p2\n" +
            "${dimen[0]} X ${dimen[1]} board\n" +
            if (nGame == 1) "" else "Total $nGame games")

    repeat(nGame) {
        println(if (nGame == 1) "Single game" else "Game #${it + 1}")
        val game = CFour(p1, p2, dim = dimen)
        game.show()
        while(game.go) {
            game.start()
        }
    }
    println("Game over!")
}
