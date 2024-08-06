import request from '@/utils/request'

// 查询能量通道列表
export function listChannel(query) {
  return request({
    url: '/channel/channel/list',
    method: 'get',
    params: query
  })
}

// 查询能量通道详细
export function getChannel(id) {
  return request({
    url: '/channel/channel/' + id,
    method: 'get'
  })
}

// 新增能量通道
export function addChannel(data) {
  return request({
    url: '/channel/channel',
    method: 'post',
    data: data
  })
}

// 修改能量通道
export function updateChannel(data) {
  return request({
    url: '/channel/channel',
    method: 'put',
    data: data
  })
}

// 删除能量通道
export function delChannel(id) {
  return request({
    url: '/channel/channel/' + id,
    method: 'delete'
  })
}
