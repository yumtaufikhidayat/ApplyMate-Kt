package id.yumtaufikhidayat.applymate.core.ext

inline fun <reified T : Enum<T>> String.toEnum(): T = enumValueOf(this)