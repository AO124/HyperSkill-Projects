package converter
//format of the s (shortForm, singular, plural, =??? meters)
val m = listOf("m", "meter", "meters", "1.0")
val cm = listOf("cm", "centimeter", "centimeters", "0.01")
val mm = listOf("mm", "millimeter", "millimeters", "0.001")
val km = listOf("km", "kilometer", "kilometers", "1000.0")
val yd = listOf("yd","yard", "yards", "0.9144")
val ft = listOf("ft", "foot", "feet", "0.3048")
val inch = listOf("in", "inch", "inches", "0.0254")
val mi = listOf("mi", "mile", "miles", "1609.35")
val distances = listOf(mm, cm, m, km, yd, ft, inch, mi)

val g = listOf("g", "gram", "grams", "1.0")
val kg = listOf("kg", "kilogram", "kilograms", "1000")
val mg = listOf("mg", "milligram", "milligrams", "0.001")
val lb = listOf("lb", "pound", "pounds", "453.592")
val oz = listOf("oz", "ounce", "ounces", "28.3495")
val weight = listOf(g, kg, mg, lb, oz)

val cel = listOf("c", "degree celsius", "degrees celsius", "celsius", "dc")
val far = listOf("f", "degree fahrenheit", "degrees fahrenheit", "fahrenheit", "df")
val kal = listOf("k", "kelvin", "kelvins")
val temp = listOf(cel, far, kal)
fun conv(amount: Double, from: String, too: String): String {
    var ffr = listOf<String>()
    var fto = listOf<String>()
    var fr = "???"
    var to = "???"
    for (sets in listOf(distances, weight, temp)) {
        var ok2 = 0
        for (unit in sets) {
            if (from in unit) {
                fr = unit[2]
                ffr = unit
                if (amount < 0.0) {
                    if (ffr in distances) return "Length shouldn't be negative"
                    if (ffr in weight) return "Weight shouldn't be negative"
                }
                ok2++
            }
            if (too in unit){
                to = unit[2]
                fto = unit
                ok2++
            }
         }
        if (ok2 == 2) {
            fun temper(): Double {
                val tocel = when(fr) {
                    far[2] -> (amount - 32) * 5 / 9
                    kal[2] -> amount - 273.15
                    else -> amount
                }
                return when(to) {
                    far[2] -> tocel * 9 / 5 + 32
                    kal[2] -> tocel + 273.15
                    else -> tocel
                }
            }
            val ans = if (ffr !in temp) amount * ffr[3].toDouble() / fto[3].toDouble() else temper()
            val ufr = if (amount == 1.0) ffr[1] else ffr[2]
            val uto = if (ans == 1.0) fto[1] else fto[2]
            return "$amount $ufr is $ans $uto".replace("celsius", "Celsius").replace("fahren", "Fahren")
        }
    }
    return "Conversion from $fr to $to is impossible"
}
fun isNumber(lineo: String): Boolean {
    val line = lineo.replaceFirst(".", "")
    if (line.length != 0 && !(line[0] == '-' || line[0].isDigit())) return false
    if (line.length > 1)for (i in line.substring(1, line.lastIndex)) if (!i.isDigit()) return false
    return true
}
fun main() {
    while(true) {
        print("Enter what you want to convert (or exit): ")
        val what = readln().toLowerCase().replace(" degree ", " ").replace(" degrees ", " ").split(" ")
        if (what[0] == "exit") break
        if (what.size == 4 && isNumber(what[0])) {
            println(conv(what[0].toDouble(), what[1], what[3]))
        } else {
            println("Parse error")
        }
    }
}
