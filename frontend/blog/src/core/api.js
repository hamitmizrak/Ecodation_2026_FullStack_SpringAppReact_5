// src/core/api.js
// Merkezi API yapılandırması + Axios instance + tüm küçük servisler.
// Eski dosyaların yerine geçer:
// config/api.js, lib/axiosClient.js, authService.js, registerService.js,
// roleService.js, blogService.js, blogCategoryService.js

import axios from 'axios';

const trimTrailingSlash = (value = '') => String(value || '').replace(/\/+$/, '');

// Proje react-scripts (CRA) kullandığı için process.env güvenli ve doğrudan kullanılır.
export const API_BASE = trimTrailingSlash(
  process.env.REACT_APP_API_BASE || 'http://localhost:5555'
);

export const IMAGE_BASE = trimTrailingSlash(
  process.env.REACT_APP_IMAGE_BASE || API_BASE
);

export const ENDPOINTS = Object.freeze({
  LOGIN: '/auth/api/v1.0.0/login',
  REGISTER_CREATE: (rolesId = 1) => `/register/api/v1.0.0/create/${rolesId}`,
  ME: '/auth/api/v1.0.0/me',

  ROLES: {
    LIST: '/roles/api/v1.0.0',
  },

  BLOG_CATEGORY: {
    LIST: '/blog/category/api/v1.0.0/list',
    CREATE: '/blog/category/api/v1.0.0/create',
    FIND: (id) => `/blog/category/api/v1.0.0/find/${id}`,
    UPDATE: (id) => `/blog/category/api/v1.0.0/update/${id}`,
    DELETE: (id) => `/blog/category/api/v1.0.0/delete/${id}`,
  },

  BLOG: {
    LIST: '/blog/api/v1.0.0/list',
    CREATE: '/blog/api/v1.0.0/create',
    FIND: (id) => `/blog/api/v1.0.0/find/${id}`,
    UPDATE: (id) => `/blog/api/v1.0.0/update/${id}`,
    DELETE: (id) => `/blog/api/v1.0.0/delete/${id}`,
  },
});

export const axiosClient = axios.create({
  baseURL: API_BASE,
});

export function setAccessToken(token) {
  if (!token) {
    clearAccessToken();
    return;
  }
  axiosClient.defaults.headers.common.Authorization = `Bearer ${token}`;
}

export function clearAccessToken() {
  delete axiosClient.defaults.headers.common.Authorization;
}

export function setApiLanguage(language = 'tr') {
  const normalized = String(language || 'tr').toLowerCase().startsWith('en') ? 'en' : 'tr';
  axiosClient.defaults.headers.common['Accept-Language'] = normalized;
  if (typeof window !== 'undefined') localStorage.setItem('language', normalized);
  return normalized;
}

axiosClient.interceptors.request.use((config) => {
  const nextConfig = config;
  nextConfig.headers = nextConfig.headers || {};

  if (!nextConfig.headers.Authorization && typeof window !== 'undefined') {
    const token = localStorage.getItem('token');
    if (token) nextConfig.headers.Authorization = `Bearer ${token}`;
  }

  if (!nextConfig.headers['Accept-Language'] && typeof window !== 'undefined') {
    nextConfig.headers['Accept-Language'] = localStorage.getItem('language') || 'tr';
  }

  return nextConfig;
});

export function extractApiData(response) {
  const body = response?.data ?? response ?? {};
  return body?.data ?? body?.result ?? body?.items ?? body?.content ?? body;
}

export function resolveImageUrl(source) {
  if (!source) return '';
  const raw = String(source).trim().replace(/\\/g, '/');
  if (!raw) return '';
  if (/^(https?:|data:|blob:)/i.test(raw)) return raw;

  const uploadIndex = raw.toLowerCase().lastIndexOf('/upload/');
  const normalized = (uploadIndex >= 0 ? raw.substring(uploadIndex) : raw).replace(/^\.?\/+/, '');
  return `${IMAGE_BASE}/${normalized}`;
}

function buildBlogDto(values = {}) {
  const dto = { ...values };
  const categoryId =
    dto.categoryId ?? dto.blogCategoryDto?.categoryId ?? dto.blogCategoryDto?.id ?? null;

  delete dto.categoryId;

  if (categoryId !== null && categoryId !== undefined && categoryId !== '') {
    dto.blogCategoryDto = {
      ...(dto.blogCategoryDto || {}),
      categoryId: Number(categoryId),
    };
  }

  return dto;
}

function buildBlogMultipart(values, file) {
  const form = new FormData();
  // Backend: @RequestPart("blog") String json, @RequestPart("file") MultipartFile
  form.append('blog', JSON.stringify(buildBlogDto(values)));
  if (file) form.append('file', file);
  return form;
}

function uploadConfig(onProgress) {
  if (typeof onProgress !== 'function') return undefined;
  return {
    onUploadProgress: (event) => {
      if (event.total) onProgress(Math.round((event.loaded * 100) / event.total));
    },
  };
}

// AUTH
export function loginApi(email, password) {
  return axiosClient.post(ENDPOINTS.LOGIN, { email, password });
}

export function fetchMe() {
  return axiosClient.get(ENDPOINTS.ME);
}

export function createRegisterWithImage(rolesId = 1, values, file = null, onProgress) {
  const form = new FormData();
  form.append('register', new Blob([JSON.stringify(values || {})], { type: 'application/json' }));
  if (file) form.append('file', file);
  return axiosClient.post(ENDPOINTS.REGISTER_CREATE(rolesId), form, uploadConfig(onProgress));
}

export function listRoles(params = {}) {
  if (!ENDPOINTS?.ROLES?.LIST) return Promise.resolve({ data: [] });
  return axiosClient.get(ENDPOINTS.ROLES.LIST, { params });
}

// BLOG
export function createBlogWithImage(values, file = null, onProgress) {
  if (!file) return axiosClient.post(ENDPOINTS.BLOG.CREATE, buildBlogDto(values));
  return axiosClient.post(
    ENDPOINTS.BLOG.CREATE,
    buildBlogMultipart(values, file),
    uploadConfig(onProgress)
  );
}

export function listBlogs(params = {}) {
  return axiosClient.get(ENDPOINTS.BLOG.LIST, { params });
}

export function findByIdBlog(id) {
  return axiosClient.get(ENDPOINTS.BLOG.FIND(id));
}

export function updateBlogWithImage(id, values, file = null, onProgress) {
  if (!file) return axiosClient.put(ENDPOINTS.BLOG.UPDATE(id), buildBlogDto(values));
  return axiosClient.put(
    ENDPOINTS.BLOG.UPDATE(id),
    buildBlogMultipart(values, file),
    uploadConfig(onProgress)
  );
}

export function deleteBlog(id) {
  return axiosClient.delete(ENDPOINTS.BLOG.DELETE(id));
}

// BLOG CATEGORY
export function createBlogCategories(payload = {}) {
  return axiosClient.post(ENDPOINTS.BLOG_CATEGORY.CREATE, payload);
}

export function listBlogCategories(params = {}) {
  return axiosClient.get(ENDPOINTS.BLOG_CATEGORY.LIST, { params });
}

export function findByIdBlogCategories(id) {
  return axiosClient.get(ENDPOINTS.BLOG_CATEGORY.FIND(id));
}

export function updateBlogCategories(id, payload = {}) {
  return axiosClient.put(ENDPOINTS.BLOG_CATEGORY.UPDATE(id), payload);
}

export function deleteBlogCategories(id) {
  return axiosClient.delete(ENDPOINTS.BLOG_CATEGORY.DELETE(id));
}
