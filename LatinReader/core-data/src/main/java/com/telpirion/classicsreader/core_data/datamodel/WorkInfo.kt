package com.telpirion.classicsreader.core_data.datamodel

data class WorkInfo(
    val id: String,
    val title: String,
    val author: String,
    val englishTitle: String,
    val englishAuthor: String,
    val location: Int,
    val englishLocation: Int,
    val workType: Int,
    val offset: Int = 1,
    val englishOffset: Int = 1,
    val tocEntries: MutableList<TOCEntry> = mutableListOf(),
    val editor: String = "",
    val translator: String = "",
    val description: String = ""
) {

    companion object {
        const val PROSE = 1
        const val POEM = 2
    }

    override fun toString(): String {
        return "$id $title $author $englishTitle $englishAuthor $location"
    }

    fun getTOCCount(): Int {
        return tocEntries.size
    }

    fun addTOCEntry(entry: TOCEntry) {
        tocEntries.add(entry)
    }

    class Builder(private val id: String) {
        private var author: String = ""
        private var title: String = ""
        private var englishTitle: String = ""
        private var englishAuthor: String = ""
        private var location: Int = 0
        private var englishLocation: Int = 0
        private var workType: Int = 0
        private var offset: Int = 1
        private var englishOffset: Int = 1
        private val tocEntries: MutableList<TOCEntry> = mutableListOf()
        private var editor: String = ""
        private var translator: String = ""
        private var description: String = ""

        fun author(author: String) = apply { this.author = author }
        fun title(title: String) = apply { this.title = title }
        fun englishTitle(englishTitle: String) = apply { this.englishTitle = englishTitle }
        fun englishAuthor(englishAuthor: String) = apply { this.englishAuthor = englishAuthor }
        fun location(location: Int) = apply { this.location = location }
        fun englishLocation(englishLocation: Int) = apply { this.englishLocation = englishLocation }
        fun workType(workType: Int) = apply { this.workType = workType }
        fun offset(offset: Int, englishOffset: Int) = apply {
            this.offset = offset
            this.englishOffset = englishOffset
        }
        fun TOCEntry(entry: TOCEntry) = apply { this.tocEntries.add(entry) }
        fun editor(editor: String) = apply { this.editor = editor }
        fun translator(translator: String) = apply { this.translator = translator }
        fun description(description: String) = apply { this.description = description }

        fun create(): WorkInfo {
            val info = WorkInfo(
                id,
                title,
                author,
                englishTitle,
                englishAuthor,
                location,
                englishLocation,
                workType,
                offset,
                englishOffset,
                mutableListOf(),
                editor,
                translator,
                description
            )
            info.tocEntries.addAll(tocEntries)
            return info
        }
    }
}