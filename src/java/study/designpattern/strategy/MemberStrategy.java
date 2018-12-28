package study.designpattern.strategy;

/**
 * describe:策略模式
 * <p>
 * 抽象折扣类
 *
 * @author dadou
 * @date 2018/12/28
 */
public interface MemberStrategy {
    /**
     * 计算图书的价格
     *
     * @param booksPrice图书原价
     * @return 打折后的价格
     */
    public double calcPrice(double booksPrice);
}
