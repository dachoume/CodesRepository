package testcou

import org.apache.spark.sql.expressions.MutableAggregationBuffer
import org.apache.spark.sql.expressions.UserDefinedAggregateFunction
import org.apache.spark.sql.types._
import org.apache.spark.sql.Row
import org.apache.spark.sql.SparkSession
/**
  * Created by 56308 on 2017/11/29.
  */
object Cou_UDAF2 extends UserDefinedAggregateFunction{
  override def inputSchema: StructType = StructType(StructField("inputColumn", StringType) :: Nil)

  override def bufferSchema: StructType = StructType(StructField("inputColumn", StringType) :: Nil)

  override def dataType: DataType = StringType

  override def deterministic: Boolean = true

  override def initialize(buffer: MutableAggregationBuffer): Unit = {
    buffer(0)=""
  }

  override def update(buffer: MutableAggregationBuffer, input: Row): Unit = {
    if (!input.isNullAt(0)) {
      buffer(0)=buffer.getString(0)+" "+input.getString(0)
    }
  }

  override def merge(buffer1: MutableAggregationBuffer, buffer2: Row): Unit = {
    buffer1(0)=buffer1(0)+" "+buffer2(0)
  }

  override def evaluate(buffer: Row): Any = buffer.getString(0).toString.trim
}
