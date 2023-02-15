import { find, assign } from 'lodash'
import querystring from 'querystring'


export default ({ service, request, serviceForMock, requestForMock, mock, faker, tools }) => ({
  /**
   * @description 文件
   * @param {Object} data 携带的信息
   */
  file_FILE (data = {}) {
    
    let query = querystring.stringify(data);

    // 接口请求
    return request({
      url: '/file/admin/file/'+data.page+'/'+data.size+"?"+query,
      method: 'get',
      data
    })
  },
  file_FILE_ADD (data = {}) {
    

    // 接口请求
    return request({
      url: '/file/admin/file/add',
      method: 'post',
      data
    })
  },
  file_FILE_DEL (data = {}) {
    

    // 接口请求
    return request({
      url: '/file/admin/file/delete/'+data,
      method: 'get',
      data
    })
  }


})
