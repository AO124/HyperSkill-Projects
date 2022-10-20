package machine
var water = 400
var milk = 540
var beans = 120
var cups = 9
var cash = 550

fun state() {
    println("""The coffee machine has:
$water ml of water
$milk ml of milk
$beans g of coffee beans
$cups disposable cups
$$cash of money""")
}
fun buy() {
    fun minus(x:Int) {
        val w = listOf(250, 350, 200)
        val m = listOf(0, 75, 100)
        val b = listOf(16, 20, 12)
        val c = listOf(1, 1, 1)
        val ca = listOf(4, 7, 6)
        var can = when(true) {
            w[x] > water -> "water"
            m[x] > milk -> "milk"
            b[x] > beans -> "coffee beans"
            c[x] > cups -> "disposable cups"
            else -> "ok"
        }
        if (can == "ok") {
            print("I have enough resources, making you a coffee!")
            water -= w[x]; milk -= m[x]; beans -= b[x]; cups -= c[x]; cash += ca[x]
        } else {
            print("Sorry, not enough $can!")
        }
    }
    print("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back: ")
    val which = readln()
    when(which) {
        "1", "2", "3" -> minus(which.toInt() - 1)
        "back" -> return
    }
}
fun fill() {
    print("Write how many ml of water do you want to add: ")
    water += readln().toInt()
    print("Write how many ml of milk do you want to add: ")
    milk += readln().toInt()
    print("Write how many grams of coffee beans do you want to add:")
    beans += readln().toInt()
    print("Write how many disposable cups of coffee do you want to add: ")
    cups += readln().toInt()
}
fun take() { print("I gave you $550"); cash = 0 }
fun main() {
    while(true) {
        print("Write action (buy, fill, take, remaining, exit): ")
        val action = readln()
        when(action) {
            "buy" -> buy()
            "fill" -> fill()
            "take" -> take()
            "remaining" -> state()
            "exit" -> break
        }
        println()
    }
}
