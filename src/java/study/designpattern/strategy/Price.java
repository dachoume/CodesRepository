package study.designpattern.strategy;

/**
 * describe:价格类，也就是上下文类
 *
 * @author dadou
 * @date 2018/12/28
 */
public class Price {
    //持有顶级抽象
    private MemberStrategy memberStrategy;

    /**
     * 构造函数，传入一个具体的策略对象
     *
     * @param memberStrategy 具体的策略对象
     */
    public Price(MemberStrategy memberStrategy) {
        this.memberStrategy = memberStrategy;
    }

    /**
     * 计算图书的价格
     *
     * @param booksPrice 图书原价格
     * @return 现价格
     */
    public double quote(double booksPrice) {
        return this.memberStrategy.calcPrice(booksPrice);
    }

}
