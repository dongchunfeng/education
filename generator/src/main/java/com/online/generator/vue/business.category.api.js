import { find, assign } from 'lodash'
import querystring from 'querystring'


export default ({ service, request, serviceForMock, requestForMock, mock, faker, tools }) => ({
  /**
   * @description 分类表
   * @param {Object} data 携带的信息
   */
  BUSINESS_CATEGORY (data = {}) {
    
    let query = querystring.stringify(data);

    // 接口请求
    return request({
      url: '/business/admin/category/'+data.page+'/'+data.size+"?"+query,
      method: 'get',
      data
    })
  },
  BUSINESS_CATEGORY_ADD (data = {}) {
    

    // 接口请求
    return request({
      url: '/business/admin/category/add',
      method: 'post',
      data
    })
  },
  BUSINESS_CATEGORY_DEL (data = {}) {
    

    // 接口请求
    return request({
      url: '/business/admin/category/delete/'+data,
      method: 'get',
      data
    })
  }


})
