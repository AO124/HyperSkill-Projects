package cinema
var list:MutableList<MutableList<String>> = mutableListOf()
var perchasedTicket = 0
var currentIncome = 0

fun state(row: Int, seats: Int, total: Int) {
    val totalIncome = if (total <= 60) total * 10 else (row / 2 * 10 + (row - (row / 2)) * 8) * seats
    val percentage = 100.0 * perchasedTicket / total
    val formatPercentage = "%.2f".format(percentage)
    println("Number of purchased tickets: $perchasedTicket")
    println("Percentage: $formatPercentage%")
    println("Current income: $$currentIncome")
    println("Total income: $$totalIncome")
}

fun seats(row:Int, seats:Int) {
    println("Cinema:")
    print(" ")
    for(i in 1..seats) print(" $i")
    for(i in 1..row) print("\n$i ${list[i - 1].joinToString(" ")}")
    println()
}

fun buy(row: Int, seats: Int, total: Int) {
    var done = 0
    while(done == 0) {
        println("Enter a row number:")
        val rNum = readln().toInt()
        println("Enter a seat number in that row:")
        val sNum = readln().toInt()
        if (rNum > row || sNum > seats) {
            println("Wrong input!")
        } else if (list[rNum - 1][sNum - 1] == "S") {
            val cost = if ( total <= 60) 10 else if (rNum > row / 2) 8 else 10
            done = 1
            list[rNum - 1][sNum - 1] = "B"
            perchasedTicket++
            currentIncome += cost
            println("Ticket price: $$cost")
        } else {
            println("That ticket has already been purchased!")
        }
    }
}

fun main() {
    var key = 0
    println("Enter the number of rows:")
    val row = readln().toInt()
    println("Enter the number of seats in each row:")
    val seatsPerRow = readln().toInt()
    list = MutableList(row) { MutableList(seatsPerRow) {"S"}}
    val total = row * seatsPerRow
    while(key == 0) {
        println("1. Show the seats\n2. Buy a ticket\n3. Statistics\n0. Exit")
        val input = readln()
        when(input) {
            "1" -> seats(row, seatsPerRow)
            "2" -> buy(row, seatsPerRow, total)
            "3" -> state(row, seatsPerRow, total)
            "0" -> key = 1
        }
    }
}
