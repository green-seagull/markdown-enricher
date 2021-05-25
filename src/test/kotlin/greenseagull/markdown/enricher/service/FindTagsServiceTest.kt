package greenseagull.markdown.enricher.service

import greenseagull.markdown.enricher.service.TestDataLibrary.EXPECTED_TAGGED_TAG_SET
import greenseagull.markdown.enricher.service.TestDataLibrary.EXPECTED_TAGGED_WITH_SPACE_TAG_SET
import greenseagull.markdown.enricher.service.TestDataLibrary.TAGGED_MD
import greenseagull.markdown.enricher.service.TestDataLibrary.TAGGED_WITH_SPACE_MD
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.nio.file.Paths


class FindTagsServiceTest {

    companion object {
        private const val RESOURCES = "src/test/resources/markdown"
    }

    private val findTagsService = FindTagsService()

    @Test
    fun `find tags in somewhere in markdown file`() {
        val markdown = Paths.get(RESOURCES, TAGGED_MD)
        assertThat(findTagsService.findFindTags(markdown)).isEqualTo(EXPECTED_TAGGED_TAG_SET)
    }

    @Test
    fun `find tags with spaces in markdown file`() {
        val markdown = Paths.get(RESOURCES, TAGGED_WITH_SPACE_MD)
        assertThat(findTagsService.findFindTags(markdown)).isEqualTo(EXPECTED_TAGGED_WITH_SPACE_TAG_SET)
    }

    @Test
    fun `find all tags in markdown files in a directory`() {
        val markdown = Paths.get(RESOURCES)
        val allTagsSet = EXPECTED_TAGGED_TAG_SET + EXPECTED_TAGGED_WITH_SPACE_TAG_SET
        assertThat(findTagsService.findFindTags(markdown)).isEqualTo(allTagsSet)
    }
}
