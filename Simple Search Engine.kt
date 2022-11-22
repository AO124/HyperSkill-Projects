package search

import java.io.File

val invertedIndex = mutableMapOf<String, MutableList<Int>>()
fun find(datas: List<String>) {
    fun word(x: String, n: Boolean = false) = x.let { invertedIndex[it] ?: emptyList() }.let { datas.filterIndexed {i, _ -> (i in it) xor n } }
    println("Select a matching strategy: ALL, ANY, NONE")
    val strategy = readln()
    println("Enter a name or email to search all suitable people.")
    val quary = readln().lowercase().split(" ")
    val match = when(strategy) {
        "ANY" -> quary.map { word(it) }.fold(listOf<String>()) {a, b -> a + b}
        "NONE" -> quary.map { word(it) }.fold(listOf<String>()) {a, b -> a + b}.let {
            datas.filter { a -> a !in it }
        }
        else -> quary.map { word(it) }.fold(listOf()) {a, b -> if (quary.all { it in b }) a + b else a}
    }
    println(if (match.isNotEmpty()) "${match.size} persons found:\n${match.joinToString("\n")}" else "No matching people found.")
}
fun printing(datas: List<String>) = println("=== List of people ===\n${datas.joinToString("\n")}")
fun main(args: Array<String>) {
    val datas = File(args[args.indexOf("--data") + 1]).readLines()
    datas.forEachIndexed {i, e ->
        e.lowercase().split(" ").forEach {
            invertedIndex[it] = invertedIndex[it] ?: mutableListOf()
            invertedIndex[it]?.add(i)
        }
    }

    while (true) {
        println("=== Menu ===\n1. Find a person\n2. Print all people\n0. Exit")
        when (readln()) {
            "0" -> break
            "1" -> find(datas)
            "2" -> printing(datas)
            else -> println("Incorrect option! Try again.")
        }
    }
    println("Bye!")
}
