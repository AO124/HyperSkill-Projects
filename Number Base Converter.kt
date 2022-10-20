package converter
import java.math.BigDecimal
fun main() {
    while(true) {
        print("Enter two numbers in format: {source base} {target base} (To quit type /exit) ")
        val what = readln()
        if (what == "/exit") break
        val (fromB, toB) = what.split(" ")
        while (true) {
            print("Enter number in base $fromB to convert to base $toB (To go back type /back) ")
            val num = readln()
            if (num == "/back") break
            println("Conversion result: " + answer(num, fromB.toBigDecimal(), toB.toBigDecimal()))
        }
    }
}
fun fromDec(_num: String, base: BigDecimal, isDec: Boolean = false): String {
    var num = (_num.toBigDecimal() * if (isDec) Math.pow(base.toDouble(), 5.0).toBigDecimal() else BigDecimal.ONE).toBigInteger().toBigDecimal()
    var ans = ""
    while(num != BigDecimal.ZERO) {
        val add = (num % base).toInt()
        ans += if (add <= 9) (add + 48).toChar() else (add + 87).toChar()
        num = (num - add.toString().toBigDecimal()) / base
    }
    while(ans.length != 5 && isDec) ans += "0"
    return ans.reversed()
}
fun toDec(_num: String, base: BigDecimal, isDec: Boolean = false): String {
    var num = if (isDec) "0" + _num else _num.reversed()
    var ans = BigDecimal.ZERO
    var power = BigDecimal.ONE
    while(num != "") {
        val add = num[0].toInt()
        ans += (if (add in 48..57) add - 48 else add - 87).toBigDecimal() * power
        power *= if (!isDec) base else (1.0 / base.toDouble()).toBigDecimal()
        num = num.substring(1)
    }
    return ans.toString()
}
fun answer(num: String, baseF: BigDecimal, baseT: BigDecimal): String {
    val parts = num.split('.')
    return when(parts.size) {
        2 -> fromDec(toDec(parts[0], baseF), baseT) + "." + fromDec(toDec(parts[1], baseF, true), baseT, true)
        else -> fromDec(toDec(num, baseF), baseT)
    }
}
