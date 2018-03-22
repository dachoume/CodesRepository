package testcou

/**
  * Created by 56308 on 2017/12/5.
  */
case class MySortKey(var first:String,var second:Int,var third:Int)
  extends Ordered[MySortKey] with Serializable{



  override def compare(that: MySortKey): Int = {
    if (first.hashCode-that.first.hashCode !=0){
      first.hashCode-that.first.hashCode
    }else if(second-that.second != 0){
      second-that.second
    }else if(third-that.third != 0){
      third-that.third
    }else{
      0
    }
  }


//  override def compareTo(that: MySortKey): Int = compare(that)

//  override def toString = s"MySortKey($first, $second, $third)"
}
