import request from '@/utils/request'
import { parseStrEmpty } from "@/utils/ruoyi";

// 查询配置列表
export function listFileConf(query) {
  return request({
    url: '/load/conf/list',
    method: 'get',
    params: query
  })
}

export function getConf() {
  return request({
    url: '/load/conf/get',
    method: 'get'
  })
}

// 查询用户详细
export function getFileConf(confId) {
  return request({
    url: '/load/conf/' + parseStrEmpty(confId),
    method: 'get'
  })
}

// 删除配置
export function delFileConf(confId) {
  return request({
    url: '/load/conf/' + confId,
    method: 'delete'
  })
}
// 新增配置
export function addFileConf(data) {
  return request({
    url: '/load/conf',
    method: 'post',
    data: data
  })
}

// 修改配置
export function updateFileConf(data) {
  return request({
    url: '/load/conf',
    method: 'put',
    data: data
  })
}

