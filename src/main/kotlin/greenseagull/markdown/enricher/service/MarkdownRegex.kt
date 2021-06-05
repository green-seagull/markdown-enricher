package greenseagull.markdown.enricher.service

object MarkdownRegex {
    private val regexOptionsSet = setOf(
        RegexOption.IGNORE_CASE,
        RegexOption.COMMENTS,
        RegexOption.UNIX_LINES
    )

    val LINK_REGEX = """\[\[([a-zAZ0-9_\ ]+)]]""".toRegex(regexOptionsSet)

    val TAG_REGEX = """(\#[a-zA-Z0-9]+)""".toRegex(regexOptionsSet)
}