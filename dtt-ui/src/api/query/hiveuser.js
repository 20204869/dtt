import request from '@/utils/request'
import { parseStrEmpty } from "@/utils/ruoyi";

// 查询关系列表
export function ListHiveUser(query) {
  return request({
    url: '/hive/config/list',
    method: 'get',
    params: query
  })
}

// 新增关系
export function addHiveUser(data) {
  return request({
    url: '/hive/config',
    method: 'post',
    data: data
  })
}

//查询数据源详情
export function getHiveUser(id) {
  return request({
    url: '/hive/config/' + parseStrEmpty(id),
    method: 'get'
  })
}
//查询数据源详情
export function connectTest(data) {
  return request({
    url: '/hive/config/connect',
    method: 'post',
    data:data
  })
}

// 修改关系
export function updateHiveUser(id,data) {
  return request({
    url: '/hive/config/'+ id,
    method: 'put',
    data: data
  })
}

// 删除关系
export function delHiveUser(id) {
  return request({
    url: '/hive/config/' + id,
    method: 'delete'
  })
}

