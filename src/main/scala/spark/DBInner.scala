package spark

import java.util
import java.util.regex.Pattern

import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable.ListBuffer

object DBInner {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)
    val conf = new SparkConf().setMaster("local").setAppName("dbinner")
    val sc = new SparkContext(conf)
    val fs = FileSystem.get(sc.hadoopConfiguration)
    fs.deleteOnExit(new Path("output/dbinner/"))
    fs.close()

    sc.wholeTextFiles("D:\\dbtest\\20210310\\")
      .flatMap(x=>{
        val list = new ListBuffer[(String,String)]
        val name = x._1.split("#")(1).replaceAll(".html","")
        x._2.split("\n").foreach(x=>{
          list.+=((name,x))
        })
        list
      }).filter(x => x._2.contains("https://www.douban.com/people/") && x._2.contains("class=\"\""))
      .map(x=>{
        val x_temp = x._2.replaceAll(" class=\"\"","")
        var uid = ""
        var name = ""
        val r = Pattern.compile("<a href=\"https://www.douban.com/people/([0-9a-zA-Z]+)/\">([^<]+)</a>")
        val m = r.matcher(x_temp)
        if(m.find){
          uid = m.group(1)
          name = m.group(2)
        }
        ((uid,name),x._1)
      }).groupByKey().map(x=>{
      val title = new Array[Int](19)
      val it = x._2.iterator.toList.distinct
      if(it.contains("214124351")){title(0)=1;}
      if(it.contains("204692485")){title(1)=1;}
      if(it.contains("211099365")){title(2)=1;}
      if(it.contains("203393690")){title(3)=1;}
      if(it.contains("196147199")){title(4)=1;}
      if(it.contains("187427707")){title(5)=1;}
      if(it.contains("213954663")){title(6)=1;}
      if(it.contains("214384494")){title(7)=1;}
      if(it.contains("212498231")){title(8)=1;}
      if(it.contains("210237023")){title(9)=1;}
      if(it.contains("211211159")){title(10)=1;}
      if(it.contains("210247631")){title(11)=1;}
      if(it.contains("204740976")){title(12)=1;}
      if(it.contains("204790660")){title(13)=1;}
      if(it.contains("203438242")){title(14)=1;}
      if(it.contains("205351919")){title(15)=1;}
      if(it.contains("214457944")){title(16)=1;}
      if(it.contains("214604466")){title(17)=1;}
      if(it.contains("214353022")){title(18)=1;}
      //x._1._1+"\t"+x._1._2+"\t"+util.Arrays.toString(title)
      (x._1._1,x._1._2,title,it)
    }).filter(x=>x._4.length>1)
      .map(x=>{
        x._1+"\t"+x._2+"\t"+util.Arrays.toString(x._3)
      }).foreach(println)

  }

}
