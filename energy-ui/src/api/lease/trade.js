import request from '@/utils/request'

// 查询能量租用订单列表
export function listTrade(query) {
  return request({
    url: '/lease/trade/list',
    method: 'get',
    params: query
  })
}

// 查询能量租用订单详细
export function getTrade(id) {
  return request({
    url: '/lease/trade/' + id,
    method: 'get'
  })
}

// 新增能量租用订单
export function addTrade(data) {
  return request({
    url: '/lease/trade',
    method: 'post',
    data: data
  })
}

// 修改能量租用订单
export function updateTrade(data) {
  return request({
    url: '/lease/trade',
    method: 'put',
    data: data
  })
}

// 删除能量租用订单
export function delTrade(id) {
  return request({
    url: '/lease/trade/' + id,
    method: 'delete'
  })
}
