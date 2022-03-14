import request from '@/utils/request'

// 查询HIVE 数据库
export function treeselect() {
  return request({
    url: '/map/meta/dbList',
    method: 'get'
  })
}

// 查询表列表
export function listTable(query) {
  return request({
    url: '/map/meta/tableList',
    method: 'get',
    params: query
  })
}


// 查询表列表
export function listDbTable() {
  return request({
    url: '/map/meta/dbTable',
    method: 'get'
  })
}

// 查询表 详细信息
export function getTableDetail(tableId) {
  return request({
    url: '/map/meta/metaTable/' + tableId,
    method: 'get'
  })
}

export function getTableList(dbId) {
  return request({
    url: '/map/meta/tableList/' + dbId,
    method: 'get'
  })
}


export function colList(tableId) {
  return request({
    url: '/map/meta/cols/' + tableId,
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


