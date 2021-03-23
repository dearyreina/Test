package spark

object ScalaTest {
  def main(args: Array[String]): Unit = {
    import scala.collection.mutable.Map
    val map1 = Map(("a",5),("b",2),("c",3))
    val map2 = Map(("a",2),("b",1),("c",6))

    val foldmap = map1.foldLeft(map2)(
      (innermap,kv) => {
        val v = if(innermap.getOrElse(kv._1,0) > kv._2) innermap.getOrElse(kv._1,0) else kv._2
        innermap(kv._1)=v
        innermap
      }
    )
    foldmap.foreach(println)

    val v = for(i <- 0 to 9 ; if(i > 2);j <- 0 to 5; if(j>i)) yield (i,j)
    println(v)
  }

}
