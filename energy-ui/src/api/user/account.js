import request from '@/utils/request'

// 查询客户端账户体系列表
export function listAccount(query) {
  return request({
    url: '/user/account/list',
    method: 'get',
    params: query
  })
}

// 查询客户端账户体系详细
export function getAccount(id) {
  return request({
    url: '/user/account/' + id,
    method: 'get'
  })
}

// 新增客户端账户体系
export function addAccount(data) {
  return request({
    url: '/user/account',
    method: 'post',
    data: data
  })
}

// 修改客户端账户体系
export function updateAccount(data) {
  return request({
    url: '/user/account',
    method: 'put',
    data: data
  })
}

// 删除客户端账户体系
export function delAccount(id) {
  return request({
    url: '/user/account/' + id,
    method: 'delete'
  })
}


// 新增客户端账户体系
export function enable(id) {
  return request({
    url: '/user/account/enable/' + id,
    method: 'post',
  })
}

