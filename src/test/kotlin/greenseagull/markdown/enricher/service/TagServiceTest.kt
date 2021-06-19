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
        private const val IGNORED_TXT = "ignored.txt"
        private const val MARKDOWN_MD = "markdown.md"
        private const val MARKDOWN = "src/test/resources/markdown"

        private val TAG: Tag = Tag("#code")
    }

    private lateinit var writableMarkdownFile1: Path
    private lateinit var writableMarkdownFile2: Path
    private lateinit var writableMarkdownFile3: Path
    private lateinit var textFile: Path
    private lateinit var tempDirPath: Path

    @BeforeEach
    fun setup() {
        tempDirPath = Files.createTempDirectory(TagServiceTest::class.simpleName)
        val tempDir = tempDirPath.toFile().absolutePath

        writableMarkdownFile1 = Paths.get(tempDir, LINKED_MD)
        writableMarkdownFile2 = Paths.get(tempDir, TAGGED_MD)
        textFile = Paths.get(tempDir, IGNORED_TXT)

        Files.createDirectory(Paths.get(tempDir, "nested"))
        writableMarkdownFile3 = Paths.get(tempDir, "nested", MARKDOWN_MD)

        Files.copy(Paths.get(MARKDOWN, LINKED_MD), writableMarkdownFile1)
        Files.copy(Paths.get(MARKDOWN, TAGGED_MD), writableMarkdownFile2)
        Files.copy(Paths.get(MARKDOWN, "nested", MARKDOWN_MD), writableMarkdownFile3)
        Files.copy(Paths.get(MARKDOWN, IGNORED_TXT), textFile)
    }

    @Test
    fun `should add tag if not already present`() {
        TagService().addMissingTag(writableMarkdownFile1, TAG)

        assertThat(countOccurrences(TAG.name, writableMarkdownFile1))
            .describedAs("Assertion failed: File $writableMarkdownFile1 must contain '1' tag '${TAG.name}'")
            .isEqualTo(1)
    }

    @Test
    fun `should NOT add tag if file has tag`() {
        TagService().addMissingTag(writableMarkdownFile2, TAG)

        assertThat(countOccurrences(TAG.name, writableMarkdownFile2))
            .describedAs("Assertion failed: File $writableMarkdownFile2 must contain '1' tag '${TAG.name}'")
            .isEqualTo(1)
    }

    @Test
    fun `should tag all md files in directory`() {
        TagService().addMissingTag(tempDirPath, TAG)

        assertThat(countOccurrences(TAG.name, writableMarkdownFile1)).isEqualTo(1)
        assertThat(countOccurrences(TAG.name, writableMarkdownFile2)).isEqualTo(1)
        assertThat(countOccurrences(TAG.name, textFile)).isEqualTo(0)
    }

    private fun countOccurrences(name: String, file: Path) =
        Files.lines(file)
            .filter { line -> line.contains(name) }
            .count()


}