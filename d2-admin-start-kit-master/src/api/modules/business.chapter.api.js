import { find, assign } from 'lodash'
import querystring from 'querystring'


export default ({ service, request, serviceForMock, requestForMock, mock, faker, tools }) => ({
  /**
   * @description 大章
   * @param {Object} data 携带的信息
   */
  BUSINESS_CHAPTER (data = {}) {
    
    let query = querystring.stringify(data);

    // 接口请求
    return request({
      url: '/business/admin/chapter/'+data.page+'/'+data.size+"?"+query,
      method: 'get',
      data
    })
  },
  BUSINESS_CHAPTER_ADD (data = {}) {
    

    // 接口请求
    return request({
      url: '/business/admin/chapter/add',
      method: 'post',
      data
    })
  },
  BUSINESS_CHAPTER_DEL (data = {}) {
    

    // 接口请求
    return request({
      url: '/business/admin/chapter/delete/'+data,
      method: 'get',
      data
    })
  }


})
