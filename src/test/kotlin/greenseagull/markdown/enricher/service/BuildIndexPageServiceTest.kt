package greenseagull.markdown.enricher.service

import greenseagull.markdown.enricher.model.Tag
import greenseagull.markdown.enricher.service.TestDataLibrary.EXPECTED_TAGGED_TAG_SET
import greenseagull.markdown.enricher.service.TestDataLibrary.TAGGED_MD
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.nio.file.Files
import java.nio.file.Paths

class BuildIndexPageServiceTest {
    companion object {
        private val MARKDOWN = "src/test/resources/markdown"
        private const val TMP = "/tmp"
        private val WRITABLE_MARKDOWN_PATH = Paths.get(TMP, "markdown")
        private val WRITABLE_MARKDOWN_FILE1 = Paths.get(TMP, "markdown", TAGGED_MD)
        private val READONLY_MARKODWN_FILE1 = Paths.get(MARKDOWN, TAGGED_MD)

        private val TAG = Tag("[[markdown]]")
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
    fun `should add tag to existing markdown and not change other lines in file`() {
        BuildIndexPageService().addTag(WRITABLE_MARKDOWN_FILE1, TAG)

        assertThat(FindTagsService().findFindTags(WRITABLE_MARKDOWN_FILE1))
            .isEqualTo(EXPECTED_TAGGED_TAG_SET + setOf(TAG))
        assertThat(Files.lines(READONLY_MARKODWN_FILE1).count())
            .isEqualTo(Files.lines(WRITABLE_MARKDOWN_FILE1).count())
    }
}