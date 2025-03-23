package gov.emercom.incidenttoolkit
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import kotlin.text.uppercase

fun FormatDate(longDate: Long, format: String = "ddMMMyy HHmm:ss'Z'"): String {
    val date = Date(longDate)
    val formatter = SimpleDateFormat(format, Locale.getDefault())
    formatter.timeZone = TimeZone.getTimeZone("UTC")
    val dateZ = formatter.format(date)
    val dateZCase = dateZ.uppercase()
    return dateZCase
}