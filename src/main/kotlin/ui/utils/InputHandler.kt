package ui.utils

import java.io.Console


class InputHandler {
    private val console: Console? = System.console()


    fun promptForInput(prompt: String): String {
        print(prompt)
        return readLine() ?: ""
    }


    fun promptForPassword(prompt: String): String {
        // Try to use console for password masking if available
        console?.let {
            return String(it.readPassword(prompt) ?: CharArray(0))
        }

        print(prompt)
        return readLine() ?: ""
    }

    /**
     * Prompts the user for an integer choice within a specified range
     */
    fun promptForIntChoice(prompt: String, range: IntRange): Int {
        while (true) {
            print(prompt)
            val input = readLine() ?: ""

            try {
                val choice = input.toInt()
                if (choice in range) {
                    return choice
                } else {
                    println("Please enter a number between ${range.first} and ${range.last}")
                }
            } catch (e: NumberFormatException) {
                println("Please enter a valid number")
            }
        }
    }

    /**
     * Prompts the user for a yes/no answer
     */
    fun promptForYesNo(prompt: String): Boolean {
        while (true) {
            print("$prompt (y/n): ")
            val input = readLine()?.trim()?.lowercase() ?: ""

            when (input) {
                "y", "yes" -> return true
                "n", "no" -> return false
                else -> println("Please enter 'y' or 'n'")
            }
        }
    }

    /**
     * Waits for the user to press Enter to continue
     */
    fun waitForEnter() {
        print("Press Enter to continue...")
        readLine()
    }
}