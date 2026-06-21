package com.hamitmizrak.designpattern._1_creation._03_abstract_factory;

public interface INotificationFactory {

    INotification createNotification();
    IMessageTemplate createTemplate();
}
