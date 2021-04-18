import { find, assign } from 'lodash'
import querystring from 'querystring'


export default ({ service, request, serviceForMock, requestForMock, mock, faker, tools }) => ({
  /**
   * @description 课程表
   * @param {Object} data 携带的信息
   */
  BUSINESS_COURSE (data = {}) {
    
    let query = querystring.stringify(data);

    // 接口请求
    return request({
      url: '/business/admin/course/'+data.page+'/'+data.size+"?"+query,
      method: 'get',
      data
    })
  },
  BUSINESS_COURSE_ADD (data = {}) {
    

    // 接口请求
    return request({
      url: '/business/admin/course/add',
      method: 'post',
      data
    })
  },
  BUSINESS_COURSE_DEL (data = {}) {
    

    // 接口请求
    return request({
      url: '/business/admin/course/delete/'+data,
      method: 'get',
      data
    })
  }


})
