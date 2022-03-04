import request from '@/utils/request'

// 查询用户列表
export function searchCol(query) {
  return request({
    url: '/map/meta/cols',
    method: 'get',
    params: query
  })
}
