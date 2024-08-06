import request from '@/utils/request'

// 查询代理利率列表
export function listAgent(query) {
  return request({
    url: '/user/proxy/list',
    method: 'get',
    params: query
  })
}

// 查询代理利率详细
export function getAgent(id) {
  return request({
    url: '/user/proxy/' + id,
    method: 'get'
  })
}

// 新增代理利率
export function addAgent(data) {
  return request({
    url: '/user/proxy',
    method: 'post',
    data: data
  })
}

// 修改代理利率
export function updateAgent(data) {
  return request({
    url: '/user/proxy',
    method: 'put',
    data: data
  })
}

// 删除代理利率
export function delAgent(id) {
  return request({
    url: '/user/proxy/' + id,
    method: 'delete'
  })
}
