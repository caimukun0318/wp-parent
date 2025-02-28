package cn.dante.pattern.ty05builder.v2;

import cn.dante.pattern.ty05builder.BenzModel;

import java.util.ArrayList;

public class Client {
    public static void main(String[] args) {
        /*
         * 客户告诉牛叉公司，我要这样一个模型，然后牛叉公司就告诉我老大
         * 说要这样一个模型，这样一个顺序，然后我就来制造
         */
        ArrayList<String> sequence = new ArrayList<String>(); //存放run的顺序
        sequence.add("engine boom"); //客户要求，run的时候时候先发动引擎
        sequence.add("start"); //启动起来
        sequence.add("stop"); //开了一段就停下来
    //要一个奔驰车：
        BenzBuilder benzBuilder = new BenzBuilder();
    //把顺序给这个builder类，制造出这样一个车出来
        benzBuilder.setSequence(sequence);
    //制造出一个奔驰车
        BenzModel benz = (BenzModel) benzBuilder.getCarModel();
    //奔驰车跑一下看看
        benz.run();
    }
}