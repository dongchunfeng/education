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
  },
  BUSINESS_COURSE_LISTCATEGORY (data = {}) {
    

    // 接口请求
    return request({
      url: '/business/admin/course/list-category/'+data,
      method: 'get',
      data
    })
  },
  BUSINESS_COURSE_FINDCOURSECONTENT (data = {}) {
    

    // 接口请求
    return request({
      url: '/business/admin/course/findCourseContent/'+data,
      method: 'get',
      data
    })
  },
  BUSINESS_COURSE_SAVECONTENT (data = {}) {
    

    // 接口请求
    return request({
      url: '/business/admin/course/saveContent/',
      method: 'post',
      data
    })
  },
  BUSINESS_COURSE_TEACHERLIST (data = {}) {
    

    // 接口请求
    return request({
      url: '/business/admin/teacher/getTeacherList/',
      method: 'get',
      data
    })
  }


})
