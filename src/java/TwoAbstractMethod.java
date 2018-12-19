package study.AnonymousInnerClassStudy;

/**
 * describe:使用匿名内部类时需要将匿名内部类中的全部抽象方法实现（就算没用到也要实现全部）
 *
 * @author dadou
 * @date 2018/12/19
 */
public class TwoAbstractMethod {
    private void test(Animal animal) {
        animal.fly();
        animal.eat();
    }

    public static void main(String[] args) {
        TwoAbstractMethod twoAbstractMethod = new TwoAbstractMethod();
        twoAbstractMethod.test(new Animal() {
            @Override
            public void fly() {
                System.out.println("can fly");
            }

            @Override
            public void eat() {
                System.out.println("can eat");
            }
        });
    }
}

abstract class Animal {
    public abstract void fly();

    public abstract void eat();
}
