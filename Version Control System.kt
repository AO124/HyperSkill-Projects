package svcs

import java.io.File

val help = mapOf(

    "config" to "Get and set a username.",

    "add" to "Add a file to the index.",

    "log" to "Show commit logs.",

    "commit" to "Save changes.",

    "checkout" to "Restore a file."

)

fun main(args: Array<String>) {

    val file = File("vcs")

    if (!file.exists()) file.mkdir()

    val config = File("vcs\\config.txt")

    if (!config.exists()) config.createNewFile()

    val index = File("vcs\\index.txt")

    if (!index.exists()) index.createNewFile()

    val commit = File("vcs\\commits")

    if (!commit.exists()) commit.mkdir()

    val log = File("vcs\\log.txt")

    if (!log.exists()) log.createNewFile()

    if (args.isEmpty() || args.first() == "--help") {

        println("These are SVCS commands:")

        for ((k, v) in help) println("%-11s%s".format(k, v))

    } else if (args.first() in help.keys) when(args.first()) {

        "config" -> {

            var newName = config.readText()

            if (args.size == 1 && newName.isBlank()) {

                println("Please, tell me who you are.")

            }

            else if (args.size == 2) newName = args[1]

            if (newName.isNotBlank()) println("The username is $newName.")

            config.writeText(newName)

        }

        "add" -> {

            val indexStr = index.readLines()

            if (args.size == 2) {

                val xfile = File(args[1])

                if (xfile.exists()) {

                    println("The file '${args[1]}' is tracked.")

                    index.appendText("\n" + args[1])

                } else println("Can't find '${args[1]}'.")

            } else {

                if (indexStr.isEmpty()) println("Add a file to the index.")

                else {

                    print("Tracked files:")

                    println(index.readText())

                }

            }

        }

        "commit" -> {

            if (args.size != 2) {

                println("Message was not passed.")

                return

            }

            val files = index.readLines()

            var hash = config.readText().hashCode()

            for (i in files.subList(1, files.size)) hash += File(i).readText().hashCode()

            val hashFile = File("vcs\\commits\\$hash")

            if (hashFile.exists()) println("Nothing to commit.")

            else {

                hashFile.mkdir()

                for (i in files.subList(1, files.size)) File(i).copyTo(File("$hashFile\\$i"))

                println("Changes are committed.")

                var text = """

                    commit $hash

                    Author: ${config.readText()}

                    ${args[1]}

                """.trimIndent()

                val old = log.readText()

                if (old.isNotBlank()) text += "\n\n$old"

                log.writeText(text)

            }

        }

        "log" -> {

            val txt = log.readText()

            if (txt.isBlank()) println("No commits yet.")

            else println(txt)

        }

        "checkout" -> {

            if (args.size != 2) {

                println("Commit id was not passed.")

                return

            }

            val commits = commit.walk().maxDepth(1).filter { it.isDirectory }.toList()

            for (i in commits) if (i.path.split("\\").last() == args[1]) {

                val files = index.readLines()

                for (j in files.subList(1, files.size)) {

                    File(j).delete()

                    File("$i\\$j").copyTo(File(j))

                }

                println("Switched to commit ${args[1]}.")

                return

            }

            println("Commit does not exist.")

        }

        else -> println(help[args.first()])

    }

    else println("'${args.first()}' is not a SVCS command.")

}
