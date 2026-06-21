package com.hamitmizrak.designpattern._1_creation._02_factory_method;

public class MainExampleFactoryMethod {

    static void main() {
        NotificationFactory factory = new NotificationFactory();

        INotification notificationFactory1= factory.createNotification("email");
        notificationFactory1.send();

        INotification notificationFactory2= factory.createNotification("sms");
        notificationFactory2.send();

        INotification notificationFactory3= factory.createNotification("push");
        notificationFactory3.send();

    }
}
