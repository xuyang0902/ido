package com.ido.design.bridge;

public class GG {
    public static void main(String[] args) {
        chase(new MM("王美丽"));
    }
    public static void chase(MM mm){
        Gift g = new WildGift(new Flower());
        give(mm,g);
    }

    private static void give(MM mm, Gift g) {
        System.out.println(mm.getName() +  "na, it's for you! " + g.impl);
    }
}
