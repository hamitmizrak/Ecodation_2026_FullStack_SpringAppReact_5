package com.hamitmizrak.designpattern._1_creation._03_abstract_factory;

import com.hamitmizrak.designpattern._1_creation._02_factory_method.INotification;

public class SmsTemplate implements IMessageTemplate {

    @Override
    public String getTemplate() {
        return "SMS Şablonu Doğrulama kodunuz hazır";
    }
}
