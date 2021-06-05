package greenseagull.markdown.enricher.service

import model.DataModel.EXPECTED_LINK_SET
import model.DataModel.EXPECTED_LINK_WITH_SPACE_SET
import model.DataModel.LINKED_MD
import model.DataModel.LINKED_WITH_SPACE_MD
import model.DataModel.RESOURCES
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.nio.file.Paths


class FindLinksServiceTest {

    private val findLinksService = FindLinksService()

    @Test
    fun `find links in somewhere in markdown file`() {
        val markdown = Paths.get(RESOURCES, LINKED_MD)
        assertThat(findLinksService.findFindLinks(markdown)).isEqualTo(EXPECTED_LINK_SET)
    }

    @Test
    fun `find links with spaces in markdown file`() {
        val markdown = Paths.get(RESOURCES, LINKED_WITH_SPACE_MD)
        assertThat(findLinksService.findFindLinks(markdown)).isEqualTo(EXPECTED_LINK_WITH_SPACE_SET)
    }

    @Test
    fun `find all links in markdown files in a directory`() {
        val markdown = Paths.get(RESOURCES)
        val allLinksSet = EXPECTED_LINK_SET + EXPECTED_LINK_WITH_SPACE_SET
        assertThat(findLinksService.findFindLinks(markdown)).isEqualTo(allLinksSet)
    }
}
