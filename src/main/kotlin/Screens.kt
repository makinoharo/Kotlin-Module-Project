import java.util.Scanner
import kotlin.system.exitProcess

val scanner = Scanner(System.`in`)

fun readCommand(maxOption: Int): Int {
    while (true) {
        print("> ")
        val input = scanner.nextLine().trim()
        val number = input.toIntOrNull()
        when {
            number == null -> println("Ошибка: введите целое число.")
            number !in 0..maxOption -> println("Ошибка: выберите номер от 0 до $maxOption.")
            else -> return number
        }
    }
}

fun readText(prompt: String): String {
    while (true) {
        println(prompt)
        print("> ")
        val input = scanner.nextLine().trim()
        if (input.isNotEmpty()) {
            return input
        }
        println("Ошибка: текст не может быть пустым.")
    }
}

fun showArchiveMenu() {
    while (true) {
        println("\n--- Список архивов ---")
        println("1. Создать архив")
        archives.forEachIndexed { index, archive -> println("${index + 2}. ${archive.name}") }
        println("0. Выход")

        val command = readCommand(archives.size + 1)
        when (command) {
            0 -> {
                println("До свидания!")
                exitProcess(0)
            }

            1 -> createArchive()
            else -> {
                val archiveIndex = command - 2
                if (archiveIndex in archives.indices) {
                    showNoteMenu(archives[archiveIndex])
                } else {
                    println("Ошибка: такого пункта нет.")
                }
            }
        }
    }
}

fun createArchive() {
    val name = readText("Введите название архива:")
    archives.add(Archive(name))
    println("Архив '$name' создан!")
}

fun showNoteMenu(archive: Archive) {
    while (true) {
        println("\n--- Архив: ${archive.name} ---")
        println("1. Создать заметку")
        archive.notes.forEachIndexed { index, note -> println("${index + 2}. ${note.name}") }
        println("0. Назад")
        println("00. Выход")

        print("> ")
        val input = scanner.nextLine().trim()

        when {
            input == "0" -> return
            input == "00" -> {
                println("До свидания!")
                exitProcess(0)
            }

            input == "1" -> createNote(archive)
            else -> {
                val noteIndex = input.toIntOrNull()
                if (noteIndex != null && noteIndex in 2..archive.notes.size + 1) {
                    showNoteContent(archive.notes[noteIndex - 2])
                } else {
                    println("Ошибка: такого пункта нет.")
                }
            }
        }
    }
}

fun createNote(archive: Archive) {
    val name = readText("Введите название заметки:")
    val text = readText("Введите текст заметки:")
    archive.notes.add(Note(name, text))
    println("Заметка '$name' создана!")
}

fun showNoteContent(note: Note) {
    println("\n--- ${note.name} ---")
    println(note.text)
    println("\n0. Назад")
    while (true) {
        print("> ")
        val input = scanner.nextLine().trim()
        if (input == "0") return
        println("Введите 0 для выхода.")
    }
}