package parking
var exist = false
class ParkingLot(n:Int) {
    val space = MutableList(n) { "free" }
    fun cmd(i: String): List<String> = i.split(" ", limit = 2)
    fun park(i: String) {
        val (id, color) = i.split(" ")
        val index = space.indexOf("free")
        println(if (index == -1 ) "Sorry, the parking lot is full." else "$color car parked in spot ${index + 1}.")
        if (index != -1) space[index] = i
    }
    fun leave(i:Int) {
        when(space[i - 1]) {
            "free" -> println("There is no car in spot $i.")
            else -> {
                println("Spot $i is free.")
                space[i - 1] = "free"
            }
        }
    }
    fun status() {
        val ans = space.count { it != "free"}
        for (i in space.indices) if (space[i] != "free") println("${i + 1} ${space[i]}")
        if (ans == 0 ) println("Parking lot is empty.")
    }
    fun reg_by_color(s: String) {
        var c = mutableListOf<String> ()
        for (i in 0 until space.size) if (space[i] != "free") {
            val x = space[i].split(" ")
            if (x[1].toLowerCase() == s.toLowerCase()) c.add(x[0])
        }
        if (c.size == 0) {
            println("No cars with color $s were found.")
        } else {
            println(c.joinToString(", "))
        }
    }
    fun spot_by_color(s: String) {
        var c = mutableListOf<Int> ()
        for (i in 0 until space.size) if (space[i] != "free") {
            if (space[i].split(" ")[1].toLowerCase() == s.toLowerCase()) c.add(i + 1)
        }
        if (c.size == 0) {
            println("No cars with color $s were found.")
        } else println(c.joinToString(", "))
    }
    fun spot_by_reg(s: String) {
        var c = mutableListOf<Int> ()
        for (i in 0 until space.size) if (space[i] != "free") {
            if (space[i].split(" ")[0].toLowerCase() == s.toLowerCase()) c.add(i + 1)
        }
        if (c.size == 0) {
            println("No cars with registration number $s were found.")
        } else println(c.joinToString(", "))
    }
    
        
}
fun creat(): ParkingLot {
    while(true) {
        val x = readln().toLowerCase().split(" ")
        if (x[0] == "exit") return ParkingLot(0)
        if (x.size == 2 && x[0] == "create") {
            println("Created a parking lot with ${x[1]} spots.")
            exist = true
            return ParkingLot(x[1].toInt())
        }
        println("Sorry, a parking lot has not been created.")
    }
    return ParkingLot(0)
}
fun main() {
    var pl = creat()
    while (exist) {
        val x = pl.cmd(readln())
        when(x[0].toLowerCase()) {
            "create" -> {
                pl = ParkingLot(x[1].toInt())
                println("Created a parking lot with ${x[1]} spots.")
            }
            "park" -> pl.park(x[1])
            "leave" -> pl.leave(x[1].toInt())
            "exit" -> break
            "status" -> pl.status()
            "spot_by_reg" -> pl.spot_by_reg(x[1])
            "spot_by_color" -> pl.spot_by_color(x[1])
            "reg_by_color"  -> pl.reg_by_color(x[1])
        }
    }
}
