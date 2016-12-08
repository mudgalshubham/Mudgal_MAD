package com.example.sunnyboy.shubham_final;

/**
 * Created by SunnyBoy on 12/8/16.
 */

public class IceCreamShop {
    private String iceCreamShop;
    private String iceCreamShopURL;

    private void setIceCreamInfo(Integer IceCreamFlavor){
        switch (IceCreamFlavor){
            case 0: //Death By Chocolate
                iceCreamShop="Glacier";
                iceCreamShopURL="http://www.glaciericecream.com";
                break;
            case 1: //Cookies and Cream
                iceCreamShop="Sweet Cow";
                iceCreamShopURL="http://www.sweetcowicecream.com";
                break;
            case 2: //Salted Caramel
                iceCreamShop="Fior di Latte";
                iceCreamShopURL="http://fiordilattegelato.com";
                break;
            default:
                iceCreamShop="none";
                iceCreamShopURL="https://www.google.com/search?q=boulder+Icecream+shops&ie=utf-8&oe=utf-8";
        }
    }

    public String getIceCreamShop() {
        return iceCreamShop;
    }

    public void setIceCreamShop(Integer iceCreamFlavor) {
        setIceCreamInfo(iceCreamFlavor);
    }

    public String getIceCreamShopURL() {
        return iceCreamShopURL;
    }

    public void setIceCreamShopURL(Integer iceCreamFlavor) {
        setIceCreamInfo(iceCreamFlavor);
    }
}
