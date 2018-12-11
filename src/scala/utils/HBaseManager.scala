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
