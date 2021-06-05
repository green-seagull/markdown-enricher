package greenseagull.markdown.enricher.service

import greenseagull.markdown.enricher.model.Tag
import greenseagull.markdown.enricher.service.MarkdownRegex.TAG_REGEX
import java.nio.file.Path

class FindTagsService {
    private val findRegexInMarkdownService: FindRegexInMarkdownService = FindRegexInMarkdownService(TAG_REGEX)

    fun findTags(path: Path): Set<Tag> = findRegexInMarkdownService
        .findElement(path)
        .map { Tag(it) }
        .toSet()
}