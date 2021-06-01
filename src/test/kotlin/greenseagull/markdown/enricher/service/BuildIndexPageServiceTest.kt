package greenseagull.markdown.enricher.service

import greenseagull.markdown.enricher.model.Link
import greenseagull.markdown.enricher.service.TestDataLibrary.EXPECTED_LINK_SET
import greenseagull.markdown.enricher.service.TestDataLibrary.LINKED_MD
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.nio.file.Files
import java.nio.file.Paths

class BuildIndexPageServiceTest {
    companion object {
        private const val MARKDOWN = "src/test/resources/markdown"
        private const val TMP = "/tmp"
        private val WRITABLE_MARKDOWN_PATH = Paths.get(TMP, "markdown")
        private val WRITABLE_MARKDOWN_FILE1 = Paths.get(TMP, "markdown", LINKED_MD)
        private val READONLY_MARKODWN_FILE1 = Paths.get(MARKDOWN, LINKED_MD)

        private val LINK = Link("[[markdown]]")
    }

    @BeforeEach
    fun setup() {
        if (Files.exists(WRITABLE_MARKDOWN_PATH)) {
            if (Files.exists(WRITABLE_MARKDOWN_FILE1)) {
                Files.delete(WRITABLE_MARKDOWN_FILE1)
            }
            Files.delete(WRITABLE_MARKDOWN_PATH)
        }
        Files.createDirectory(WRITABLE_MARKDOWN_PATH)
        Files.copy(READONLY_MARKODWN_FILE1, WRITABLE_MARKDOWN_FILE1)
    }

    @Test
    fun `should add link to existing markdown and not change other lines in file`() {
        BuildIndexPageService().addLink(WRITABLE_MARKDOWN_FILE1, LINK)

        assertThat(FindLinksService().findFindLinks(WRITABLE_MARKDOWN_FILE1))
            .isEqualTo(EXPECTED_LINK_SET + setOf(LINK))
        assertThat(Files.lines(READONLY_MARKODWN_FILE1).count())
            .isEqualTo(Files.lines(WRITABLE_MARKDOWN_FILE1).count())
    }
}