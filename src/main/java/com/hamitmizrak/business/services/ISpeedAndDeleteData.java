package com.hamitmizrak.business.services;

import java.util.List;

public interface ISpeedAndDeleteData <D,E> {

    // SPEED DATA
    public List<D> speedData(Integer data);

    // DELETE ALL
    public List<D> deleteData();
}
