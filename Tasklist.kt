package tasklist

import kotlinx.datetime.*
import com.squareup.moshi.*
import java.io.File
val tasks =  mutableListOf<MutableMap<String, String>>()
val prioColor = mapOf("C" to "\u001B[101m \u001B[0m", "H" to "\u001B[103m \u001B[0m", "N" to "\u001B[102m \u001B[0m", "L" to "\u001B[104m \u001B[0m")
val dueColor = mapOf("O" to "\u001B[101m \u001B[0m", "T" to "\u001B[103m \u001B[0m", "I" to "\u001B[102m \u001B[0m")

val allJobs = mapOf("priority" to ::getPriority, "date" to ::getDate, "time" to ::getTime, "task" to ::getTask)
fun getField(): String {
    while (true) {
        println("Input a field to edit (priority, date, time, task):")
        val x = readln()
        if (x in allJobs.keys) return x
        println("Invalid field")
    }
}
fun editing() {
    if (tasks.isEmpty()) {
        println("No tasks have been input")
        return
    }
    printAll()
    while (true) {
        println("Input the task number (1-${tasks.size}):")
        val t = readln()
        if (Regex("\\d+").matches(t) && t.toInt() in 1..tasks.size) {
            getField().let { tasks[t.toInt() - 1][it] = allJobs[it]!!()}
            println("The task is changed")
            return
        }
        println("Invalid task number")
    }
}
fun delete() {
    if (tasks.isEmpty()) {
        println("No tasks have been input")
        return
    }
    printAll()
    while (true) {
        println("Input the task number (1-${tasks.size}):")
        val t = readln()
        if (Regex("\\d+").matches(t) && t.toInt() in 1..tasks.size) {
            tasks.removeAt(t.toInt() - 1)
            println("The task is deleted")
            return
        }
        println("Invalid task number")
    }
}
fun getDate(): String{
    while (true) {
        try {
            println("Input the date (yyyy-mm-dd):")
            val (y, m, d) = readln().split("-").map { it.toInt() }
            return LocalDate(y, m, d).toString()
        } catch (e: IllegalArgumentException) {
            println("The input date is invalid")
        }
    }
}
fun getTime(): String {
    while (true) {
        try {
            println("Input the time (hh:mm):")
            val (h, min) = readln().split(":").map { it.toInt() }
            return LocalDateTime(2001, 1, 1, h, min).toString().substringAfter('T')
        } catch (e: IllegalArgumentException) {
            println("The input time is invalid")
        }
    }
}
fun getPriority(): String {
    while (true) {
        println("Input the task priority (C, H, N, L):")
        val importance = readln().uppercase()
        if (importance in listOf("C", "H", "N", "L")) return prioColor[importance]!!
    }
}
fun getTask(): String {
    val task = mutableListOf<String>()
    println("Input a new task (enter a blank line to end):")
    while (true) {
        val line = readln().trim()
        if (line.isBlank()) break
        task.add(line)
    }
    return task.joinToString("\n")
}
fun add() {
    val all = mutableMapOf("priority" to getPriority(), "date" to getDate(), "time" to getTime(), "task" to getTask())
    if (all["task"] == "") println("The task is blank")
    else {
        tasks.add(all)
    }
}
fun getUntil(date: String): String {
    val (y, m, d) = date.split("-").map { it.toInt() }
    val x = Clock.System.now().toLocalDateTime(TimeZone.of("UTC+0")).date.daysUntil(LocalDate(y, m, d))
    return dueColor[if (x > 0) "I" else if (x < 0) "O" else "T"]!!
}
fun gap() = println(List(80) { if (it in listOf(0, 5, 18, 26, 30, 34, 79)) "+" else "-" }.joinToString(""))
fun show2(i: Int, e: Map<String, String>) {
    val temp = mutableListOf<String>()
    for (line in e["task"]!!.split("\n")) {
        var linePart = ""
        for (c in line) {
            linePart  += c
            if (linePart.length == 44) {
                temp.add(linePart)
                linePart = ""
            }
        }
        if (linePart != "") temp.add(linePart)
    }
    temp.forEachIndexed {n, m ->
        val add = if (n == 0) "| ${"%-2d".format(i + 1)} | ${e["date"]} | ${e["time"]} | ${e["priority"]} | ${getUntil(e["date"]!!)} |" else "|    |            |       |   |   |"
        println(add + "%-44s|".format(m))
    }
    gap()
}

fun printAll() {
    if (tasks.isEmpty()) {
        println("No tasks have been input")
        return
    }
    gap()
    println("| N  |    Date    | Time  | P | D |                   Task                     |")
    gap()
    tasks.forEachIndexed(::show2)
}
fun main() {
    val jsonFile = File("tasklist.json")
    val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
    val type = Types.newParameterizedType(List::class.java, Map::class.java)
    val taskListAdapter = moshi.adapter<List<Map<String, String>>>(type)
    if (jsonFile.exists()) tasks.addAll(taskListAdapter.fromJson(jsonFile.readText())!!.map { it.toMutableMap() })
    while (true) {
        println("Input an action (add, print, edit, delete, end):")

        when(readln().lowercase()) {
            "add" -> add()
            "print" -> printAll()
            "end" -> break
            "delete" -> delete()
            "edit" -> editing()
            else -> println("The input action is invalid")
        }
    }
    jsonFile.writeText(taskListAdapter.toJson(tasks))
    println("Tasklist exiting!")
}
