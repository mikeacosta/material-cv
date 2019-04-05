package net.acosta.mike.resume.data

enum class ProfileType(val value: Int) {
    Name(0),
    Summary(1),
    Header(2),
    Highlight(3),
    Skill(4);

    companion object {
        private val map = ProfileType.values().associateBy(ProfileType::value)

        @JvmStatic
        fun fromInt(type: Int) = map[type]
    }
}

enum class DataStatus(val value: Int) {
    Updated(0),
    Stale(1),
    Empty(2)
}