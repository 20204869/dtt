import request from '@/utils/request'
import { parseStrEmpty } from "@/utils/ruoyi";

// 新增用户
export function addDirectory(data) {
  return request({
    url: '/system/user',
    method: 'post',
    data: data
  })
}
