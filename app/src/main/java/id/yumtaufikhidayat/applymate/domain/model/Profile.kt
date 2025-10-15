package id.yumtaufikhidayat.applymate.domain.model

data class Profile(
    val id: Long = 1,
    val name: String,
    val headline: String?,
    val location: String?,
    val level: String?,
    val skills: List<String> = emptyList(),
    val completionScore: Int = 0
)