package greenseagull.markdown.enricher.service

import greenseagull.markdown.enricher.model.Tag
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

class TagServiceTest {
    companion object {
        private const val LINKED_MD = "linked.md"
        private const val MARKDOWN = "src/test/resources/markdown"
        private val READONLY_MARKDOWN_FILE1 = Paths.get(MARKDOWN, LINKED_MD)

        private val TAG: Tag = Tag("#code")
    }

    private lateinit var writableMarkdownFile1: Path

    @BeforeEach
    fun setup() {
        val tempDirPath = Files.createTempDirectory(TagServiceTest::class.simpleName)
        writableMarkdownFile1 = Paths.get(tempDirPath.toFile().absolutePath, LINKED_MD)

        Files.copy(READONLY_MARKDOWN_FILE1, writableMarkdownFile1)
    }

    @Test
    fun `should add tag if not already present`() {
        TagService().addMissingTag(writableMarkdownFile1, TAG)

        Files.lines(writableMarkdownFile1).forEach { println(it) }

        val countTagOccurrences = Files.lines(writableMarkdownFile1)
            .filter { line -> line.contains(TAG.name) }
            .count()

        assertThat(countTagOccurrences)
            .describedAs("Assertion failed: File $writableMarkdownFile1 must contain '1' tag '${TAG.name}'")
            .isEqualTo(1)
    }
}