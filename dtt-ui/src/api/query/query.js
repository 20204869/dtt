import request from '@/utils/request'

// 历史查询记录列表
export function historyQuery() {
  return request({
    url: '/query/history/list',
    method: 'get'
  })
}

// 新增查询记录
export function addQuery(data) {
  return request({
    url: '/query/history',
    method: 'post',
    data:data
  })
}

//执行查询
export function executeSql(data) {
  return request({
    url: '/query/history/excuteSql',
    method: 'post',
    data:data
  })
}

//查询执行日志
export function queryLog() {
  return request({
    url: '/query/history/log',
    method: 'get'
  })
}
