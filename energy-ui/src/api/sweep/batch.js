import request from '@/utils/request'

// 查询归集批次列表
export function listBatch(query) {
  return request({
    url: '/sweep/batch/list',
    method: 'get',
    params: query
  })
}

// 查询归集批次详细
export function getBatch(id) {
  return request({
    url: '/sweep/batch/' + id,
    method: 'get'
  })
}

// 新增归集批次
export function addBatch(data) {
  return request({
    url: '/sweep/batch',
    method: 'post',
    data: data
  })
}

// 新增归集批次
export function importAddress(id) {
  return request({
    url: '/sweep/batch/import/' + id,
    method: 'post',
  })
}

// 修改归集批次
export function updateBatch(data) {
  return request({
    url: '/sweep/batch',
    method: 'put',
    data: data
  })
}

// 删除归集批次
export function delBatch(id) {
  return request({
    url: '/sweep/batch/' + id,
    method: 'delete'
  })
}
