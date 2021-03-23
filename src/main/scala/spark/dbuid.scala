package spark

import java.util.regex.Pattern

import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable.ListBuffer

object dbuid {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)
    val conf = new SparkConf().setMaster("local").setAppName("dbinner")
    val sc = new SparkContext(conf)
    val fs = FileSystem.get(sc.hadoopConfiguration)
    fs.deleteOnExit(new Path("output/dbuid2/"))
    fs.close()

    sc.textFile("D:\\dbtest\\20210310-5\\",4)
      .filter(x => x.contains("https://www.douban.com/people/") && x.contains("class=\"\""))
      .map(x=> {
        val x_temp = x.replaceAll(" class=\"\"", "")
        var uid = ""
        var name = ""
        val r = Pattern.compile("<a href=\"https://www.douban.com/people/([0-9a-zA-Z]+)/\">([^<]+)</a>")
        val m = r.matcher(x_temp)
        if (m.find) {
          uid = m.group(1)
          name = m.group(2)
        }
        uid+"\t"+name
      }).distinct().repartition(1).saveAsTextFile("output/dbuid5/")
  }
}
