package study.designpattern.singleton;

/**
 * describe:通过枚举实现单例，默认构造函数是private的，饿汉式
 * 标准的饿汉式也是通过静态加载，利用jvm实现线程安全
 *
 * @author dadou
 * @date 2018/12/20
 */
public enum EnumSingletonStudy {
    uniquesingleton;

    public void singletonSayHello() {
        System.out.println("Hello");
    }


}

