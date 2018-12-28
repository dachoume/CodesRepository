package study.designpattern.strategy;

/**
 * describe:客户端类
 *
 * @author dadou
 * @date 2018/12/28
 */
public class Client {
    public static void main(String[] args) {
        AdvancedMemberStrategy advancedMemberStrategy = new AdvancedMemberStrategy();

        Price price = new Price(advancedMemberStrategy);

        double quote = price.quote(100);

        System.out.println("最终价格为" + quote);
    }
}
