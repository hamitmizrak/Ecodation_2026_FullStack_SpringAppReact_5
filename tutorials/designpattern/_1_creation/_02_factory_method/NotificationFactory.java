package com.hamitmizrak.designpattern._1_creation._02_factory_method;

public class NotificationFactory {

    // Factory Method
    public INotification createNotification(String type) {
        if(type==null || type.isBlank()){
            throw new IllegalArgumentException("Bildirim tipi boş olamaz");
        }

        if("email".equalsIgnoreCase(type)){
            return new EmailNotification();
        }

        if("sms".equalsIgnoreCase(type)){
            return new SmsNotification();
        }

        if("push".equalsIgnoreCase(type)){
            return new PushNotification();
        }

        throw new IllegalArgumentException("Geçersiz Bildirim tipi: "+type);
    }
}
