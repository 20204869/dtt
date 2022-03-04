import request from '@/utils/request'
import { parseStrEmpty } from "@/utils/ruoyi";

// 查询指标列表
export function listTarget(query) {
  return request({
    url: '/map/target/list',
    method: 'get',
    params: query
  })
}

// 查询指标详细
export function getTargetByTable(resultTable) {
  return request({
    url: '/map/target/table/' + resultTable,
    method: 'get'
  })
}

// 查询指标详细
export function getTarget(targetId) {
  return request({
    url: '/map/target/' + parseStrEmpty(targetId),
    method: 'get'
  })
}

// 新增指标
export function addTarget(data) {
  return request({
    url: '/map/target',
    method: 'post',
    data: data
  })
}

// 修改指标
export function updateTarget(data) {
  return request({
    url: '/map/target',
    method: 'put',
    data: data
  })
}

// 删除指标
export function delTarget(targetId) {
  return request({
    url: '/map/target/' + targetId,
    method: 'delete'
  })
}
