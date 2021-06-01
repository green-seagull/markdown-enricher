package greenseagull.markdown.enricher.service

import greenseagull.markdown.enricher.model.Link
import greenseagull.markdown.enricher.service.MarkdownRegex.LINK_REGEX
import java.nio.file.Files
import java.nio.file.Path
import kotlin.streams.toList

class FindLinksService {
    fun findFindLinks(path: Path): Set<Link> =
        when {
            Files.isRegularFile(path) -> scanForLinksSingleFile(path)
            Files.isDirectory(path) -> scanForLinksDirectory(path)
            else -> emptySet()
        }

    private fun scanForLinksDirectory(path: Path): Set<Link> {
        val stream = Files.walk(path)

        return stream
            .filter { it != path }
            .map {
            val links = findFindLinks(it)
            links
        }
            .toList()
            .flatten()
            .toSet()

    }

    private fun scanForLinksSingleFile(path: Path): Set<Link> {
        val linksLineRaw = Files.lines(path)
            .filter { line ->
                LINK_REGEX.containsMatchIn(line)
            }
            .toList()

        return linksLineRaw
            .asSequence()
            .map { line -> LINK_REGEX.findAll(line).toList() }
            .flatten()
            .map { it.value }
            .map { Link(it) }
            .toSet()
    }
}

