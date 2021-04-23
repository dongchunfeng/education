import { find, assign } from 'lodash'
import querystring from 'querystring'


export default ({ service, request, serviceForMock, requestForMock, mock, faker, tools }) => ({
  /**
   * @description 讲师
   * @param {Object} data 携带的信息
   */
  BUSINESS_TEACHER (data = {}) {
    
    let query = querystring.stringify(data);

    // 接口请求
    return request({
      url: '/business/admin/teacher/'+data.page+'/'+data.size+"?"+query,
      method: 'get',
      data
    })
  },
  BUSINESS_TEACHER_ADD (data = {}) {
    

    // 接口请求
    return request({
      url: '/business/admin/teacher/add',
      method: 'post',
      data
    })
  },
  BUSINESS_TEACHER_DEL (data = {}) {
    

    // 接口请求
    return request({
      url: '/business/admin/teacher/delete/'+data,
      method: 'get',
      data
    })
  }


})
