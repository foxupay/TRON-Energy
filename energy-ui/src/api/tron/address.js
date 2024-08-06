import request from '@/utils/request'

// 查询TRON地址列表
export function listAddress(query) {
  return request({
    url: '/tron/address/list',
    method: 'get',
    params: query
  })
}

// 查询TRON地址详细
export function getAddress(id) {
  return request({
    url: '/tron/address/' + id,
    method: 'get'
  })
}

// 新增TRON地址
export function addAddress(data) {
  return request({
    url: '/tron/address',
    method: 'post',
    data: data
  })
}

// 修改TRON地址
export function updateAddress(data) {
  return request({
    url: '/tron/address',
    method: 'put',
    data: data
  })
}

// 删除TRON地址
export function delAddress(id) {
  return request({
    url: '/tron/address/' + id,
    method: 'delete'
  })
}

// 激活TRON地址
export function activate(data) {
  return request({
    url: '/tron/address/activate',
    method: 'post',
    data: data
  })
}

// 资金归集
export function sweep(data) {
  return request({
    url: '/sweep/batch/sweep',
    method: 'post',
    data: data
  })
}

// 同步余额
export function syncBalance(data) {
  return request({
    url: '/tron/address/syncBalance',
    method: 'post',
    data: data
  })
}
