package utils

import org.apache.hadoop.hbase.{HBaseConfiguration, HConstants}
import org.apache.hadoop.hbase.client.ConnectionFactory
import org.apache.hadoop.hbase.client.Connection

class HBaseManager extends Serializable {
    private val conf = HBaseConfiguration.create()
    conf.set("hbase.zookeeper.quorum", PropertiesManager.getPropertiesManager.getProperties("hbase.properties", "hbase.zookeeper.quorum"))
    conf.set("hbase.zookeeper.property.clientPort", PropertiesManager.getPropertiesManager.getProperties("hbase.properties", "hbase.zookeeper.property.clientPort"))
    conf.set("hbase.znode.parent", "/hbase")

    def getConnection: Connection = {
        try {
            ConnectionFactory.createConnection(conf)
        } catch {
            case ex: Exception =>
                ex.printStackTrace()
                null
        }
    }
}

object HBaseManager {
    var hbaseManager: HBaseManager = _

    def getHBaseManager: HBaseManager = {
        synchronized {
            if (hbaseManager == null) {
                hbaseManager = new HBaseManager
            }
        }
        hbaseManager
    }
}

=======================人工分割线=======================
/**
  * 测试用HBaseManager创建连接，并从HBase中取得单条信息
  */
object HBaseManagerTest {
    def main(args: Array[String]): Unit = {
        val conn: Connection = HBaseManager.getHBaseManager.getConnection
        println("创建了连接")
        try {
            //获取 user 表
            val table = conn.getTable(TableName.valueOf("test1"))
            println(table)
            println("获取了表")
            try {

                //查询某条数据
                val g: Get = new Get("4".getBytes)
                println(g)
                val result = table.get(g)
                println(result)
                val value = Bytes.toString(result.getValue("cf1".getBytes, "age".getBytes))
                println("4 :" + value)


            } finally {
                if (table != null) table.close()
            }
        } finally {
            conn.close()
        }



        /*        def getAResult(connection: Connection, tablename: String, family: String, column: String, key: String): Unit = {
                    var table: Table = null
                    try {
                        val userTable: TableName = TableName.valueOf(tablename)
                        println(userTable)
                        table = connection.getTable(userTable)
                        val g = new Get(key.getBytes())
                        val result = table.get(g)
                        println(result)
                        val value = Bytes.toString(result.getValue(family.getBytes(), column.getBytes()))
                        println("key:" + value)
                    } finally {
                        if (table != null) table.close()

                    }

                }
                getAResult(conn,"test1","cf1","name",4.toString)*/
    }

}
