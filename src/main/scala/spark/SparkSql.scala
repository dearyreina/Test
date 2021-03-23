package spark


import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.{DataFrame, SparkSession}

case class People(name:String,age:Long)
object SparkSql {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)
    val conf: SparkConf = new SparkConf().setMaster("local").setAppName("spark_sql")
    val spark: SparkSession = SparkSession.builder().config(conf).getOrCreate()
    import spark.implicits._
    import org.apache.spark.sql.functions._
    //val list = List(("li",20),("cai",25),("zhang",15),("li",40))
    //val df = spark.createDataFrame(list).toDF("name","age")
    //val df = spark.sparkContext.makeRDD(list).toDF("name","age")
    //val df: DataFrame = spark.read.format("json").option("timestampFormat","yyyy/MM/dd HH:mm:ss ZZ").load("input/dataframe/people.json")
    //val ds = Seq(People("li",20),People("cai",25),People("zhang",15)).toDS()
    val ds = spark.sparkContext.makeRDD(List(People("li",20),People("cai",25),People("zhang",15),People("li",40))).toDS()
    ds.createOrReplaceTempView("people")
    //spark.sql("select name,age+20 from people").show()
    //ds.filter($"age">20).groupBy($"name").sum("age") as "total_age"
    //val res = ds.filter($"age">=20).groupBy($"name").agg(("age","sum"),("age","avg")).select($"sum(age)" as "sum_age",$"avg(age)" as "avg_age").show()
    //val res = ds.filter($"age">=20).groupBy($"name").agg(sum("age").as("age_sum"),avg("age").as("age_avg")).orderBy($"age_avg".desc).show()
    val w = Window.partitionBy("name").orderBy($"age".desc)
    val res = ds.select($"name",$"age",row_number().over(w).as("number")).show()
    val res1: DataFrame = ds.join(ds, Seq("name"), "left").toDF("name", "age1", "age2").select("name", "age1")
    res1.rdd.map(row => {
      (row.get(0),row.get(1))
    }).foreach(println)
    spark.sql("select name,age,row_number() over(partition by name order by age desc) as number from people").show()
    //val ds = df.as[People]
    //ds.show()
    //df.createOrReplaceTempView("people")
    //df.show()
    spark.close()
  }

}
