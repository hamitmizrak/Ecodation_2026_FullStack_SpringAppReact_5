package com.hamitmizrak.designpattern._1_creation._03_abstract_factory;


public class MainExampleAbstractFactory {

    static void main() {
       INotificationFactory emailFactory= new EmailFactory();
        INotification emailNotification = emailFactory.createNotification();
        IMessageTemplate emailTemplate = emailFactory.createTemplate();

        emailNotification.send();
        System.out.println(emailTemplate.getTemplate());
        System.out.println("-------------------------------");

    }
}
