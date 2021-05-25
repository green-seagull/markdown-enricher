package greenseagull.markdown.enricher.service

import greenseagull.markdown.enricher.model.Tag

object TestDataLibrary {
    const val TAGGED_MD = "tagged.md"
    val EXPECTED_TAGGED_TAG_SET = setOf(Tag("[[first_tag]]"), Tag( "[[secondTag]]"))

    const val TAGGED_WITH_SPACE_MD = "tagged_with_space.md"
    val EXPECTED_TAGGED_WITH_SPACE_TAG_SET = setOf(Tag("[[testing ]]"), Tag( "[[observability]]"))
}