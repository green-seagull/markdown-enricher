package greenseagull.markdown.enricher.service

import greenseagull.markdown.enricher.model.Tag
import java.nio.file.Files
import java.nio.file.Path
import kotlin.streams.toList
import kotlin.text.RegexOption.*

class FindTagsService {
    companion object {
        private val TAG_REGEX = """\[\[([a-zAZ0-9_\ ]+)]]""".toRegex(setOf(IGNORE_CASE, COMMENTS, UNIX_LINES))
    }

    fun findFindTags(path: Path): Set<Tag> =
        when {
            Files.isRegularFile(path) -> scanForTagsSingleFile(path)
            Files.isDirectory(path) -> scanForTagsDirectory(path)
            else -> emptySet()
        }

    private fun scanForTagsDirectory(path: Path): Set<Tag> {
        val stream = Files.walk(path)

        return stream
            .filter { it != path }
            .map {
            val tags = findFindTags(it)
            tags
        }
            .toList()
            .flatten()
            .toSet()

    }

    private fun scanForTagsSingleFile(path: Path): Set<Tag> {
        val tagsLineRaw = Files.lines(path)
            .filter { line ->
                TAG_REGEX.containsMatchIn(line)
            }
            .toList()

        return tagsLineRaw
            .asSequence()
            .map { line -> TAG_REGEX.findAll(line).toList() }
            .flatten()
            .map { it.value }
            .map { Tag(it) }
            .toSet()
    }
}

