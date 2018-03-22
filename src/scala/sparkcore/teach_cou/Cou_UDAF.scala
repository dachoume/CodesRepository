package testcou

import org.apache.spark.sql.Encoder
import org.apache.spark.sql.Encoders
import org.apache.spark.sql.expressions.Aggregator

/**
  * Created by 56308 on 2017/11/29.
  */

case class Employee(single:String)
case class Average(var buffer: String)

object Cou_UDAF extends Aggregator[Employee,Average,String]{
  override def zero: Average = Average("")

  override def reduce(b: Average, a: Employee): Average = {
    b.buffer=b.buffer+","+a.single
    b
  }

  override def merge(b1: Average, b2: Average): Average = {
    b1.buffer=b1.buffer+","+b2.buffer
    b1
  }

  override def finish(reduction: Average): String = reduction.buffer

  override def bufferEncoder: Encoder[Average] = Encoders.product

  override def outputEncoder: Encoder[String] = Encoders.STRING
}
