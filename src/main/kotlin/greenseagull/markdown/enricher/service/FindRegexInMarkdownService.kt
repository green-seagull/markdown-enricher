package greenseagull.markdown.enricher.service

import greenseagull.markdown.enricher.model.Link
import java.nio.file.Files
import java.nio.file.Path
import kotlin.streams.toList

class FindRegexInMarkdownService(private val regex: Regex) {
    fun findElement(path: Path): Set<String> =
        when {
            Files.isRegularFile(path) -> scanForRegexSingleFile(path)
            Files.isDirectory(path) -> scanForRegexDirectory(path)
            else -> emptySet()
        }

    private fun scanForRegexDirectory(path: Path): Set<String> {
        val stream = Files.walk(path)

        return stream
            .filter { it != path }
            .map {
            val links = findElement(it)
            links
        }
            .toList()
            .flatten()
            .toSet()

    }

    private fun scanForRegexSingleFile(path: Path): Set<String> {
        val containsRegex = Files.lines(path)
            .filter { line ->
                regex.containsMatchIn(line)
            }
            .toList()

        return containsRegex
            .asSequence()
            .map { line -> regex.findAll(line).toList()}
            .flatten()
            .map { it.value }
            .toSet()
    }
}

