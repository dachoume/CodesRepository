package utils

import java.text.SimpleDateFormat
import java.util.Date

class DateUtils extends Serializable {
    private val sdf_oh = new SimpleDateFormat("HH")
    private val sdf_m = new SimpleDateFormat("yyyyMM")


    def getOnlyHour(t: String): Option[String] = {
        try {
            if (t == "") {
                None
            } else {
                Some(sdf_oh.format(new Date(t.toLong * 1000)))
            }
        } catch {
            case ex: Exception =>
                ex.printStackTrace()
                None
        }
    }

    def getMonth(t: String): Option[String] = {
        try {
            if (t == "") {
                None
            } else {
                Some(sdf_m.format(new Date(t.toLong * 1000)))
            }
        } catch {
            case ex: Exception =>
                ex.printStackTrace()
                None
        }
    }
}

object DateUtils {
    var dateUtils: DateUtils = _

    def getDateUtils: DateUtils = {
        synchronized {
            if (dateUtils == null) {
                dateUtils = new DateUtils
            }
        }
        dateUtils
    }
}
