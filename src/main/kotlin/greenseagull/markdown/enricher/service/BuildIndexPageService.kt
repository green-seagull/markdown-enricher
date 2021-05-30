package greenseagull.markdown.enricher.service

import greenseagull.markdown.enricher.model.Tag
import java.nio.file.Files
import java.nio.file.Path
import kotlin.streams.toList

class BuildIndexPageService {
    companion object {
        val TAG_REGEX = """\[\[([a-zAZ0-9_\ ]+)]]""".toRegex(setOf(
            RegexOption.IGNORE_CASE,
            RegexOption.COMMENTS,
            RegexOption.UNIX_LINES
        ))
    }

    fun addTag(path: Path, tag: Tag) {
        val lines = Files.lines(path)
            .map { line ->
                if (TAG_REGEX.containsMatchIn(line)) {
                    "$line $tag"
                } else line
            }.toList()

        Files.write(path, lines)
    }
}
