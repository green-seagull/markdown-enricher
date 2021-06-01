package greenseagull.markdown.enricher.service

import greenseagull.markdown.enricher.model.Link
import java.nio.file.Files
import java.nio.file.Path
import kotlin.streams.toList

class BuildIndexPageService {
    companion object {
        val LINK_REGEX = """\[\[([a-zAZ0-9_\ ]+)]]""".toRegex(setOf(
            RegexOption.IGNORE_CASE,
            RegexOption.COMMENTS,
            RegexOption.UNIX_LINES
        ))
    }

    fun addLink(path: Path, link: Link) {
        val lines = Files.lines(path)
            .map { line ->
                if (LINK_REGEX.containsMatchIn(line)) {
                    "$line $link"
                } else line
            }.toList()

        Files.write(path, lines)
    }
}
