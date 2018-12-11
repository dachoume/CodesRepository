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
=======================人工分割线=======================
object MySQLPoolManagerTest {
    def main(args: Array[String]): Unit = {
        val conn: Connection = MySQLPoolManager.getMysqlPoolManager.getConnection
        val ps: PreparedStatement = conn.prepareStatement("select * from sex_count")
        val rs: ResultSet = ps.executeQuery()
        while (rs.next()) {
            println(rs.getString("sex"))
            println(rs.getInt("count"))
        }

        MySQLPoolManager.getMysqlPoolManager.closeSource(ps, conn)

        //下面的测试需要把cpds的private去掉
        //为了证明使用这种方法可以创建出线程安全的单例
        //cpds才是单例的
        //    val s1 = MySQLPoolManager.getMysqlPoolManager.cpds
        //    val s2 = MySQLPoolManager.getMysqlPoolManager.cpds
        //    println(s1)
        //    println(s2)
        //    println(s1==s2)
        //    println(s1.equals(s2))
        //
        //    val t1 = new MySQLPoolManager
        //    val t2 = new MySQLPoolManager
        //    println(t1.cpds==t2.cpds)
        //    println(t1.cpds.equals(t2.cpds))
    }

}
