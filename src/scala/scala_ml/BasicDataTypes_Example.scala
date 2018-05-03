package ml_study

import org.apache.log4j.{Level, Logger}
import org.apache.spark.ml.feature.LabeledPoint
import org.apache.spark.ml.linalg.{Matrices, Matrix, Vectors}
import org.apache.spark.mllib.linalg.distributed.{CoordinateMatrix, MatrixEntry}
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.SparkSession

object BasicDataTypes_Example {
    def main(args: Array[String]): Unit = {
        Logger.getLogger("org.apache.spark").setLevel(Level.WARN)

        val spark = SparkSession
          .builder
          .appName("CorrelationExample")
          .getOrCreate()
        import spark.implicits._
        val sc = spark.sparkContext

        //向量
        println("向量")
        val data = Seq(
            Vectors.sparse(4, Seq((0, 1.0), (3, -2.0))), //第一个4表示该向量有4维，第二个序列中第一个元组表示在0位置的值为1.0，第二个元组表示在3位置值为-2.0；其余位置为0
            Vectors.dense(4.0, 5.0, 0.0, 3.0), //向量的稠密表示方法使用dense
            Vectors.dense(6.0, 7.0, 0.0, 8.0),
            Vectors.sparse(4, Seq((0, 9.0), (3, 1.0))) //和第一个向量表示方法一致，为向量的稀疏表示，用sparse
        )

        val df: DataFrame = data.map(Tuple1.apply).toDF("features")
        df.show()

        //带标签的稀疏向量
        println("带标签的稀疏向量")
        val neg = LabeledPoint(0.0, Vectors.sparse(3, Array(0, 2), Array(2.0, 8.0)))
        println(neg)

        //本地矩阵
        println("本地矩阵")
        val sm: Matrix = Matrices.sparse(3, 2, Array(0, 1, 3), Array(0, 2, 1), Array(9, 6, 8))
        println(sm)

        //坐标矩阵
        println("坐标矩阵")
        val ent1 = new MatrixEntry(0, 1, 0.5)
        val ent2 = new MatrixEntry(2, 2, 1.8)
        val entries: RDD[MatrixEntry] = sc.parallelize(Array(ent1, ent2))
        val coordMat: CoordinateMatrix = new CoordinateMatrix(entries)
        coordMat.entries.foreach(println)

        spark.stop()
    }

}
========================================人工分割线========================================
向量
+--------------------+
|            features|
+--------------------+
|(4,[0,3],[1.0,-2.0])|
|   [4.0,5.0,0.0,3.0]|
|   [6.0,7.0,0.0,8.0]|
| (4,[0,3],[9.0,1.0])|
+--------------------+

带标签的稀疏向量
(0.0,(3,[0,2],[2.0,8.0]))
本地矩阵
3 x 2 CSCMatrix
(0,0) 9.0
(2,1) 6.0
(1,1) 8.0
坐标矩阵
MatrixEntry(0,1,0.5)
MatrixEntry(2,2,1.8)
