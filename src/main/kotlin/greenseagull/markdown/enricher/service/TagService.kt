package greenseagull.markdown.enricher.service

import greenseagull.markdown.enricher.model.Tag
import java.io.BufferedWriter
import java.io.FileWriter
import java.nio.file.*
import java.util.stream.Stream

class TagService {
    companion object {
        private val TEMP_FILE = Paths.get("/tmp/markdown-temp.md")
    }

    fun addMissingTag(path: Path, tag: Tag) {
        if (Files.isRegularFile(path)) {
            val containsTag = Files.lines(path).anyMatch { line -> line.contains(tag.name) }
            if (!containsTag) {
                val fileStreamWithPrependedTags = Stream.concat(Stream.of("tags: ${tag.name}\n"), Files.lines(path))

                writeStreamToFile(fileStreamWithPrependedTags, path)
            }
        }
    }

    private fun writeStreamToFile(stream: Stream<String>, path: Path) {
        if (Files.exists(TEMP_FILE)) {
            Files.delete(TEMP_FILE)
        }

        BufferedWriter(FileWriter(TEMP_FILE.toFile())).use { writer ->
            stream.forEach { line -> writer.write(line) }
        }

        Files.move(TEMP_FILE, path, StandardCopyOption.REPLACE_EXISTING)
    }
}