package com.hamitmizrak.controller.interfaces;

import com.hamitmizrak.controller.ICrudApi;
import com.hamitmizrak.controller.ISpeedAndDeleteApi;

// D: Dto
// E: Entity
public interface IBlogCategoryApi<D> extends
        ISpeedAndDeleteApi<D>,
        ICrudApi<D> {
}
