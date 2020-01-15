package cn.dante.pattern.ty04bridge.v2;

public class IPod extends Product {
    public void beProducted() {
        System.out.println("生产出的iPod是这个样子的...");
    }

    public void beSelled() {
        System.out.println("生产出的iPod卖出去了...");
    }
}