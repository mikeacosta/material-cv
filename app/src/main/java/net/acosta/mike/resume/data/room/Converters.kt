package net.acosta.mike.resume.data.room

import androidx.room.TypeConverter
import net.acosta.mike.resume.data.ProfileType
import net.acosta.mike.resume.utils.DATETIME_FORMAT
import java.text.SimpleDateFormat
import java.util.*


object Converters {

    private const val SEPARATOR = "|"
    private val dateTimeFormat = SimpleDateFormat(DATETIME_FORMAT, Locale.US)

    @TypeConverter
    @JvmStatic
    fun profileTypeToString(profileType: ProfileType): String {
        return profileType.toString()
    }

    @TypeConverter
    @JvmStatic
    fun stringToProfileType(profileType: String): ProfileType {
        return ProfileType.valueOf(profileType)
    }

    @TypeConverter
    @JvmStatic
    fun listToDelimitedString(content: MutableList<String>): String {
        return content.joinToString(separator = SEPARATOR) { it }
    }

    @TypeConverter
    @JvmStatic
    fun delimitedStringToList(delimitedString: String): MutableList<String> {
        return delimitedString.split(SEPARATOR).map { it }.toMutableList()
    }

    @TypeConverter
    @JvmStatic
    fun stringToDateTime(value: String?): Date? {
        if (value == null)
            return null

        return dateTimeFormat.parse(value)
    }

    @TypeConverter
    @JvmStatic
    fun dateTimeToString(date: Date?): String? {
        if (date == null)
            return null

        return dateTimeFormat.format(date)
    }
}