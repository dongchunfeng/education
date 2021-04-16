import { find, assign } from 'lodash'
import querystring from 'querystring'


export default ({ service, request, serviceForMock, requestForMock, mock, faker, tools }) => ({
  /**
   * @description ${tableNameCn}
   * @param {Object} data 携带的信息
   */
  BUSINESS_${DOMAIN} (data = {}) {
    
    let query = querystring.stringify(data);

    // 接口请求
    return request({
      url: '/${module}/admin/${domain}/'+data.page+'/'+data.size+"?"+query,
      method: 'get',
      data
    })
  },
  BUSINESS_${DOMAIN}_ADD (data = {}) {
    

    // 接口请求
    return request({
      url: '/${module}/admin/${domain}/add',
      method: 'post',
      data
    })
  },
  BUSINESS_${DOMAIN}_DEL (data = {}) {
    

    // 接口请求
    return request({
      url: '/${module}/admin/${domain}/delete/'+data,
      method: 'get',
      data
    })
  }


})
