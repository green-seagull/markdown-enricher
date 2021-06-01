package greenseagull.markdown.enricher.service

object MarkdownRegex {
    val LINK_REGEX = """\[\[([a-zAZ0-9_\ ]+)]]""".toRegex(setOf(
        RegexOption.IGNORE_CASE,
        RegexOption.COMMENTS,
        RegexOption.UNIX_LINES
    ))
}