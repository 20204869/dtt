import request from '@/utils/request'

// 查询表
export function getBusiness(searchValue) {
  return request({
    url: '/map/business/businessTable/' + searchValue,
    method: 'get'
  })
}

// 查询表字段信息
export function getCols(tableName) {
  return request({
    url: '/map/business/businessCol/' + tableName,
    method: 'get'
  })
}
