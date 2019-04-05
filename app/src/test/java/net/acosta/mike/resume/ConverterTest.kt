package net.acosta.mike.resume

import net.acosta.mike.resume.data.room.Converters
import net.acosta.mike.resume.data.ProfileType
import net.acosta.mike.resume.utils.DATETIME_FORMAT
import org.junit.Assert
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

class ConverterTest {

    @Test
    fun profileTypeToStringTest() {
        Assert.assertEquals("Name", Converters.profileTypeToString(ProfileType.Name))
    }

    @Test
    fun stringToProfileTypeTest() {
        Assert.assertEquals(ProfileType.Summary, Converters.stringToProfileType("Summary"))
    }

    @Test
    fun listToDelimitedStringTest() {
        val list = mutableListOf("kotlin", "java", "python", "javascript")
        val expected = "kotlin|java|python|javascript"
        Assert.assertEquals(expected, Converters.listToDelimitedString(list))
    }

    @Test
    fun delimitedStringToListTest() {
        val string = "Android|iOS|Windows|BlackBerry"
        val expected = mutableListOf("Android", "iOS", "Windows", "BlackBerry")
        Assert.assertEquals(expected, Converters.delimitedStringToList(string))
    }

    @Test
    fun stringToDateTimeTest() {
        val string = "2018-10-08 16:41:10.207"
        val expected = SimpleDateFormat(DATETIME_FORMAT, Locale.US).parse(string)
        Assert.assertEquals(expected, Converters.stringToDateTime(string))
    }


    @Test
    fun dateTimeToStringTest() {
        val dateTimeString = "2018-10-08 16:41:10.207"
        val dateTime = SimpleDateFormat(DATETIME_FORMAT, Locale.US).parse(dateTimeString)
        Assert.assertEquals(dateTimeString, Converters.dateTimeToString(dateTime))
    }
}