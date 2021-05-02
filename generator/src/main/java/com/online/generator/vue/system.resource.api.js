import { find, assign } from 'lodash'
import querystring from 'querystring'


export default ({ service, request, serviceForMock, requestForMock, mock, faker, tools }) => ({
  /**
   * @description 资源
   * @param {Object} data 携带的信息
   */
  system_RESOURCE (data = {}) {
    
    let query = querystring.stringify(data);

    // 接口请求
    return request({
      url: '/system/admin/resource/'+data.page+'/'+data.size+"?"+query,
      method: 'get',
      data
    })
  },
  system_RESOURCE_ADD (data = {}) {
    

    // 接口请求
    return request({
      url: '/system/admin/resource/add',
      method: 'post',
      data
    })
  },
  system_RESOURCE_DEL (data = {}) {
    

    // 接口请求
    return request({
      url: '/system/admin/resource/delete/'+data,
      method: 'get',
      data
    })
  }


})
