import request from '@/utils/request'
import { parseStrEmpty } from "@/utils/ruoyi";

// 查询数据源列表
export function ListDataSource(query) {
  return request({
    url: '/datasource/datasource/list',
    method: 'get',
    params: query
  })
}

// 新增数据源
export function addDataSource(data) {
  return request({
    url: '/datasource/datasource',
    method: 'post',
    data: data
  })
}

//查询数据源详情
export function getDataSource(id) {
  return request({
    url: '/datasource/datasource/' + parseStrEmpty(id),
    method: 'get'
  })
}
//查询数据源详情
export function connectTest(data) {
  return request({
    url: '/datasource/datasource/connect',
    method: 'post',
    data:data
  })
}

// 修改数据源
export function updateDataSource(id,data) {
  return request({
    url: '/datasource/datasource/'+ id,
    method: 'put',
    data: data
  })
}

// 删除数据源
export function delDataSource(id) {
  return request({
    url: '/datasource/datasource/' + id,
    method: 'delete'
  })
}

