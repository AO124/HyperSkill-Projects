import java.io.File
var len = 0
fun nameList(str: String,space: String ,location: String): List<String> {
    val file = File(location).readLines()
    val ans = MutableList(file[0].split(" ")[0].toInt()) { mutableListOf<String>() }
    for (x in ans.indices) {
    	for (i in str) {
            for (j in file.indices) {
                if (i == ' ') {
                    ans[x].add(space)
                    break
                }
                else if (file[j][0] == i && file[j][1] == ' ') {
                    ans[x].add(file[j + x + 1])
                }
            }
        }
    }
    return ans.map {
        val line = it.joinToString("")
        len = if (len < line.length) line.length else len
        line
    }
}
fun main() {
    print("Enter name and surname: ")
    val name = nameList(readln(), "          ", System.getProperty("user.dir") + "\\src\\signature\\roman (1).txt")
    val nameLen = len
    print("Enter person's status: ")
    val status = nameList(readln(), "     ", System.getProperty("user.dir") + "\\src\\signature\\medium (1).txt")
    val statusLen = len
    val n = maxOf(nameLen, statusLen) + 4

    fun magic (i: String) {
        val x = (n - i.length) / 2
        print("88" + " ".repeat(x))
        print(i)
        println(" ".repeat(n - x - i.length) + "88")
    }
    println("8".repeat(n + 4))
    name.forEach { magic(it) }
    status.forEach { magic(it) }
    println("8".repeat(n + 4))
}
