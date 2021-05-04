import { find, assign } from 'lodash'
import querystring from 'querystring'


export default ({ service, request, serviceForMock, requestForMock, mock, faker, tools }) => ({
  /**
   * @description 角色
   * @param {Object} data 携带的信息
   */
  system_ROLE (data = {}) {
    
    let query = querystring.stringify(data);

    // 接口请求
    return request({
      url: '/system/admin/role/'+data.page+'/'+data.size+"?"+query,
      method: 'get',
      data
    })
  },
  system_ROLE_ADD (data = {}) {
    

    // 接口请求
    return request({
      url: '/system/admin/role/add',
      method: 'post',
      data
    })
  },
  system_ROLE_DEL (data = {}) {
    

    // 接口请求
    return request({
      url: '/system/admin/role/delete/'+data,
      method: 'get',
      data
    })
  },
  system_LIST_ROLERESOURCE (data = {}) {
    

    // 接口请求
    return request({
      url: '/system/admin/role/listRoleResource/'+data,
      method: 'get',
      data
    })
  },
  system_SAVE_ROLERESOURCE (data = {}) {
    

    // 接口请求
    return request({
      url: '/system/admin/role/saveResource',
      method: 'post',
      data
    })
  },
  system_SAVE_ROLEUSER (data = {}) {
    

    // 接口请求
    return request({
      url: '/system/admin/role/saveUser',
      method: 'post',
      data
    })
  },
  system_LIST_ROLEUSER (data = {}) {
    

    // 接口请求
    return request({
      url: '/system/admin/role/listRoleUser/'+data,
      method: 'get',
      data
    })
  }


})
