package ui.utils


class OutputFormatter {

    private val RESET = "\u001B[0m"
    private val RED = "\u001B[31m"
    private val GREEN = "\u001B[32m"
    private val YELLOW = "\u001B[33m"
    private val CYAN = "\u001B[36m"
    private val BOLD = "\u001B[1m"

    private val useColors = !System.getProperty("os.name").startsWith("Windows") ||
            System.getenv("TERM") != null

    fun printHeader(text: String) {
        val line = "=".repeat(text.length + 4)
        println()
        println(colorize(line, BOLD))
        println(colorize("  $text  ", BOLD))
        println(colorize(line, BOLD))
        println()
    }

    fun printMenu(options: List<String>) {
        println()
        options.forEach { option ->
            println(colorize(option, CYAN))
        }
        println()
    }

    fun printSuccess(message: String) {
        println(colorize("✓ SUCCESS: $message", GREEN))
    }

    fun printError(message: String) {
        println(colorize("✗ ERROR: $message", RED))
    }

    fun printInfo(message: String) {
        println(colorize(message, RESET))
    }

    fun printWarning(message: String) {
        println(colorize("! WARNING: $message", YELLOW))
    }

    private fun colorize(text: String, color: String): String {
        return if (useColors) "$color$text$RESET" else text
    }
}