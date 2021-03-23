package spark

import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable

object SortJob {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)
    val conf = new SparkConf().setMaster("local").setAppName("SortTest")
    val sc = new SparkContext(conf)
//    val fs = FileSystem.get(sc.hadoopConfiguration)
//    fs.deleteOnExit(new Path("output/sparkoutputsplit"))
//    fs.close()
    val rdd0 = sc.textFile("input/sparkinputsplit",3)
    val rdd1 = rdd0.map(x=>{
      val arr = x.split("\t")
      (arr(0),arr(1).toLong)
    })
    rdd1.groupByKey().map(x =>{
      val it = x._2.iterator
      val tree = new mutable.TreeSet[Long]()
      while(it.hasNext){
        tree.add(it.next())
        if(tree.size > 10){
          tree.remove(tree.firstKey)
        }
      }
      (x._1,tree.toList.reverse)
    }).foreach(println)

    rdd1.groupByKey().map(x => {
      val list = x._2.iterator.toList.sortBy(y=>y)(Ordering.Long.reverse)
      (x._1,list.take(10))
    }).foreach(println)

  }

}
