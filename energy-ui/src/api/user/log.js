import request from '@/utils/request'

// 查询用户余额记录列表
export function listLog(query) {
  return request({
    url: '/user/log/list',
    method: 'get',
    params: query
  })
}

// 查询用户余额记录详细
export function getLog(id) {
  return request({
    url: '/user/log/' + id,
    method: 'get'
  })
}

// 新增用户余额记录
export function addLog(data) {
  return request({
    url: '/user/log',
    method: 'post',
    data: data
  })
}

// 修改用户余额记录
export function updateLog(data) {
  return request({
    url: '/user/log',
    method: 'put',
    data: data
  })
}

// 删除用户余额记录
export function delLog(id) {
  return request({
    url: '/user/log/' + id,
    method: 'delete'
  })
}
