import java.util.Date


import scala.collection.mutable.{ArrayBuffer, ListBuffer}

/**
 * Unit = java void
 * Any : 所有类的父类
 * AnyRef : Any的子类，引用类型的超类
 * AnyVal : Any的子类，值类型的超类
 * Nothing : 所有类的子类，表示没有值，Trait
 * Null : 唯一实例为null，AnyRef的子类，Trait
 * None : Option的两个子类之一（另一个是Some），安全函数返回值（集合）
 * Nil : 长度为0的List
 */

/**
 * 1、object相当于java中的单例，object中定义的全是静态的
 * 2、val：常量不可变 var：变量可变，val更利于资源回收
 * 3、变量和常量类型可以省略不写，会自动推断
 * 4、class类定义时可以传参，传参要指定类型，类似于默认构造函数；传参定义为val，可把传参变为公有参数；类中属性默认有getter和setter方法;类中重写构造方法，构造方法第一行必须先调用默认的构造
 * 5、新建class时，除了构造方法之外的其他方法不执行，其他都执行
 * 6、参数默认访问限制为public
 * 7、在同一个文件中class和object名称一样时：伴生类和伴生对象，可以互相访问私有变量
 * 8、调用object的构造方法，是调用其对应的apply方法，apply方法可重载
 */
/**
 * 1、方法使用return时方法体的返回值类型必须指定，没有return时，默认将方法体中最后一行计算的结果返回，自动推断方法返回类型
 * 2、方法传入参数必须指定类型
 * 3、方法体如果可以一行写完，花括号可以省略
 * 4、定义方法时如果没有方法名和方法体之间的“=”，则不会有结果返回，返回类型为unit
 * 5、方法返回类型指定为Unit时，返回结果为“()”，不会有结果返回
 */
/**
 * 1、递归方法要显示的指明方法的返回类型
 * 2、匿名函数样式 (入参)=>{方法体} 匿名函数入参在方法体中只使用一次，参数可以使用_代替，如果只使用一个参数，(_)也可以去掉
 */



class Person(xName:String,xAge:Int){
  val name = xName
  val age = xAge
  var gender = "man"
  println("************new class*************")
  def this(xName:String,xAge:Int,xGender:String){
    this(xName,xAge)
    this.gender = xGender
  }
}


object Learn {
  var title = "welcome"

  def apply(title : String)={
    this.title = title
    println(title)
  }

  def main(args: Array[String]): Unit = {
    /**
     * 类和对象
     */
    val person = new Person("lishujie", 26)
    println(s"My name is ${person.age}, I am ${person.name} years old")
    val person2 = new Person("lishujie", 26, "woman")
    println(s"${person2.gender} : My name is ${person2.age}, I am ${person2.name} years old")
    Learn("welcome to my party")
    println(Learn.title)

    /**
     * if else
     */
    val age = 20
    if (age < 20) {
      println("<20")
    } else if (age > 20) {
      println(">20")
    } else {
      println("=20")
    }

    /**
     * for
     */
    for (i <- 1 to 10) { //to 包括10
      println(i)
    }
    for (i <- 1 until 10) { //until不包括10
      println(i)
    }
    for (i <- 1 to(10, 2)) { //步长是2
      println(i)
    }

    for (i <- 1 to 9; if (i > 7); j <- 1 to 9; if (j < 5)) { //分号设置过滤条件
      println(s"$i-----$j")
    }

    val getJ = for (i <- 1 to 9; if (i > 7); j <- 1 to 9; if (j < 5)) yield j //yeild关键字获取for循环内变量的向量集合Vector
    val v = for (i <- 0 to 9 ; if (i > 2); j <- 0 to 5; if (j>i)) yield (i,j)
    println(getJ)

    /**
     * while
     * do while
     */

    var num = 0
    while (num < 3) {
      println(s"please……$num")
      num += 1
    }

    var num2 = 0
    do {
      println(s"please2……$num2")
      num2 += 1
    } while (num2 < 4)

    def max1(a: Int, b: Int): Int = {
      if (a > b)
        a
      else
        b
    }

    def max2(a: Int, b: Int) = if (a > b) a else b

    println(max1(3, 2))
    println(max2(3, 2))

    /**
     * 递归方法
     */

    def fun1(num: Int): Int = {
      if (num == 1) {
        1
      } else {
        num * fun1(num - 1)
      }
    }

    println(fun1(5))

    /**
     * 参数有默认值的方法
     */

    def fun2(a: Int = 100, b: Int = 200): Int = {
      a + b
    }

    println(fun2())
    println(fun2(200))
    println(fun2(b = 300))

    /**
     * 可变长参数的方法
     */

    def fun3(s: String*): Unit = {
      println(s)
      s.foreach(println)
    }

    fun3("a", "b", "c")

    /**
     * 匿名函数
     */

    val fun4 = (a: Int, b: Int) => { //匿名函数也可以命名 使用def定义也可以
      a + b
    }
    println(fun4(10, 20))


    /**
     * 嵌套方法
     */
    def fun5(num: Int): Int = {
      def fun6(num1: Int): Int = {
        val num2 = num1 + 2
        num2 //需要单独返回num2  谨记
      }

      fun6(num)
    }

    println(fun5(3))

    /**
     * 偏应用函数
     */

    def showLog(date: Date, s: String) = {
      println(s"$date is today,$s")
    }

    val date = new Date()

    def fun7 = showLog(date, _: String) //?

    fun7("hello")
    fun7("hello world")


    /**
     * 高阶函数
     */
    /**
     * 方法的参数是函数
     */
    def funA(a: Int, b: Int): Int = {
      a + b
    }

    def funB(f: (Int, Int) => Int, c: Int): Int = { //入参是方法
      val result = f(100, 200) + c
      result
    }

    println(funB(funA, 300))

    /**
     * 方法的返回是函数
     */

    def funC(a: Int, b: Int): (Int, Int) => Int = {
      def funD(c: Int, d: Int): Int = {
        a + b + c + d
      }

      funD //当不指定方法的返回类型是函数时，可以使用"funD _"来强制返回方法本身
    }

    println(funC(100, 200)(1, 2))

    /**
     * 方法的参数和返回都是函数
     */

    def funE(a: Int, b: Int, f: (Int, Int) => Int): (Int, Int) => String = {
      def funF(c: Int, d: Int): String = {
        s"${a + b},${f(c, d)}" //${} 可以调用任何表达式
      }

      funF
    }

    def funG(c: Int, d: Int): Int = {
      c * d
    }

    println(funE(100, 200, funG)(2, 6))

    /**
     * 柯里化函数
     */
    def fun11(a: Int, b: Int)(c: Int, d: Int): Int = {
      a + b + c + d
    }

    val fun12: (Int, Int) => Int = fun11(1, 2) //需要显示指定函数类型  否则使用fun11(1,2) _  这种形式也可以
    println(fun12(3, 4))


    /**
     * Array 数组
     */
    val arr = Array("a", "b", "c", "d") //没有new的是新建object 不写泛型可以自动推断
    arr.foreach(println)
    for (elem <- arr) {
      println(elem)
    }
    val arr2 = new Array[String](3) //3表示数据长度 写new是新建class
    arr2(0) = "a"
    arr2(1) = "b"
    arr2(2) = "c"
    arr2.foreach(println)

    val arr3 = new Array[Array[String]](2) //二维数组 限制的只是第一维的长度
    arr3(0) = Array("a1", "a2", "a3", "a4")
    arr3(1) = Array("b1", "b2", "b3")
    arr3.foreach(temp => temp.foreach(println))

    val arr4 = Array.ofDim[String](1, 2) //定长二维数组的定义方法
    arr4(0)(0) = "A1"
    arr4(0)(1) = "A2"
    arr4.foreach(temp => temp.foreach(println))

    val arr5 = Array(Array(1, 2, 3), Array(4, 5, 6, 7)) //不能省略二层Array，否则会认为是Array(Tuple)，foreach编译不通过
    arr5.foreach(temp => temp.foreach(println))

    val arr6 = Array.concat(arr5(0), arr5(1))
    arr6.foreach(println)

    val arr7 = Array.fill(5)("hello") //初始化一个值为hello的5个元素数组
    arr7.foreach(println)

    val arrbuf = ArrayBuffer[Int](1, 2, 3)
    //val arrbuf = scala.collection.mutable.ArrayBuffer[Int](1,2,3) //Array是不可变长数组 ArrayBuffer是其可变长形式 mutable：可变 immutable：不可变
    arrbuf.+=(4) //尾部追加4，注意arrbuf后有. ，+=是函数
    arrbuf.+=:(0) //头部添加0
    arrbuf.+=(5, 6, 7) //尾部追加多个数字
    arrbuf.foreach(println)

    /**
     * List
     */
    //val list = List(1,2,"a") 也可以编译通过，泛型是Any
    val list = List("a", "b", "c")
    list.map("hello:" + _).foreach(println)
    list.map("hello:" + _).filter(_.equals("hello:c")).foreach(println)
    list.map("hello:" + _).map(_.split(" ")).foreach(elem => elem.foreach(print)) //map返回多个数组
    list.map("hello:" + _).flatMap(_.split(" ")).foreach(println) //flatmap打平返回多个数组

    val listbuf = ListBuffer[String]("A", "B", "B", "c") //可变长list
    listbuf.+=("D")
    listbuf.+=:("S")
    listbuf.-=("B") //只能减去一个B
    listbuf.foreach(println)

    /**
     * Set
     */
    val set = Set[Int](1, 2, 3, 4, 4, 5) //默认不可变
    set.foreach(println)
    val set1 = Set[Int](4, 5, 6)
    println("**********")
    set.intersect(set1).foreach(println) //交集
    println("**********")
    (set & set1).foreach(println) //交集 操作符
    println("**********")
    set.diff(set1).foreach(println) //差集
    println("**********")
    (set &~ set1).foreach(println) //差集 操作符
    println("**********")
    set1.diff(set).foreach(println)
    println("**********")
    (set1 &~ set).foreach(println)
    println("**********")
    set.filter(_ > 2).foreach(println) //过滤
    println("**********->")
    val mset = scala.collection.mutable.Set[Int](1, 2, 3) //可变长set
    mset.+=(100)
    mset.foreach(println)
    println("**********")
    val set2 = set.+(100) //不可变长set需要赋值给新set
    set2.foreach(println)
    println("**********")

    /**
     * Map
     */
    val map = Map[String, Int]("a" -> 1, "b" -> 2, ("c", 3))
    println(map.get("a").get) //Some(1)
    println(map.get("A")) //None
    println(map.getOrElse("a", 100)) //返回值 而不是Some(1)
    println(map.getOrElse("A", 100)) //返回值

    val keys = map.keys
    keys.foreach(key => {
      println(key + " " + map.get(key).get)
    })
    val values = map.values
    values.foreach(println)
    println("**********")
    val map2 = Map[String, Int]("d" -> 4, ("e", 5), ("a", 100))
    val map3 = map.++(map2) //2覆盖1
    println(map3)
    val map4 = map.++:(map2) //1覆盖2
    println(map4)
    val mmap = scala.collection.mutable.Map[String, Int]("A" -> 1, "B" -> 2) //可变长map
    mmap.put("C", 3) //可变长map才能执行put
    val mmap2 = mmap.filter(elem => { //filter操作需要赋值给新map
      elem._2 > 1 //map的元素相当于tuple元祖，_1,_2可取出key value
    })
    println(mmap2)

    /**
     * Tuple
     */
    val tuple = Tuple1(1)
    val tuple1 = new Tuple1[String]("A") //可new可不new
    val tuple2 = Tuple2("a", true)
    val tuple3 = Tuple3(1, 3.4, "b") //tuple的每个元素类型可以不一样
    val tuple6 = (1, 2, 3, "a", "cc", true) //可以直接用括号新建tuple，容量最大到22，使用_1、_2、_3等来提取每个元素
    tuple6.productIterator.foreach(println) //tuple的迭代方法
    println(tuple2.swap) //只有tuple2有swap函数来调换key value

    /**
     * Trait 相当于java中接口和抽象类的整合
     */
    val h = new Human
    h.read("lishujie")
    h.listen("caiyongbin")

    val p1 = new Point(1, 1)
    val p2 = new Point(2, 2)
    println(p1.isEqual(p2))
    println(p1.isNotEqual(p2))

    /**
     * Match
     */
    val tp = (1, "a", "bb", true, 200, 5.6, 1.0) //模式匹配会存在数值转换，1.0会转换为1
    def matchUtil(o: Any) = {
      val result = o match { //从上往下匹配 匹配到了自动跳出
        case 1 => "1"
        case i: Int => s"int:$i"
        case s: String => s"string:$s"
        case _ => "no match" //_表示没有匹配到
      }
      println(result)
    }

    tp.productIterator.foreach(matchUtil)

    /**
     * 偏函数PartialFunction
     */
    def match2: PartialFunction[Any, Int] = { //偏函数关键字 PartialFunction 没有括号入参 与模式匹配一起使用
      case "a" => 1
      case "bb" => 2
      case _ => 100
    }

    println(match2(true))

    /**
     * 样例类 case
     */
    case class PersonCase(name: String, age: Int) { //没有其他定义的情况下 花括号可以直接去掉

    }
    val p = new PersonCase("lishujie", 26)
    println(s"${p.name} : ${p.age} ----$p -----${p.hashCode()}") //样例类会自动实现构造函数入参的getter、setter方法、toString和HashCode方法
    val q = PersonCase("caiyongbin", 28)
    println(s"${q.name} : ${q.age}")

    /**
     * 隐式转换 ：当没有显示指定值、方法、类等，会在作用域里自动寻找匹配 implicit
     */
    //隐式转换参数和函数
    def read(implicit str: String): Unit = {
      println(s"read:$str")
    }

    implicit val title = "读一下"
    read //隐式转换参数和隐式转换函数是配套使用的，同一作用域内不能有同一类型的隐式转换参数，如果隐式转换函数有多个入参，使用柯里化的方法，如def read(age:Int)(implicit str:String)
    //隐式转换函数
    class Li(family: String) {
      val xfamily = family
      def sayFirstName()={
        println(s"my family name is ${this.xfamily}")
      }
    }
    class Lishujie(family: String,name:String){
      val xfamily = family
      val xname = name
    }
    implicit def nameToFamily(o:Lishujie):Li={  //隐式转换方法，入参A类型，返回B类型，那么A类型对象可以使用B类型的方法或者参数
      new Li(o.xfamily)
    }
    val lishujie = new Lishujie("li","shujie")
    lishujie.sayFirstName()
    implicit class Family(o:Lishujie){ //隐式转换类，B类的构造方法入参为A类型，那么A类型对象可以调用B类的方法或者参数
      def sayName()={
        println(s"my family name is ${o.xfamily} ${o.xname}")
      }
    }
    lishujie.sayName() //注意，隐式转换函数和隐式转换类在同一作用域内也会冲突
  }
  trait Read{ //trait不能传参
    def read(str:String): Unit ={
      println(s"read:$str……")
    }
  }
  trait Listen{
    def listen(str:String): Unit ={
      println(s"listen:$str……")
    }
  }
  class Human extends Read with Listen { //继承两个trait时，第一个使用extends，剩下的使用with
  }

  trait Utils{
    def isEqual(o:Any):Boolean //没有实现的方法在继承trait时需要被实现
    def isNotEqual(o:Any):Boolean = !isEqual(o)
  }
  class Point(x:Int,y:Int) extends Utils {
    val xx = x
    val yy = y

    override def isEqual(o: Any): Boolean = { //需要实现trait中没有实现的方法
      o.isInstanceOf[Point] && o.asInstanceOf[Point].xx == this.xx
    }
  }

}






