package net.acosta.mike.resume.utils

import android.content.Context
import javax.inject.Singleton
import android.graphics.Typeface
import androidx.core.content.res.ResourcesCompat
import java.util.*
import javax.inject.Inject


@Singleton
class FontCache @Inject constructor(private val context: Context) {
    private val fontCache = Hashtable<String, Typeface>()

    fun get(resourceId: Int): Typeface? {
        var typeface: Typeface? = fontCache[resourceId.toString()]
        if  (typeface == null) {
            try {
                typeface = ResourcesCompat.getFont(context, resourceId)
            } catch (e: Exception) {
                return null
            }

            fontCache[resourceId.toString()] = typeface
        }

        return typeface
    }
}