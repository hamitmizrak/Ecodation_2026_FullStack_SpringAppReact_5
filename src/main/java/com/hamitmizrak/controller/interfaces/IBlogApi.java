package com.hamitmizrak.controller.interfaces;

import com.hamitmizrak.controller.ICrudApi;
import com.hamitmizrak.controller.IImageApi;
import com.hamitmizrak.controller.ISpeedAndDeleteApi;

// D: Dto
public interface IBlogApi<D> extends
        ISpeedAndDeleteApi<D>,
        ICrudApi<D>,
        IImageApi<D> {
}
