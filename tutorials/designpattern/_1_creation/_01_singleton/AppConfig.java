package com.hamitmizrak.designpattern._1_creation._01_singleton;

public class AppConfig {

    // Singleton Design Patter
    private static AppConfig instance;

    // Field
    private String appName;


    // Constructor
    // Constructor private olursa: Dışarıdan instance izin vermez
    private AppConfig() {
        this.appName = "Digem App";
    }

    // Tek nesneye erişim sağlayan method
    public static AppConfig getInstance(){
        if(instance==null){
            instance = new AppConfig();
        }
        return instance;
    }

    // Getter and setter
    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }
}
