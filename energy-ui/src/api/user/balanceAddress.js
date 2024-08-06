import request from '@/utils/request'

// 查询地址余额列表
export function listBalanceAddress(query) {
  return request({
    url: '/user/balance/address/list',
    method: 'get',
    params: query
  })
}

// 查询地址余额详细
export function getBalanceAddress(id) {
  return request({
    url: '/user/balance/address/' + id,
    method: 'get'
  })
}

// 新增地址余额
export function addBalanceAddress(data) {
  return request({
    url: '/user/balance/address',
    method: 'post',
    data: data
  })
}

// 修改地址余额
export function updateBalanceAddress(data) {
  return request({
    url: '/user/balance/address',
    method: 'put',
    data: data
  })
}

// 删除地址余额
export function delBalanceAddress(id) {
  return request({
    url: '/user/balance/address/' + id,
    method: 'delete'
  })
}
