package com.hamitmizrak.designpattern._1_creation._02_factory_method;

public class PushNotification implements INotification{
    @Override
    public void send() {
        System.out.println("Push Bildirimi gönderildi");
    }
}
