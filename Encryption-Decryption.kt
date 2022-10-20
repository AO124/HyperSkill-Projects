package tictactoe
import java.io.File
fun String.unicode(key: Int): String = this.map { (it.code + key).toChar()}.joinToString("")
fun String.shift(key: Int): String = this.map { if (it.isLetter()) ((it.code + key - if (it.isLowerCase()) 'a'.code else 'A'.code) % 26 + if ((it.code + key - if (it.isLowerCase()) 'a'.code else 'A'.code) % 26 < 0) 1 + if (it.isLowerCase()) 'z'.code else 'Z'.code else if (it.isLowerCase()) 'a'.code else 'A'.code).toChar() else it
}.joinToString("")
fun Array<String>.input(cmd: String, default: String): String = this.indexOf(cmd).let { if ( it > -1) (if (1 + it < this.size && this[it + 1][0] != '-') this[it + 1] else throw java.lang.Exception("Error and error")) else default}
fun main(args: Array<String>) {
        try {
                val enc = args.input("-mode", "enc")
                val str = args.input("-data", "").let { if (it == "" && "-in" in args) File(args.input("-in", "")).readText() else it }
                val key = args.input("-key", "0").toInt() * if (enc == "enc") 1 else -1
                val out = args.input("-out", "")
                val ans = if (args.input("-alg", "shift") == "shift") str.shift(key) else str.unicode(key)
                if (out != "") File(out).writeText(ans) else println(ans)
        } catch (e: Exception) {
                println("Error And error")
        }
}
