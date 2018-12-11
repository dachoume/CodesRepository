package utils

import java.sql.{Connection, SQLException, Statement}

import com.mchange.v2.c3p0.ComboPooledDataSource

class MySQLPoolManager extends Serializable {
    private val cpds = new ComboPooledDataSource(true)
    try {
        cpds.setJdbcUrl(PropertiesManager.getPropertiesManager.getProperties("c3p0.properties", "jdbcUrl"))
        cpds.setDriverClass(PropertiesManager.getPropertiesManager.getProperties("c3p0.properties", "driverClass"))
        cpds.setUser(PropertiesManager.getPropertiesManager.getProperties("c3p0.properties", "user"))
        cpds.setPassword(PropertiesManager.getPropertiesManager.getProperties("c3p0.properties", "password"))
    } catch {
        case e: Exception => e.printStackTrace()
    }

    def getConnection: Connection = {
        try {
            cpds.getConnection
        } catch {
            case ex: Exception =>
                ex.printStackTrace()
                null
        }
    }

    def closeSource(stat: Statement, conn: Connection): Unit = {
        if (stat != null) {
            try {
                stat.close()
            } catch {
                case e: SQLException => e.printStackTrace
            }
        }

        if (conn != null) {
            try {
                conn.close()
            } catch {
                case e: SQLException => e.printStackTrace
            }
        }
    }

    //只关闭连接
    def closeConn(conn: Connection): Unit = {
        if (conn != null) {
            try {
                conn.close()
            } catch {
                case e: SQLException => e.printStackTrace
            }
        }
    }

    //只关闭statment
    def closeStat(stat: Statement): Unit = {
        if (stat != null) {
            try {
                stat.close()
            } catch {
                case e: SQLException => e.printStackTrace
            }
        }
    }
}


object MySQLPoolManager {
    var mySQLPoolManager: MySQLPoolManager = _

    def getMysqlPoolManager: MySQLPoolManager = {
        synchronized {
            if (mySQLPoolManager == null) {
                mySQLPoolManager = new MySQLPoolManager
            }
        }
        mySQLPoolManager
    }
}
