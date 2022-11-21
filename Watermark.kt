package watermark
import java.awt.image.BufferedImage
import java.awt.Color
import java.io.File
import javax.imageio.ImageIO
fun getImageInfo(file: File, print: Boolean = false): List<Any> {
    val image = ImageIO.read(file)
    val info = listOf(file.path, image.width, image.height, image.colorModel.numComponents, image.colorModel.numComponents, image.colorModel.pixelSize, listOf("OPAQUE", "BITMASK", "TRANSLUCENT")[image.transparency - 1])
    if (print) println("""
        Image file: ${info[0]}
        Width: ${info[1]}
        Height: ${info[2]}
        Number of components: ${info[3]}
        Number of color components: ${info[4]}
        Bits per pixel: ${info[5]}
        Transparency: ${info[6]}
        """.trimIndent()
    )
    return info
}
fun fileCheck(address: String, what: String = "image"): List<Any> {
    val file = File(address)
    if (file.exists()) {
        val info = getImageInfo(file)
        if (info[4].toString().toInt() >= 3) {
            if (info[5].toString().toInt() in listOf(24, 32)) return info
            else println("The $what isn't 24 or 32-bit.")
        } else println("The number of $what color components isn't 3.")
    } else println("The file $address doesn't exist.")
    return listOf()
}
fun main() {
    println("Input the image filename:")
    val iInfo = fileCheck(readln())
    if (iInfo.isEmpty()) return
    println("Input the watermark image filename:")
    val wInfo = fileCheck(readln(), "watermark")
    if (wInfo.isEmpty()) return
    if (iInfo[1].toString().toInt() < wInfo[1].toString().toInt() || iInfo[2].toString().toInt() < wInfo[2].toString()
            .toInt()
    ) {
        println("The watermark's dimensions are larger.")
        return
    }
    var waterIsClean = false
    var transColor: Color? = null
    if (wInfo[6].toString() == "TRANSLUCENT") {
        println("Do you want to use the watermark's Alpha channel?")
        waterIsClean = readln().lowercase() == "yes"
    } else {
        println("Do you want to set a transparency color?")
        if (readln().lowercase() == "yes") {
            println("Input a transparency color ([Red] [Green] [Blue]):")
            val tcolor = readln().split(" ")
            if (tcolor.size != 3 || tcolor.any { !Regex("[0-9]+").matches(it) || it.toInt() !in 0..255 }) {
                println("The transparency color input is invalid.")
                return
            }
            val (r, g, b) = tcolor.map { it.toInt() }
            transColor = Color(r, g, b)
        }
    }
    val image = ImageIO.read(File(iInfo[0].toString()))
    val water = ImageIO.read(File(wInfo[0].toString()))
    println("Input the watermark transparency percentage (Integer 0-100):")
    val trans = readln()
    if (!Regex("-?\\d+").matches(trans)) println("The transparency percentage isn't an integer number.")
    else if (trans.toInt() !in 0..100) println("The transparency percentage is out of range.")
    else {
        var diffx = image.width - water.width
        var diffy = image.height - water.height
        var isGrid = false
        println("Choose the position method (single, grid):")
        when (readln().lowercase()) {
            "grid" -> {
                isGrid = true
            }
            "single" -> {
                println("Input the watermark position ([x 0-$diffx] [y 0-$diffy]):")
                val pos = readln().split(" ")
                if (pos.size != 2 || pos.any { !Regex("-?[0-9]+").matches(it) }) {
                    println("The position input is invalid.")
                    return
                }
                val (a, b) = pos.map { it.toInt() }
                if (a !in 0..diffx || b !in 0..diffy) {
                    println("The position input is out of range.")
                    return
                }
                diffx = a
                diffy = b
            }

            else -> {
                println("The position method input is invalid.")
                return
            }
        }
        println("Input the output image filename (jpg or png extension):")
        val output = File(readln())
        if (Regex(".+[.](png|jpg)").matches(output.path)) {
            val outImage = BufferedImage(image.width, image.height, BufferedImage.TYPE_INT_RGB)
            for (x in 0 until image.width) {
                for (y in 0 until image.height) {
                    val weight = trans.toInt()
                    val i = Color(image.getRGB(x, y))
                    if ((x - diffx !in 0 until water.width || y - diffy !in 0 until water.height) && !isGrid) {
                        outImage.setRGB(x, y, i.rgb)
                        continue
                    }
                    val w = Color(
                        if (isGrid) water.getRGB(x % water.width, y % water.height) else water.getRGB(
                            x - diffx,
                            y - diffy
                        ), waterIsClean
                    )
                    val color = Color(
                        (weight * w.red + (100 - weight) * i.red) / 100,
                        (weight * w.green + (100 - weight) * i.green) / 100,
                        (weight * w.blue + (100 - weight) * i.blue) / 100
                    )
                    val change = waterIsClean && w.alpha == 0 || transColor != null && transColor == w
                    outImage.setRGB(x, y, (if (change) i else color).rgb)
                }
            }
            ImageIO.write(outImage, output.path.substringAfterLast('.'), output)
            println("The watermarked image $output has been created.")
        } else println("The output file extension isn't \"jpg\" or \"png\".")
    }
}
