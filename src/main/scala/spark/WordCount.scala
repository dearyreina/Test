package spark

import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}

object WordCount {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local").setAppName("WordCount")
    val sc = new SparkContext(conf)
    Logger.getLogger("org").setLevel(Level.ERROR)
    val rdd1 = sc.makeRDD(List("There are moments in life when you miss someone so much that you just want to pick them from your dreams and hug them for real","Dream what you want to dream","go where you want to go","be what you want to be,because you have only one life and one chance to do all the things you want to do"))
    rdd1.flatMap(x => x.split(" "))
      .map((_,1))
      .reduceByKey(_+_).foreach(println)

    println("------------------------------------")

    rdd1.flatMap(_.split(" "))
        .map((_,1))
        .groupByKey()
        .map(x => {
          val it = x._2
          (x._1,it.size)
        }).foreach(println)

    println("------------------------------------")

    rdd1.flatMap(_.split(" "))
      .groupBy(x => x)
      .map(x => {
        (x._1,x._2.size)
      }).foreach(println)

    println("------------------------------------")
    println(rdd1.flatMap(_.split(" "))
        .map(x => (x,1))
        .reduce((x,y) => (x._1,x._2+y._2))) //这种做法是错的！

    println("------------------------------------")

    val rdd2 = sc.makeRDD(List(("a",10),("b",40),("c",50),("a",19),("b",30),("c",22),("a",15),("b",10),("c",31),("c",23)),3)
    rdd2.combineByKey(
      v => (v,1),
      (c:(Int,Int),v) => (c._1+v,c._2+1),
      (c1:(Int,Int),c2:(Int,Int)) => (c1._1+c2._1,c1._2+c2._2)
    ).foreach(println)

    println("------------------------------------")

    rdd2.map(x => (x._1,(x._2,1))).reduceByKey((x,y) => (x._1+y._1,x._2+y._2)).foreach(println)

    println("------------------------------------")

    rdd2.map(x => (x._1,(x._2,1))).aggregateByKey((0,0))(
      (x:(Int,Int),y:(Int,Int)) => (x._1+y._1,x._2+y._2),
      (x:(Int,Int),y:(Int,Int)) => (x._1+y._1,x._2+y._2)
    ).foreach(println)

    println("------------------------------------")

    rdd2.map(x => (x._1,(x._2,1))).foldByKey((0,0))(
      (x:(Int,Int),y:(Int,Int)) => (x._1+y._1,x._2+y._2)
    ).foreach(println)

    println("------------------------------------")

    val rdd_A = sc.makeRDD(List(("1","a"),("2","b"),("3","c"),("4","b"),("4","c")))
    val rdd_B = sc.makeRDD(List(("1","A"),("2","B"),("3","C"),("3","C"),("3","C")))

    rdd_A.join(rdd_B).foreach(println)

    println("------------------------------------")

    rdd_A.leftOuterJoin(rdd_B).foreach(println)

    println("------------------------------------")

    rdd_A.cogroup(rdd_B).foreach(
      x =>{
        val key = x._1
        val lA = x._2._1.mkString(",")
        val lB = x._2._2.mkString(",")
        println(s"$key - ($lA|$lB)")
      }
    )

    println("------------------------------------")
    val rdd3 = sc.makeRDD(List(("a",10),("a",20),("a",40),("a",10),("a",15),("a",10)),3)

    rdd3.map(x => (x._1,(x._2,1))).foldByKey((10,0))(
      (x:(Int,Int),y:(Int,Int)) => (x._1+y._1,x._2+y._2)
    ).foreach(println)

    println("------------------------------------")
    val rdd4 = sc.makeRDD(List(10,20,40,10,15,10),3)
    val result = rdd4.fold(10)( //fold的zerovalue在分区内和分区间都会使用，但是foldbykey只有分区内会使用
      (a:Int,b:Int) => a+b
    )
    println(result)

  }

}
