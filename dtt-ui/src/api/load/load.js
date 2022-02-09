import request from '@/utils/request'

// 查询用户列表
export function listFile(query) {
  return request({
    url: '/load/file/list',
    method: 'get',
    params: query
  })
}

// 删除用户
export function delFile(fileId) {
  return request({
    url: '/load/file/' + fileId,
    method: 'delete'
  })
}
// 用户头像上传
export function uploadFile(data) {
  return request({
    url: '/load/file/save',
    method: 'post',
    data: data
  })
}

