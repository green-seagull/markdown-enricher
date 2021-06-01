package greenseagull.markdown.enricher.service

import greenseagull.markdown.enricher.model.Link
import greenseagull.markdown.enricher.service.MarkdownRegex.LINK_REGEX
import java.nio.file.Files
import java.nio.file.Path
import kotlin.streams.toList

class BuildIndexPageService {
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
