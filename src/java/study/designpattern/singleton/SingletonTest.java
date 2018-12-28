package study.designpattern.singleton;

/**
 * describe:测试枚举实现的单例
 *
 * @author dadou
 * @date 2018/12/20
 */
public class SingletonTest {
    public static void main(String[] args) {
        EnumSingletonStudy uniquesingleton1 = EnumSingletonStudy.uniquesingleton;
        EnumSingletonStudy uniquesingleton2 = EnumSingletonStudy.uniquesingleton;
        //已有提示说 always true
        System.out.println(uniquesingleton1 == uniquesingleton2);

        uniquesingleton1.singletonSayHello();
    }
}
