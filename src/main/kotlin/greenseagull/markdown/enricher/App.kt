package greenseagull.markdown.enricher

import greenseagull.markdown.enricher.model.Tag
import greenseagull.markdown.enricher.service.TagService
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.system.exitProcess

val regexOptionsSet = setOf(
    RegexOption.IGNORE_CASE,
    RegexOption.COMMENTS,
    RegexOption.UNIX_LINES
)

val tagRegex = """(\#[a-zA-Z0-9]+)""".toRegex(regexOptionsSet)

fun main(args: Array<String>) {
    if (args.size != 2) {
        println("Args: $args")
        exitPrintingUsage()
    }

    val tag = args[0]
    val path = args[1]

    if (!Files.exists(Paths.get(path))) {
        System.err.println("Dir/path does not exist: '$path'")
        exitPrintingUsage()
    }

    if (!tagRegex.containsMatchIn(tag)) {
        System.err.println("Not valid tag '$tag'")
        exitPrintingUsage()
    }

    TagService().addMissingTag(Paths.get(path), Tag(tag))
}

private fun exitPrintingUsage() {
    println("Usage: gradle run --stacktrace --args=\"<tag> <dir with markdown files or markdown file>\"")
    exitProcess(0)
}