package id.yumtaufikhidayat.applymate.core.ext

/**
 * Cleans and validates job application URLs.
 * - Adds https:// if there is no prefix.
 * - Ensures domain and TLD are valid.
 * - Rejects domains without extensions (e.g., www.mo).
 */
fun String.normalizeJobLink(): String {
    val trimmed = trim()

    val withScheme = when {
        trimmed.startsWith("http://", ignoreCase = true) -> trimmed
        trimmed.startsWith("https://", ignoreCase = true) -> trimmed
        else -> "https://$trimmed"
    }

    return withScheme
}

/**
 * Validates application URL format.
 * Stricter than android.util.Patterns.WEB_URL
 */
fun String.isValidJobLink(): Boolean {
    val trimmed = trim()

    // Regex custom (domain & TLD check)
    val strictUrlRegex = Regex(
        pattern = "^(https?://)?([\\w-]+\\.)+[\\w-]{2,}(/\\S*)?$",
        options = setOf(RegexOption.IGNORE_CASE)
    )

    return strictUrlRegex.matches(trimmed)
}

fun String.cleanUrl(): String = replace("https://", "")
    .replace("http://", "")
    .removeSuffix("/")

/**
 * Automatically normalizes links as the user types.
 * - Adds https:// if the user starts with www or a regular domain.
 * - No double prefixes (check existing scheme).
 */
fun String.autoNormalizeLink(): String {
    val trimmed = trim()

    if (trimmed.isEmpty()) return this

    // Prefix exist → don't change anything
    if (trimmed.startsWith("http://", ignoreCase = true) ||
        trimmed.startsWith("https://", ignoreCase = true)
    ) return trimmed

    // Check only when a new user starts typing a domain without a schema
    val looksLikeDomain = Regex("^[\\w.-]+\\.[a-z]{2,}").containsMatchIn(trimmed)
    val shouldNormalize = looksLikeDomain && !trimmed.startsWith("https://")

    return if (shouldNormalize) "https://$trimmed" else trimmed
}

/**
 * Detect whether a string looks like a full domain without a scheme (http/https)
 */
fun String.isValidDomainWithoutScheme(): Boolean {
    val trimmed = trim()
    if (trimmed.startsWith("http://") || trimmed.startsWith("https://")) return false
    return Regex("^[\\w.-]+\\.[a-z]{2,}(\\/.*)?$").matches(trimmed)
}

fun String.normalizeLinkIfNeeded(): String {
    val trimmed = this.trim()
    return when {
        trimmed.startsWith("https://") -> trimmed
        trimmed.isValidDomainWithoutScheme() -> trimmed.autoNormalizeLink()
        else -> trimmed
    }
}


