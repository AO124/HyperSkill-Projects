package cryptography

import java.awt.Color
import java.io.File
import java.io.IOException
import javax.imageio.ImageIO
val exit =  MutableList(24) { if (it > 21) 1 else 0}
fun toBit(msg: String): MutableList<Int> {
    val ans = MutableList(0) { 0 }
    for (i in msg) {
        val part = MutableList(8) { 0 }
        var num = i.code
        for (i in 7 downTo 0) {
            part[i] = num % 2
            num /= 2
        }
        ans.addAll(part)
    }
    return ans
}
fun toMsg(msg: MutableList<Int>): String {
    var ans = ""
    while(msg.size >= 8) {
        var num = 0.0
        for (i in 7 downTo 0) {
            num += Math.pow(2.0, i.toDouble()) * msg.removeFirst().toDouble()
        }
        ans += num.toInt().toChar()
    }
    return ans
}
fun magic(bits: MutableList<Int>, password: String): MutableList<Int> {
    val pass = toBit(password)
    for(i in bits.indices) {
        bits[i] = bits[i] xor pass[i % pass.size]
    }
    return bits
}
fun hide() {
    println("Input image file:")
    val input = readln()
    val file = File(input)
    val image = ImageIO.read(file)
    println("Output image file:")
    val output = readln()
    println("Message to hide:")
    var msg = toBit(readln())
    println("Password:")
    msg = magic(msg, readln())
    msg.addAll(exit)
    if (msg.size > image.width * image.height) {
        println("The input image is not large enough to hold this message.")
        return
    }
    loo2@for (x in 0 until image.height) {
        for (y in 0 until image.width) {
            val col = Color(image.getRGB(y, x))
            val b = col.blue
            val new = Color(col.red, col.green, col.blue - b % 2 + msg.removeFirst())
            image.setRGB(y, x, new.rgb)
            if (msg.isEmpty()) break@loo2
        }
    }
    println("Message saved in $output image.")
    ImageIO.write(image, "png", File(output))
}
fun show() {
    println("Input image file:")
    val input = readln()
    val file = File(input)
    val image = ImageIO.read(file)
    var ans = MutableList(0) { 0 }
    var n = 0
    loo@for (x in 0 until image.height) {
        for (y in 0 until image.width) {
            ans.add(Color(image.getRGB(y, x)).blue % 2)
            n = if(ans.last() == exit[n]) n + 1 else 0
            if (n == 24) {
                if (ans.size % 8 == 0) break@loo
                else n = 0
            }
        }
    }
    println("Password:")
    ans = magic(ans, readln())
    println("Message:\n${toMsg(ans.subList(0, ans.size - 24))}")
}
fun main() {
    while(true) {
        println("Task (hide, show, exit): ")
        val what = readln()
        when(what) {
            "hide" -> try {
                hide()
            } catch (e: IOException) {
                println("Can't read input file!")
            }
            "show" -> show()
            "exit" -> break
            else -> println("Wrong task: $what")
        }
    }
    println("Bye!")
}
