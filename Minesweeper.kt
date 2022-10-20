package minesweeper
import kotlin.random.Random
val num = mutableListOf(0, 0, 0, 0, 0, 0, 0, 0, 0)//I actually dont need you
fun sum():Int {
    var sum = 0
    for(i in num) sum += i
    return sum
}
var listi = MutableList(9) {MutableList(9) {'.'}}
class MineAround(val row: Int, val col: Int) {
    val cell = listi[row][col]
    var num = finder()
    fun finder():Int {
        var n = 0
        if (cell == 'X') return 0
        for (i in -1..1) {
            for (j in -1..1)
            try {
                if(listi[row + i][col + j] == 'X') n++
            } catch (e: Exception) {}
        }
        return n
    }
    
}
class Cellf(mark: String) {
    val row = (mark[2].toString().toInt() - 1)
    val col = (mark[0].toString().toInt() - 1)
    val cell = listi[row][col]
    var ans = 0
    init {
        if (cell != 'X' || cell != '.' || cell != '*') ans = 0
        if (cell == 'X' ) ans = 1
        if (cell == '.') ans = 2
    }
    }
fun stageprinter(l:MutableList<MutableList<Char>>, sim: Char, emp: Char = '*') {
    println(" │123456789│")
    println("—│—————————│")
    for (i in 0..l.size - 1) {
        println("${i + 1}│${l[i].joinToString().replace(", ","").replace("$sim",".").replace("V", "*")}│")
    }
    println("—│—————————│")
}
fun hinter() {
    for (i in 0..8) {
        for (j in 0..8) {
            val x = MineAround(i,j).num
            val ans = (x + 48).toChar()
            if(x != 0) listi[i][j] = ans
        }
    }
    return
}
fun mineSetter(n:Int) {
    var e = 0
    while (e < n) {
        val n1 = Random.nextInt(0,9)
        val n2 = Random.nextInt(0,9)
        if(listi[n1][n2] == '.') {
            listi[n1][n2] = 'X' 
            num[n1]++
            e++
            continue
            } else continue
    }
   game()
    return
}
fun game() {
    while(sum() > 0) {
        hinter()
        stageprinter(listi, 'X')
        print("Set/delete mines marks (x and y coordinates): > ")
        val mark = readln()
        println()
        val c = Cellf(mark)
        if (c.ans == 0) {println("There is a number here!"); break}
        if (c.ans == 1) {
            listi[c.row][c.col] = '*'
            num[c.row]--
            if (sum() == 0) break
            break
        }
        if (c.ans == 2) listi[c.row][c.col] = 'V'
        stageprinter(listi, 'X')
        break
    }
    stageprinter(listi,'X','.')
    println("Congratulations! You found all the mines!")
    
}
fun main() {
    println("How many mines do you want on the field? ")
    val n =readln().toInt()
    mineSetter(n)

}
