package greenseagull.markdown.enricher.service

import greenseagull.markdown.enricher.model.Link
import greenseagull.markdown.enricher.service.MarkdownRegex.LINK_REGEX
import java.nio.file.Path

class FindLinksService {
    private val findRegexInMarkdownService: FindRegexInMarkdownService = FindRegexInMarkdownService(LINK_REGEX)

    fun findFindLinks(path: Path): Set<Link> = findRegexInMarkdownService.findElement(path)
        .map { Link(it) }
        .toSet()
}