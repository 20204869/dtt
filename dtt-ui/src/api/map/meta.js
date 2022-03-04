import request from '@/utils/request'

// 查询HIVE 数据库
export function treeselect() {
  return request({
    url: '/map/meta/dbList',
    method: 'get'
  })
}

// 查询用户列表
export function listTable(query) {
  return request({
    url: '/map/meta/tableList',
    method: 'get',
    params: query
  })
}

// 查询表 详细信息
export function getTableDetail(tableId) {
  return request({
    url: '/map/meta/metaTable/' + tableId,
    method: 'get'
  })
}


// 查询表关系 详细信息
export function getTableRelation(tableName) {
  return request({
    url: '/map/meta/tableRelation/' + tableName,
    method: 'get'
  })
}
