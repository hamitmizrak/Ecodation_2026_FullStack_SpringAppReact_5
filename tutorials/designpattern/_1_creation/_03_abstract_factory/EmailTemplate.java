package com.hamitmizrak.designpattern._1_creation._03_abstract_factory;

public class EmailTemplate  implements IMessageTemplate {

    @Override
    public String getTemplate() {
        return "Email şablonu Hoşgeldiniz maili";
    }
}
