package scala.util

import java.text.SimpleDateFormat
import java.util.regex.Pattern
import java.util.{Calendar, Date, Locale}

/**
 * Created by 58 on 2016/9/14.
 */
object DateUtil {

  val sdf: SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
  val utilSdf: SimpleDateFormat  = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US)
  val daySdf: SimpleDateFormat = new SimpleDateFormat("yyyyMMdd")
  val hourSdf: SimpleDateFormat = new SimpleDateFormat("HH")

  /**
   * 时间戳类型转换日期字符串
   * @param timestamp
   * @return
   */
  def getLong2DateFunc(timestamp: String, convertSdf: SimpleDateFormat): String = {
    var datetime = ""
    try {
      datetime = convertSdf.format(timestamp.toLong)
    } catch {
      case e: Exception => datetime = convertSdf.format(new Date(System.currentTimeMillis()))
    }
    datetime
  }

  /**
   * Date类型转换日期字符串
   * @param utilDate
   * @return
   */
  def getDate2DateFunc(utilDate: String, convertSdf: SimpleDateFormat): String = {
    var datetime = ""
    try {
      datetime = convertSdf.format(utilSdf.parse(utilDate))
    } catch {
      case e: Exception => datetime = convertSdf.format(new Date(System.currentTimeMillis()))
    }
    datetime
  }

  /**
   * 字符串类型转换日期字符串
   * @param strDate
   * @return
   */
  def getString2DateFunc(strDate: String, convertSdf: SimpleDateFormat): String = {
    var datetime = ""
    try {
      datetime = convertSdf.format(sdf.parse(strDate))
    } catch {
      case e: Exception => datetime = convertSdf.format(new Date(System.currentTimeMillis()))
    }
    datetime
  }

  /**
   * 日期字符串转换Long类型
   * @param strDate
   * @return
   */
  def getSdate2LongFunc(strDate: String): String = {
    var datetime = ""
    try {
      datetime = sdf.parse(strDate).getTime.toString
    } catch {
      case e: Exception => datetime = "-"
    }
    datetime
  }

  /**
   * 获取前一天日期
   * @param date
   * @return
   */
  def getBeforeDay(date: Date): Date = getBeforeDay(date, 1)

  /**
   * 获取前n天日期
   * @param date
   * @param interval
   * @return
   */
  def getBeforeDay(date: Date, interval: Int): Date = {
    val calendar: Calendar = Calendar.getInstance()
    calendar.setTime(date)
    calendar.add(Calendar.DAY_OF_MONTH, 0 - interval)
    calendar.getTime()
  }

  /**
   * 获取前n月日期
   * @param date
   * @param interval
   * @return
   */
  def getBeforeMonth(date: Date, interval: Int): Date = {
    val calendar: Calendar = Calendar.getInstance()
    calendar.setTime(date)
    calendar.add(Calendar.MONTH, 0 - interval)
    calendar.getTime()
  }

  def main(args: Array[String]) {
    val x = "              <a href=\"https://www.douban.com/people/182408025/\" class=\"\">Jackie</a>"
    var uid = ""
    var name = ""
    val r = Pattern.compile("<a href=\"https://www.douban.com/people/([1-9]+)/\" class=\"\">([^<]+)</a>")
    val m = r.matcher(x)
    if(m.find){
      uid = m.group(1)
      name = m.group(2)
    }
  }
}
