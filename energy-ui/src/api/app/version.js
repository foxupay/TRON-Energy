import request from '@/utils/request'

// 查询APP版本列表
export function listVersion(query) {
  return request({
    url: '/application/version/list',
    method: 'get',
    params: query
  })
}

// 查询APP版本详细
export function getVersion(id) {
  return request({
    url: '/application/version/' + id,
    method: 'get'
  })
}

// 新增APP版本
export function addVersion(data) {
  return request({
    url: '/application/version',
    method: 'post',
    data: data
  })
}

// 修改APP版本
export function updateVersion(data) {
  return request({
    url: '/application/version',
    method: 'put',
    data: data
  })
}

// 删除APP版本
export function delVersion(id) {
  return request({
    url: '/application/version/' + id,
    method: 'delete'
  })
}
