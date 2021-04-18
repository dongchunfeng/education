import { find, assign } from 'lodash'
import querystring from 'querystring'


export default ({ service, request, serviceForMock, requestForMock, mock, faker, tools }) => ({
  /**
   * @description 小节
   * @param {Object} data 携带的信息
   */
  BUSINESS_SECTION (data = {}) {
    
    let query = querystring.stringify(data);

    // 接口请求
    return request({
      url: '/business/admin/section/'+data.page+'/'+data.size+"?"+query,
      method: 'get',
      data
    })
  },
  BUSINESS_SECTION_ADD (data = {}) {
    

    // 接口请求
    return request({
      url: '/business/admin/section/add',
      method: 'post',
      data
    })
  },
  BUSINESS_SECTION_DEL (data = {}) {
    

    // 接口请求
    return request({
      url: '/business/admin/section/delete/'+data,
      method: 'get',
      data
    })
  }


})
