import request from '@/utils/request'

// 查询接口秘钥列表
export function listKey(query) {
  return request({
    url: '/app/key/list',
    method: 'get',
    params: query
  })
}

// 查询接口秘钥详细
export function getKey(id) {
  return request({
    url: '/app/key/' + id,
    method: 'get'
  })
}

// 新增接口秘钥
export function addKey(data) {
  return request({
    url: '/app/key',
    method: 'post',
    data: data
  })
}

// 修改接口秘钥
export function updateKey(data) {
  return request({
    url: '/app/key',
    method: 'put',
    data: data
  })
}

// 删除接口秘钥
export function delKey(id) {
  return request({
    url: '/app/key/' + id,
    method: 'delete'
  })
}
