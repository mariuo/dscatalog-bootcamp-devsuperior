import axios,{ AxiosRequestConfig } from 'axios';
import { CLIENT_ID, CLIENT_SECRET, getSessionData, logout } from './auth';
import qs from 'qs';


type LoginData = {
    username: string;
    password: string;
}
const BASE_URL = process.env.REACT_APP_BACKEND_URL ?? 'http://localhost:8080';

axios.interceptors.response.use(function (response) {
    return response;
  }, function (error) {
      if (error.response.status === 401){
          logout();
      }
    return Promise.reject(error);
  });


export const makeRequest = (params : AxiosRequestConfig) => {
    return axios({
        ...params,
        baseURL: BASE_URL    
    });
}

export const makePrivateRequest = (params : AxiosRequestConfig) => {
    const sessionData = getSessionData();

    const headers = {
        'Authorization': `Bearer ${sessionData.access_token}`
    }
    return makeRequest({ ...params, headers})
}

export const makeLogin = (LoginData: LoginData) => {
    const token = `${CLIENT_ID}:${CLIENT_SECRET}`;

    const headers = {
        Authorization:`Basic ${window.btoa(token)}`,
        'Content-Type':'application/x-www-form-urlencoded'
    }
    const payload = qs.stringify({ ...LoginData, grant_type: 'password'})
       
    return makeRequest({url: '/oauth/token', data: payload, method: 'POST', headers})
}