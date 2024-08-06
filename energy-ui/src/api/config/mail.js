import request from '@/utils/request'

// 查询邮件配置列表
export function listMail(query) {
  return request({
    url: '/config/mail/list',
    method: 'get',
    params: query
  })
}

// 查询邮件配置详细
export function getMail(id) {
  return request({
    url: '/config/mail/' + id,
    method: 'get'
  })
}

// 新增邮件配置
export function addMail(data) {
  return request({
    url: '/config/mail',
    method: 'post',
    data: data
  })
}

// 修改邮件配置
export function updateMail(data) {
  return request({
    url: '/config/mail',
    method: 'put',
    data: data
  })
}

// 删除邮件配置
export function delMail(id) {
  return request({
    url: '/config/mail/' + id,
    method: 'delete'
  })
}
