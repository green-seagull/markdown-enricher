package model

import greenseagull.markdown.enricher.model.Link
import greenseagull.markdown.enricher.model.Tag

object DataModel {
    const val LINKED_MD = "linked.md"
    const val TAGGED_MD = "tagged.md"
    val EXPECTED_LINK_SET = setOf(Link("[[first_link]]"), Link( "[[secondLink]]"))

    const val LINKED_WITH_SPACE_MD = "linked_with_space.md"
    val EXPECTED_LINK_WITH_SPACE_SET = setOf(Link("[[testing ]]"), Link( "[[observability]]"))

    val EXPECTED_TAG_SET = setOf(Tag("#tag"), Tag("#tag2"))

    const val RESOURCES = "src/test/resources/markdown"
}