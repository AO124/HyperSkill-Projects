package sorting
import java.io.File

fun yIsGreat(x: String, y: String, t: String): Boolean {
    if (t == "long") return x.toLong() < y.toLong()
    if (x.length == y.length) return x == maxOf(x, y)
    return x.length < y.length
}
fun sorter(list: MutableList<String>, type: String): MutableList<String> {
    if (list.size == 1) return list
    val div = list.size / 2
    val (list1, list2) = listOf(list.subList(0, div), list.subList(div, list.size)).map { sorter(it.toMutableList(), type) }
    var pointer = 0
    while (list2.isNotEmpty()) {
        val x = list2.first()
        if (pointer >= list1.size || yIsGreat(x, list1[pointer],type)) list1.add(pointer, list2.removeFirst())
        pointer++
    }
    return list1
}
fun main(args: Array<String>) {
    val whatToPrint = mutableListOf<String> ()
    if ("-sortingType" in args && Regex("-sortingType(natural|byCount)").find(args.joinToString("")) == null) {
        println("No sorting type defined!")
        return
    } else if ("-dataType" in args && Regex("-dataType(long|line|word)").find(args.joinToString("")) == null) {
        println("No data type defined!")
        return
    }
    args.forEach {
        if (it[0] == '-' && it !in listOf("-dataType", "-sortingType", "-inputFile", "-outputFile")) println("\"$it\" is not a valid parameter. It will be skipped.")
    }
    val typeIndex = args.indexOf("-dataType")
    val type = if (typeIndex == -1) "word" else args[typeIndex + 1]
    val datas = mutableListOf<String> ()
    try {
        val input = args.indexOf("-inputFile")
        if (input > -1) datas.addAll(File(args[input + 1]).readLines())
        else while (true) {
        val line = readln()
        if (type == "line") datas.add(line) else datas.addAll(line.split(Regex(" +")))
    }
    } catch (_: Exception)  {}
    whatToPrint.add("Total ${if (type == "long")"number" else type}s: ${datas.size}")
    if ("-sortingType" in args && args[args.indexOf("-sortingType") + 1] == "byCount") {
        val temp = datas.toSet().map { x -> x to datas.count {y -> y == x } }
        val ans = mutableMapOf<Int, List<String>>()
        temp.forEach {
            val (a, b) = it
            ans[b] = (ans[b] ?: listOf()) + a
        }
        for (i in ans.keys.sorted()) {
            val new = if (type != "long") ans[i]!!.sorted() else sorter(ans[i]!!.toMutableList(), type)
            for (j in new) whatToPrint.add("$j: $i time(s), ${i * 100 / datas.size}%")
        }
    } else whatToPrint.add("Sorted data:" + if (type != "line") sorter(datas, type).joinToString(" ") else sorter(datas, type).joinToString("\n", "\n", "\n"))
    val output = args.indexOf("-outputFile")
    if (output > -1) File(args[output + 1]).writeText(whatToPrint.joinToString("\n"))
    else whatToPrint.forEach { println(it) }
}
