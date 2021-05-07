import { find, assign } from 'lodash'
import querystring from 'querystring'


export default ({ service, request, serviceForMock, requestForMock, mock, faker, tools }) => ({
  /**
   * @description 用户
   * @param {Object} data 携带的信息
   */
  BUSINESS_USER (data = {}) {
    
    //let query = querystring.stringify(data);

    // 接口请求
    return request({
      url: '/system/admin/user/list',
      method: 'post',
      data
    })
  },
  BUSINESS_USER_ADD (data = {}) {
    

    // 接口请求
    return request({
      url: '/system/admin/user/add',
      method: 'post',
      data
    })
  },
  BUSINESS_USER_DEL (data = {}) {
    

    // 接口请求
    return request({
      url: '/system/admin/user/delete/'+data,
      method: 'get',
      data
    })
  },
  SYSTEM_USER_SAVEPASSWORD(data = {}){
    // 接口请求
    return request({
      url: '/system/admin/user/save-password',
      method: 'post',
      data
    })
  },
  SYSTEM_USER_ALL (data = {}) {

    // 接口请求
    return request({
      url: '/system/admin/user/list',
      method: 'get',
      data
    })
  },


})
