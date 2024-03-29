import request from '@/utils/request'

// 查询fpsmonitor列表
export function listMon(query) {
  return request({
    url: '/system/mon/list',
    method: 'get',
    params: query
  })
}

// 查询fpsmonitor详细
export function getMon(id) {
  return request({
    url: '/system/mon/' + id,
    method: 'get'
  })
}

// 新增fpsmonitor
export function addMon(data) {
  return request({
    url: '/system/mon',
    method: 'post',
    data: data
  })
}

// 修改fpsmonitor
export function updateMon(data) {
  return request({
    url: '/system/mon',
    method: 'put',
    data: data
  })
}

// 删除fpsmonitor
export function delMon(id) {
  return request({
    url: '/system/mon/' + id,
    method: 'delete'
  })
}
