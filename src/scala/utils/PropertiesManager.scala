package utils

import java.util.Properties
import java.io.{BufferedInputStream, FileInputStream, InputStream}

class PropertiesManager extends Serializable {
    private val properties: Properties = new Properties()

    def getProperties(filename: String, options: String): String = {
        var in = None: Option[InputStream] //初始化一个为None的流，使用Some
        try {
            in = Some(this.getClass().getClassLoader.getResourceAsStream(filename)) //文件要放到resources文件夹下
            properties.load(in.get)
            properties.getProperty(options) //读取键为options的数据的值
        } catch {
            case e: Exception => "fileerror" + "   " + e.getMessage
        } finally {
            if (in.isDefined) {
                if (in.get != null) {
                    try {
                        in.get.close()
                    } catch {
                        case e: Exception => "fileerror" + "   " + e.getMessage
                    }
                }
            }

        }

    }

    def getPropertiesByPath(filePath: String, options: String): String = {
        var in = None: Option[InputStream] //初始化一个为None的流，使用Some
        try {
            in = Some(new BufferedInputStream(new FileInputStream(filePath))) //文件要放到resources文件夹下
            properties.load(in.get)
            properties.getProperty(options) //读取键为options的数据的值
        } catch {
            case e: Exception => "fileerror" + "   " + e.getMessage
        } finally {
            if (in.isDefined) {
                if (in.get != null) {
                    try {
                        in.get.close()
                    } catch {
                        case e: Exception => "fileerror" + "   " + e.getMessage
                    }
                }
            }
        }

    }

}

object PropertiesManager {
    var propertiesManager: PropertiesManager = _

    def getPropertiesManager: PropertiesManager = {
        synchronized {
            if (propertiesManager == null) {
                propertiesManager = new PropertiesManager
            }
        }
        propertiesManager
    }


}
======================人工分割线======================
println(PropertiesManager.getPropertiesManager.getPropertiesByPath("/Users/dxdou/DDWorking/test.properties", "a"))
println(PropertiesManager.getPropertiesManager.getProperties("hbase.properties", "hbase.zookeeper.quorum"))
