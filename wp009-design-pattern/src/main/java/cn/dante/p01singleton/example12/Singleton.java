package cn.dante.p01singleton.example12;
/**
 * 使用枚举来实现单例模式的示例
 */
public enum Singleton {
    /**
     * 定义一个枚举的元素,它就代表了Singleton的一个实例
     */
    uniqueInstance;

    /**
     * 示意方法，单例可以有自己的操作
     */
    public void singletonOperation(){
        //功能处理
        System.out.println("a=="+Singleton.uniqueInstance.hashCode());
    }

}
