// blogCategoryService.js

// Import
import {axiosClient} from "../../lib/axiosClient";
import {ENDPOINTS} from "../../config/api";


// CREATE (BlogCategory)
export function createBlogCategories(payload={}){
    return axiosClient.post(ENDPOINTS.BLOG_CATEGORY.CREATE,payload)
}


// LIST (BlogCategory)
export function listBlogCategories(params={}){
    return axiosClient.get(ENDPOINTS.BLOG_CATEGORY.LIST,params)
}


// FIND (BlogCategory)
export function findByIdBlogCategories(id){
    return axiosClient.get(ENDPOINTS.BLOG_CATEGORY.FIND(id));
}


// UPDATE (BlogCategory)
export function updateBlogCategories(id,payload){
    return axiosClient.put(ENDPOINTS.BLOG_CATEGORY.UPDATE(id),payload);
}


// DELETE (BlogCategory)
export function deleteBlogCategories(id){
    return axiosClient.put(ENDPOINTS.BLOG_CATEGORY.DELETE(id));
}


// CREATE
// FIND
// UPDATE
// DELETE