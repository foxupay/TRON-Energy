import request from '@/utils/request'

// 查询TRON交易列表
export function listTransaction(query) {
  return request({
    url: '/tron/transaction/list',
    method: 'get',
    params: query
  })
}

// 查询TRON交易详细
export function getTransaction(id) {
  return request({
    url: '/tron/transaction/' + id,
    method: 'get'
  })
}

// 新增TRON交易
export function addTransaction(data) {
  return request({
    url: '/tron/transaction',
    method: 'post',
    data: data
  })
}

// 修改TRON交易
export function updateTransaction(data) {
  return request({
    url: '/tron/transaction',
    method: 'put',
    data: data
  })
}

// 删除TRON交易
export function delTransaction(id) {
  return request({
    url: '/tron/transaction/' + id,
    method: 'delete'
  })
}

// 删除TRON交易
export function handleTransaction(id) {
  return request({
    url: '/tron/transaction/handle/' + id,
    method: 'post'
  })
}
