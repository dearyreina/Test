package spark

import java.util.regex.Pattern

import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}

object db {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)

    val sparkConf = new SparkConf().setMaster("local").setAppName("wb")
    val sc = new SparkContext(sparkConf)
    val fs = FileSystem.get(sc.hadoopConfiguration)
    fs.deleteOnExit(new Path("output/dbrc"))
    fs.close()
//    val rdd1 = sc.textFile("D:\\db1")
//      .filter(x=>x.contains("data-author-id"))
//      .map(x=>{
//        var uid = ""
//        val r = Pattern.compile("data-author-id=\"([0-9a-zA-Z]+)\"")
//        val m = r.matcher(x)
//        if(m.find){
//          uid = m.group(1)
//        }
//        uid
//      }).filter(x => x.length > 0)
//      .distinct()

    sparkConf.set("textinputformat.record.delimiter","<h4>")
    val rdd = sc.wholeTextFiles("D:\\dbrc",4).flatMap(x=>x._2.split("clearfix comment-item reply-item"))
        .filter(x=>x.contains("https://www.douban.com/people/87838068/")
          || x.contains("https://www.douban.com/people/161954278/")
          || x.contains("https://www.douban.com/people/166606279/")
          || x.contains("https://www.douban.com/people/fsv/")
          || x.contains("https://www.douban.com/people/197182219/"))
        .map(x =>{
          val x_temp = x.replaceAll(" class=\"\"","")
          var time = ""
                  val r = Pattern.compile("(2021-03-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2})")
                  val m = r.matcher(x_temp)
                  if(m.find){
                    time = m.group(1)
                  }
          var uid = ""
          var name = ""
          val r2 = Pattern.compile("<a href=\"https://www.douban.com/people/([0-9a-zA-Z]+)/\">([^<]+)</a>")
          val m2 = r2.matcher(x_temp)
          if(m2.find){
            uid = m2.group(1)
            name = m2.group(2)
          }
          (uid,name,time)
        }).repartition(1)
      .saveAsTextFile("output/dbrc")

    //爬楼

//    val rdd0_1 = sc.textFile("D:\\dbrc",4)
//      .filter(x=>x.contains("https://www.douban.com/people/"))
//      .map(x=>{
//        //val x_temp = x.replaceAll(" class=\"\"","")
//        var uid = ""
//        var name = ""
//        //val r = Pattern.compile("<a href=\"https://www.douban.com/people/([0-9a-zA-Z]+)/\">([^<]+)</a>")
//        val r = Pattern.compile("<a href=\"https://www.douban.com/people/([0-9a-zA-Z]+)/\" class=\"\">([^<]+)</a>")
//        val m = r.matcher(x)
//        if(m.find){
//          uid = m.group(1)
//          name = m.group(2)
//        }
//        (uid,name)
//      }).filter(x => x._1.length > 0).map(x=>(x,1)).reduceByKey(_+_).repartition(1).saveAsTextFile("output/dbrc")
//uid交集
    /**val rdd0_1 = sc.textFile("D:\\db1")
      .filter(x=>x.contains("https://www.douban.com/people/"))
      .map(x=>{
        val x_temp = x.replaceAll(" class=\"\"","")
        var uid = ""
        var name = ""
        val r = Pattern.compile("<a href=\"https://www.douban.com/people/([0-9a-zA-Z]+)/\">([^<]+)</a>")
        val m = r.matcher(x_temp)
        if(m.find){
          uid = m.group(1)
          name = m.group(2)
        }
        (uid,name)
      }).filter(x => x._1.length > 0)
      .distinct()

    val rdd0_2 = sc.textFile("D:\\db2")
      .filter(x=>x.contains("https://www.douban.com/people/"))
      .map(x=>{
        val x_temp = x.replaceAll(" class=\"\"","")
        var uid = ""
        var name = ""
        val r = Pattern.compile("<a href=\"https://www.douban.com/people/([0-9a-zA-Z]+)/\">([^<]+)</a>")
        val m = r.matcher(x_temp)
        if(m.find){
          uid = m.group(1)
          name = m.group(2)
        }
        (uid,name)
      }).filter(x => x._1.length > 0)
      .distinct()

    val rdd0 = rdd0_1.intersection(rdd0_2)

    println(rdd0_1.count())
    println(rdd0_2.count())
    println(rdd0.count())
    rdd0.foreach(println)*/


//    val rdd2 = sc.textFile("D:\\db2")
//      .filter(x=>x.contains("data-author-id"))
//      .map(x=>{
//        var uid = ""
//        val r = Pattern.compile("data-author-id=\"([0-9a-zA-Z]+)\"")
//        val m = r.matcher(x)
//        if(m.find){
//          uid = m.group(1)
//        }
//        uid
//      }).filter(x => x.length > 0)
//      .distinct()
//
//      val rdd3 = rdd1.intersection(rdd2)
  }

}
