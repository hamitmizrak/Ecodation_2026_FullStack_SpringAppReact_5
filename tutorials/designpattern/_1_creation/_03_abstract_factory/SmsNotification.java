package com.hamitmizrak.designpattern._1_creation._03_abstract_factory;

public class SmsNotification implements INotification {
    @Override
    public void send() {
        System.out.println("Sms gönderildi");
    }
}
