package spark


import java.text.SimpleDateFormat

import com.bj58.opt.adlogparser.roi.RoiClick
import org.apache.commons.lang.StringUtils
import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.spark.{SparkConf, SparkContext}
import util.{CommonUtil, SearchFilter}

import scala.util.DateUtil

object RoiTest {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local").setAppName("roitest")
    val sc = new SparkContext(conf)
    val fs = FileSystem.get(sc.hadoopConfiguration)
    fs.deleteOnExit(new Path("output/roitest/"))
    fs.close()
    val rdd1 = sc.textFile("input/roitest/")
    rdd1.map(record=>{
      val entity = new RoiClick
      try {
        entity.parseLog_new(record, entity)
      } catch {
        case e: Exception => null
      }
    }).filter(_ != null)
      .filter(x => x.getCatepath.startsWith("9224") || x.getCatepath.startsWith("13941"))
      .filter(x => "backdetailview".equals(x.getClickTag))
      .filter(x => !SearchFilter.isSpider(x.getHttp_user_agent))
      .filter(x => !StringUtils.isEmpty(x.getInfoid) && !StringUtils.equals("0",x.getInfoid) && StringUtils.isNumeric(x.getInfoid))
      .filter(x => !StringUtils.isEmpty(x.getUserid) && !StringUtils.equals("0",x.getUserid) && StringUtils.isNumeric(x.getUserid))
      .filter(x => !StringUtils.isEmpty(x.getInfouserid) && !StringUtils.equals("0",x.getInfouserid) && StringUtils.isNumeric(x.getInfouserid))
      .map(x =>{
        val sdf: SimpleDateFormat = new SimpleDateFormat("yyyyMMdd")
        val dt = DateUtil.getLong2DateFunc(x.getMsec.toString, sdf)
        val cate1 = x.getCatepath.split(",")(0)
        val params = x.getParams;
        var infoType = CommonUtil.getInfoTypeJsonValue(params,cate1);
        if(StringUtils.isEmpty(infoType) || "null".equals(infoType)){
          infoType="5"
        }
        val infoTypeArr = infoType.split(",")
        var tempType = ""
        var isbiz = false
        for(tempType <- infoTypeArr){
          if(StringUtils.isNumeric(tempType) && !tempType.equals("5")){
            isbiz = true
          }
        }
        val sb = new StringBuilder
        var catetype = "0"
        if("13941".equals(cate1)){
          catetype = "1"
        }
        sb.append("paychat").append("_").append(x.getInfouserid).append("_").append(dt)
          .append("\t")
          .append(x.getUserid).append("_").append(x.getInfoid).append("_").append(x.getMsec).append("_").append(infoType).append("_").append(catetype)
        sb.toString()
      }).saveAsTextFile("output/roitest/")
  }

}
