package com.hamitmizrak.controller;

import com.hamitmizrak.business.dto.BlogDto;
import com.hamitmizrak.error.ApiResult;
import org.springframework.http.ResponseEntity;

import java.util.List;

// D: Dto

public interface ICrudApi<D> {

    // CREATE
    public ResponseEntity<ApiResult<?>>  objectServiceCreate(D d);

    // LIST
    public ResponseEntity<ApiResult<List<D>>> objectServiceList();

    // FIND BY ID
    public ResponseEntity<ApiResult<?>> objectServiceFindById(Long id);

    // UPDATE
    public ResponseEntity<ApiResult<?>> objectServiceUpdate(Long id, D d);

    // DELETE
    public ResponseEntity<ApiResult<?>> objectServiceDelete(Long id);
} // end ICrudService
