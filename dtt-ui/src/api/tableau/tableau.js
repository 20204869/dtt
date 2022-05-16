import request from '@/utils/request'
import { parseStrEmpty } from "@/utils/ruoyi";

// 查询tableau 视图
export function tableauViewUrl(workbookId) {
  return request({
    url: '/tableau/web/viewUrl/'+ parseStrEmpty(workbookId),
    method: 'get'
  })
}

// 查询tableau 项目列表
export function allProject(query) {
  return request({
    url: '/tableau/web/projectList/',
    method: 'get',
    params: query
  })
}

// 根据project ID获取 workbook 列表
export function workbookByProject(projectId) {
  return request({
    url: '/tableau/web/workbookList/' + parseStrEmpty(projectId),
    method: 'get'
  })
}

// 根据workbook ID获取 视图 列表
export function viewByWorkbookId(workbookId) {
  return request({
    url: '/tableau/web/viewList/' + parseStrEmpty(workbookId),
    method: 'get'
  })
}
