import request from '@/utils/request'

// 查询归集记录列表
export function listLog(query) {
  return request({
    url: '/sweep/log/list',
    method: 'get',
    params: query
  })
}

// 查询归集记录详细
export function getLog(id) {
  return request({
    url: '/sweep/log/' + id,
    method: 'get'
  })
}

// 新增归集记录
export function addLog(data) {
  return request({
    url: '/sweep/log',
    method: 'post',
    data: data
  })
}

// 修改归集记录
export function updateLog(data) {
  return request({
    url: '/sweep/log',
    method: 'put',
    data: data
  })
}

// 删除归集记录
export function delLog(id) {
  return request({
    url: '/sweep/log/' + id,
    method: 'delete'
  })
}
