package greenseagull.markdown.enricher.service

import greenseagull.markdown.enricher.model.Link
import model.DataModel.EXPECTED_LINK_SET
import model.DataModel.LINKED_MD
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

class BuildIndexPageServiceTest {
    companion object {
        private const val MARKDOWN = "src/test/resources/markdown"
        private val READONLY_MARKDOWN_FILE1 = Paths.get(MARKDOWN, LINKED_MD)

        private val LINK = Link("[[markdown]]")
    }

    private lateinit var writableMarkdownFile1: Path

    @BeforeEach
    fun setup() {
        val tempDirPath = Files.createTempDirectory(BuildIndexPageServiceTest::class.simpleName)
        writableMarkdownFile1 = Paths.get(tempDirPath.toFile().absolutePath, LINKED_MD)

        Files.copy(READONLY_MARKDOWN_FILE1, writableMarkdownFile1)
    }

    @Test
    fun `should add link to existing markdown and not change other lines in file`() {
        BuildIndexPageService().addLink(writableMarkdownFile1, LINK)

        assertThat(FindLinksService().findFindLinks(writableMarkdownFile1))
            .isEqualTo(EXPECTED_LINK_SET + setOf(LINK))
        assertThat(Files.lines(READONLY_MARKDOWN_FILE1).count())
            .isEqualTo(Files.lines(writableMarkdownFile1).count())
    }
}