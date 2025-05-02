package ui.utils


class OutputFormatter {
    // ANSI color codes
    private val RESET = "\u001B[0m"
    private val RED = "\u001B[31m"
    private val GREEN = "\u001B[32m"
    private val YELLOW = "\u001B[33m"
    private val BLUE = "\u001B[34m"
    private val PURPLE = "\u001B[35m"
    private val CYAN = "\u001B[36m"
    private val BOLD = "\u001B[1m"

    // Enable/disable colors based on system support
    private val useColors = !System.getProperty("os.name").startsWith("Windows") ||
            System.getenv("TERM") != null

    /**
     * Prints a header with emphasis
     */
    fun printHeader(text: String) {
        val line = "=".repeat(text.length + 4)
        println()
        println(colorize(line, BOLD))
        println(colorize("  $text  ", BOLD))
        println(colorize(line, BOLD))
        println()
    }

    /**
     * Prints a sub-header with less emphasis
     */
    fun printSubHeader(text: String) {
        val line = "-".repeat(text.length + 4)
        println()
        println(colorize(line, BOLD))
        println(colorize("  $text  ", BOLD))
        println(colorize(line, BOLD))
    }

    /**
     * Prints a menu of options
     */
    fun printMenu(options: List<String>) {
        println()
        options.forEach { option ->
            println(colorize(option, CYAN))
        }
        println()
    }

    /**
     * Prints a success message
     */
    fun printSuccess(message: String) {
        println(colorize("✓ SUCCESS: $message", GREEN))
    }

    /**
     * Prints an error message
     */
    fun printError(message: String) {
        println(colorize("✗ ERROR: $message", RED))
    }

    /**
     * Prints an informational message
     */
    fun printInfo(message: String) {
        println(colorize(message, RESET))
    }

    /**
     * Prints a warning message
     */
    fun printWarning(message: String) {
        println(colorize("! WARNING: $message", YELLOW))
    }

    /**
     * Prints a horizontal divider line
     */
    fun printDivider(width: Int = 80) {
        println(colorize("-".repeat(width), RESET))
    }

    /**
     * Prints a row of data for tabular display (used in swimlanes)
     */
    fun printRow(columns: List<String>) {
        val columnSeparator = colorize(" | ", BLUE)
        println(columns.joinToString(separator = columnSeparator))
    }

    /**
     * Applies color to text if colors are enabled
     */
    private fun colorize(text: String, color: String): String {
        return if (useColors) "$color$text$RESET" else text
    }
}