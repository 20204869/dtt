import request from '@/utils/request'

// 历史查询记录列表
export function historyQuery() {
  return request({
    url: '/query/history/list',
    method: 'get'
  })
}

// 新增用户
export function addQuery(data) {
  return request({
    url: '/query/history',
    method: 'post',
    data:data
  })
}


export function executeSql(data) {
  return request({
    url: '/query/history/excuteSql',
    method: 'post',
    data:data
  })
}
