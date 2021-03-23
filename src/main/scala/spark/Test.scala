package spark

import java.util.regex.Pattern

import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.log4j._

import scala.collection.mutable.ListBuffer
import scala.util.matching.UnanchoredRegex

object Test {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)
    val sparkConf = new SparkConf().setMaster("local").setAppName("wb")
    val sc = new SparkContext(sparkConf)
    val fs = FileSystem.get(sc.hadoopConfiguration)
    fs.deleteOnExit(new Path("output/wb3"))
    fs.deleteOnExit(new Path("output/wb4"))
    fs.close()
    val rdd1 = sc.textFile("D:\\test3",1)
    val rdd2 = rdd1.flatMap(x=>x.split("\\\\n")).filter(x=>x.contains("usercard"))
      .map(x=>{
        //println(x)
        //val r = Pattern.compile("usercard=\"id=([0-9]+)\">([^<]+)</a>")
        val r = Pattern.compile("usercard=\\\\\"id=([0-9]+)\\\\\"")
        val m = r.matcher(x)
        var userid = ""
        var name = ""
        var tiefen = 0
        var v = 0
        var rv = 0
        if(m.find){
          userid = m.group(1)
          //name = m.group(2)
        }
        if(x.contains("微博铁粉")){
          tiefen =1
        }
        if(x.contains("W_icon icon_approve")){
          v=1
        }
        if(x.contains("W_icon icon_approve_gold")){
          rv=1
        }
        (userid,(name,tiefen,v,rv))
      }).filter(x=>x._1.length>2).groupByKey(1).map(x=>{
      (x._1,x._2.iterator.next())
    }).map(x=>{
      x._1+"\t"+x._2._1+"\t"+x._2._2+"\t"+x._2._3+"\t"+x._2._4
    })

    rdd2.saveAsTextFile("output/wb3")


//    val rdd2 = rdd1.flatMap(x=>{
//      val listbuf = ListBuffer[String]()
//      val r = Pattern.compile("usercard=\"id=([0-9]+)\"")
//      val m = r.matcher(x)
//      while(m.find){
//        listbuf.+=(m.group(1))
//      }
//      listbuf
//    })
//    rdd2.map(x=>(x,1)).reduceByKey(_+_).map(x=>(x._2,x._1)).sortByKey(false).saveAsTextFile("output/wb2")
//
//
//    rdd2.saveAsTextFile("output/wb")



  }

}
