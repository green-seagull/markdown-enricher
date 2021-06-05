package greenseagull.markdown.enricher.service

import model.DataModel
import model.DataModel.RESOURCES
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.nio.file.Paths

class FindTagsServiceTest {

    private val findTagsService: FindTagsService = FindTagsService()

    @Test
    fun `find all tags in markdown files in a directory`() {
        val markdown = Paths.get(RESOURCES, DataModel.TAGGED_MD)
        Assertions.assertThat(findTagsService.findTags(markdown)).isEqualTo(DataModel.EXPECTED_TAG_SET)
    }
}