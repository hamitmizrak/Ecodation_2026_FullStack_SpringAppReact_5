package com.hamitmizrak.designpattern._1_creation._01_singleton;

public class MainExampleSingleton {

    static void main() {
        AppConfig appConfig1 =AppConfig.getInstance();
        appConfig1.setAppName("Mobile");
        System.out.println(appConfig1.getAppName());

        AppConfig appConfig2 =AppConfig.getInstance();
        appConfig2.setAppName("Yapay Zeka");
        System.out.println(appConfig2.getAppName());

        // Kontrol
        if(appConfig1 == appConfig2) {
            System.out.println("Aynı nesne kullanılıyor ve Singleton başarılı");
        }else{
            System.out.println("Farklı nesneler kullanılmış eyvahhhhh");
        }

    }
}
