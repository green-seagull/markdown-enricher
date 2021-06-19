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
        private const val TAGGED_MD = "tagged.md"
        private const val MARKDOWN = "src/test/resources/markdown"

        private val TAG: Tag = Tag("#code")
    }

    private lateinit var writableMarkdownFile1: Path
    private lateinit var writableMarkdownFile2: Path

    @BeforeEach
    fun setup() {
        val tempDirPath = Files.createTempDirectory(TagServiceTest::class.simpleName)
        writableMarkdownFile1 = Paths.get(tempDirPath.toFile().absolutePath, LINKED_MD)
        writableMarkdownFile2 = Paths.get(tempDirPath.toFile().absolutePath, TAGGED_MD)

        Files.copy(Paths.get(MARKDOWN, LINKED_MD), writableMarkdownFile1)
        Files.copy(Paths.get(MARKDOWN, TAGGED_MD), writableMarkdownFile2)
    }

    @Test
    fun `should add tag if not already present`() {
        TagService().addMissingTag(writableMarkdownFile1, TAG)

        val countTagOccurrences = Files.lines(writableMarkdownFile1)
            .filter { line -> line.contains(TAG.name) }
            .count()

        assertThat(countTagOccurrences)
            .describedAs("Assertion failed: File $writableMarkdownFile1 must contain '1' tag '${TAG.name}'")
            .isEqualTo(1)
    }

    @Test
    fun `should NOT add tag if file has tag`() {
        TagService().addMissingTag(writableMarkdownFile2, TAG)

        val countTagOccurrences = Files.lines(writableMarkdownFile2)
            .filter { line -> line.contains(TAG.name) }
            .count()

        assertThat(countTagOccurrences)
            .describedAs("Assertion failed: File $writableMarkdownFile2 must contain '1' tag '${TAG.name}'")
            .isEqualTo(1)
    }
}