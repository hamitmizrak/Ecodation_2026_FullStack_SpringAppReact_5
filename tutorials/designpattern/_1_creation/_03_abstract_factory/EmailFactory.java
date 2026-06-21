package com.hamitmizrak.designpattern._1_creation._03_abstract_factory;

public class EmailFactory implements INotificationFactory{

    @Override
    public INotification createNotification() {
        return new EmailNotification();
    }

    @Override
    public IMessageTemplate createTemplate() {
        return new EmailTemplate();
    }
}
