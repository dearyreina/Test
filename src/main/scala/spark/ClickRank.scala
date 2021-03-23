package spark

import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.sql.expressions.{MutableAggregationBuffer, UserDefinedAggregateFunction}
import org.apache.spark.sql.types.{DataType, LongType, MapType, StringType, StructField, StructType}
import org.apache.spark.sql.{Dataset, Row, SparkSession}

case class ClickData(sloc1:String,scate2:String)

class ClickRankStr extends UserDefinedAggregateFunction{
  override def inputSchema: StructType = {
    StructType(List(StructField("sloc1",StringType)))
  }

  override def bufferSchema: StructType = {
    StructType(List(StructField("totalcount",LongType),StructField("countmap",MapType(StringType,LongType))))
  }

  override def dataType: DataType = {
    //StructType(List(StructField("str",StringType)))
    StringType
  }

  override def deterministic: Boolean = true

  override def initialize(buffer: MutableAggregationBuffer): Unit = {
    buffer(0) = 0l
    buffer(1) = Map[String,Long]()
    //buffer.update(1,Map[String,Long]())
  }

  override def update(buffer: MutableAggregationBuffer, input: Row): Unit = {
    buffer(0) =  buffer.getLong(0) + 1
    val sloc1 = input.getString(0)
    val map = buffer.getMap[String,Long](1)
    buffer(1)=map.updated(sloc1,map.getOrElse(sloc1,0l)+1)
  }

  override def merge(buffer1: MutableAggregationBuffer, buffer2: Row): Unit = {
    val map1 = buffer1.getMap[String,Long](1)
    val map2 = buffer2.getMap[String,Long](1)
    buffer1(1) = map1.foldLeft(map2){
      case(map,(k,v)) => {
        map.updated(k,map.getOrElse(k,0l)+v)
      }
    }
    buffer1(0) = buffer1.getLong(0)+buffer2.getLong(0)
  }

  override def evaluate(buffer: Row): Any = {
    val totalcount = buffer.getLong(0)
    val map = buffer.getMap[String,Long](1)
    val list = map.toList.sortWith((left,right) => left._2>right._2).take(2)
    val sb = new StringBuilder
    var topcount = 0l
    if(list.size >=2){
      sb.append(list(0)._1+":"+list(0)._2*100/totalcount+"%").append(",").append(list(1)._1+":"+list(1)._2*100/totalcount+"%")
      topcount = list(0)._2+list(1)._2
    }else{
      sb.append(list(0)._1+":"+list(0)._2*100/totalcount+"%")
      topcount = list(0)._2
    }
    if(totalcount>topcount){
      sb.append(",").append((totalcount-topcount)*100/totalcount+"%")
    }
    sb.toString()
  }
}

object ClickRank {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)
    val conf: SparkConf = new SparkConf().setMaster("local").setAppName("ClickRank")
    val spark: SparkSession = SparkSession.builder().config(conf).getOrCreate()
    val system: FileSystem = FileSystem.get(spark.sparkContext.hadoopConfiguration)
    system.deleteOnExit(new Path("output/clickrank/"))
    system.close()

    import spark.implicits._

    val ds1: Dataset[ClickData] = spark.read.textFile("input/sparksql").map(x=> x.split("\t")).map(x=>ClickData(x(0),x(1))).as[ClickData]
    ds1.createOrReplaceTempView("click")
    ds1.show()
    val clickrank = new ClickRankStr
    spark.udf.register("clickrank",clickrank)

    //spark.sql("select scate2,sloc1 from click").show()
    spark.sql("select scate2,clickrank(sloc1) from click group by scate2").show()

    spark.stop()



  }

}
