package com.hamitmizrak.designpattern._1_creation._03_abstract_factory;

public class SmsFactory implements INotificationFactory{


    @Override
    public INotification createNotification() {
        return new SmsNotification();
    }

    @Override
    public IMessageTemplate createTemplate() {
        return new SmsTemplate();
    }
}
