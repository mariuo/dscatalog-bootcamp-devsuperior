import axios from 'axios'

export const api = axios.create({
    baseURL: "https://mario-dscatalog.herokuapp.com",
});

export const TOKEN = "Basic ZHNjYXRhbG9nOmRzY2F0YWxvZzEyMw==";

export function getProducts(){
    const res = api.get(`/products?page=0&linesPerPage=12&direction=ASC&orderBy=name`);
    return res;
}