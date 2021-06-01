package greenseagull.markdown.enricher.service

import greenseagull.markdown.enricher.model.Link

object TestDataLibrary {
    const val LINKED_MD = "linked.md"
    val EXPECTED_LINK_SET = setOf(Link("[[first_link]]"), Link( "[[secondLink]]"))

    const val LINKED_WITH_SPACE_MD = "linked_with_space.md"
    val EXPECTED_LINK_WITH_SPACE_SET = setOf(Link("[[testing ]]"), Link( "[[observability]]"))
}