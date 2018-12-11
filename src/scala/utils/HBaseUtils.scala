package utils

import org.apache.hadoop.hbase.TableName
import org.apache.hadoop.hbase.client.{Connection, Delete, Put, Table}
import org.apache.hadoop.hbase.util.Bytes

import scala.collection.mutable.ArrayBuffer

object HBaseUtils extends Serializable {

    /**
      * 使用putlist插入数据，默认时间戳
      * 需要先创建list，如val list = ArrayBuffer[Put]
      * 需要创建table连接，如Table table=connection.getTable(tableName);
      * 调用该方法后需要使用table将该list，put进去,  table.put(list.asJava)
      *
      * @param list   ArrayBuffer
      * @param rowkey 行键
      * @param family 列簇
      * @param column 列名
      * @param value  值
      */
    def putListData(list: ArrayBuffer[Put], rowkey: String, family: String, column: String, value: String): Unit = {
        val put = new Put(Bytes.toBytes(rowkey))
        put.addColumn(family.getBytes, column.getBytes, value.getBytes)
        list += put
    }


    /**
      * 删除某条记录
      *
      * @param connection HBase数据库连接
      * @param tablename  表名
      * @param family     列簇
      * @param column     列名
      * @param key        RowKey
      */
    def deleteRecord(connection: Connection, tablename: String, family: String, column: String, key: String): Unit = {
        var table: Table = null
        try {
            val userTable = TableName.valueOf(tablename)
            table = connection.getTable(userTable)
            val d = new Delete(key.getBytes())
            d.addColumn(family.getBytes(), column.getBytes())
            table.delete(d)
            println("delete record done.")
        } finally {
            if (table != null) table.close()
            connection.close()
        }
    }

}
