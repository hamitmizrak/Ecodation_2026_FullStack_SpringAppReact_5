package com.hamitmizrak.business.services.interfaces;

import com.hamitmizrak.business.services.ICrudService;
import com.hamitmizrak.business.services.IImageService;
import com.hamitmizrak.business.services.IModelMapperService;
import com.hamitmizrak.business.services.ISpeedAndDeleteService;

// D: Dto
// E: Entity
public interface IBlogServices<D,E> extends
        IModelMapperService<D,E>,
        ISpeedAndDeleteService<D,E>,
        ICrudService<D,E>,
        IImageService<D> {
}
