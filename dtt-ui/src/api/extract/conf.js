import request from '@/utils/request'
import { parseStrEmpty } from "@/utils/ruoyi";

// 查询模板列表
export function listExtractConf(query) {
  return request({
    url: '/extract/conf/list',
    method: 'get',
    params: query
  })
}
// 查询模板详细
export function getExtractConf(id) {
  return request({
    url: '/extract/conf/' + parseStrEmpty(id),
    method: 'get'
  })
}

// 查询模板用户信息
export function getUserTemplate(id) {
  return request({
    url: '/extract/conf/userTemplate/' + id,
    method: 'get'
  })
}

//获取用户所属的取数模板
export function getUserTemplates(query) {
  return request({
    url: '/extract/conf/template/',
    method: 'get',
    params: query
  })
}

// 保存授权角色
export function updateUserTemplate(data) {
  return request({
    url: '/extract/conf/userTemplate',
    method: 'put',
    params: data
  })
}

// 删除配置
export function delExtractConf(ids) {
  return request({
    url: '/extract/conf/' + ids,
    method: 'delete'
  })
}
// 新增配置
export function addExtractConf(data) {
  return request({
    url: '/extract/conf',
    method: 'post',
    data: data
  })
}

// 修改配置
export function updateExtractConf(data) {
  return request({
    url: '/extract/conf',
    method: 'put',
    data: data
  })
}
