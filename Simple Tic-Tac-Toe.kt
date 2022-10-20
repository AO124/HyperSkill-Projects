package tictactoe
var turn = 0
fun printer(list: MutableList<MutableList<Char>>) {
    println("---------")
    for (i in list) {
        println(i.joinToString(prefix = "| ", postfix = " |", separator = " "))
    }
    println("---------")
}
fun move(list: MutableList<MutableList<Char>>): MutableList<MutableList<Char>> {
    while(true) {
        val xy = readln().split(" ")
        if (xy.size != 2 || xy[0].length != 1 || xy[1].length != 1) println("You should enter numbers!")
        else if (xy[0][0].isDigit() && xy[1][0].isDigit()) {
            if (xy[0].toInt() !in 1..3 || xy[1].toInt() !in 1..3) {
                println("Coordinates should be from 1 to 3!")
            } else {
                val x = xy[0].toInt() - 1
                val y = xy[1].toInt() - 1
                if (list[x][y] == '_') {
                    list[x][y] = if (turn % 2 == 0) 'X' else 'O'
                    turn += 1
                    return list
                } else {
                    print("This cell is occupied! Choose another one!")
                }
            }
        } else {
            println("You should enter numbers!")
        }
    }
    return list
}
fun result(list: MutableList<MutableList<Char>>): Boolean {
    var oc = 0
    var xc = 0
    for (i in list) {
        for (j in i) {
            oc += if (j == 'O') 1 else 0
            xc += if (j == 'X') 1 else 0
        }
    }
    //if (oc + 1 < xc || xc + 1 < oc) return "Impossible"
    var win = ""
    for (who in "XO") {
        for (i in 0..2) {
            if (list[i][0] == list[i][1] && list[i][1] == list[i][2] && list[i][2] == who) win += who
            if (list[0][i] == list[1][i] && list[1][i] == list[2][i] && list[2][i] == who) win += who
        }
        if (list[0][2] == list[1][1] && list[1][1] == list[2][0] && list[2][0] == who) win += who
        if (list[0][0] == list[1][1] && list[1][1] == list[2][2] && list[2][2] == who) win += who
    }
    if (win.length == 1) {
        println("$win wins")
        return false
    }
    if (oc + xc == 9) {
        println("Draw")
        return false
    }
    return true
}
fun listmaker(l: MutableList<Char>) = mutableListOf(l.subList(0, 3), l.subList(3, 6), l.subList(6, 9))
fun main() {
    var game = listmaker("_________".toMutableList())
    printer(game)
    while(result(game)) {
        game = move(game)
        printer(game)
    }
    
}
