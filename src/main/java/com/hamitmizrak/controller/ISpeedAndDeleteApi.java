package com.hamitmizrak.controller;

import com.hamitmizrak.error.ApiResult;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ISpeedAndDeleteApi<D> {

    // SPEED DATA
    public ResponseEntity<ApiResult<List<D>>> speedData(Integer data);

    // DELETE ALL
    public ResponseEntity<ApiResult<List<D>>> deleteData();
}
