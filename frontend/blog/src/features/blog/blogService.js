// src/api/blogService.js

import { axiosClient } from '../../lib/axiosClient';
import { ENDPOINTS } from '../../config/api';


// Ekle (opsiyonel resim)
export function createBlogWithImage(values, file, onProgress) {
    // values: { title, content, categoryId, ... }
    const form = new FormData();
    Object.entries(values || {}).forEach(([k, v]) => form.append(k, v ?? ''));

    if (file) form.append('image', file);

    return axiosClient.post(ENDPOINTS.BLOG.CREATE, form, {
        headers: { 'Content-Type': 'multipart/form-data' },
        onUploadProgress: (evt) => {
            if (onProgress && evt.total) onProgress(Math.round((evt.loaded * 100) / evt.total));
        },
    });
}


// Liste
export function listBlogs(params = {}) {
    return axiosClient.get(ENDPOINTS.BLOG.LIST, { params });
}

// Bul
export function findByIdBlog(id) {
    return axiosClient.get(ENDPOINTS.BLOG.FIND(id));
}

// Güncelle (opsiyonel resim)
export function updateBlogWithImage(id, values, file, onProgress) {
    const form = new FormData();
    Object.entries(values || {}).forEach(([k, v]) => form.append(k, v ?? ''));
    if (file) form.append('image', file);

    return axiosClient.put(ENDPOINTS.BLOG.UPDATE(id), form, {
        headers: { 'Content-Type': 'multipart/form-data' },
        onUploadProgress: (evt) => {
            if (onProgress && evt.total) onProgress(Math.round((evt.loaded * 100) / evt.total));
        },
    });
}

// Sil
export function deleteBlog(id) {
    return axiosClient.delete(ENDPOINTS.BLOG.DELETE(id));
}
