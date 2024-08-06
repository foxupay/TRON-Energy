package com.fox.energy.conf.mapper;

import java.util.List;

import com.fox.energy.conf.domain.AppConfMail;

/**
 * 邮件配置Mapper接口
 *
 * @author IEME
 * @date 2024-07-22
 */
public interface AppConfMailMapper {
    /**
     * 查询邮件配置
     *
     * @param id 邮件配置主键
     * @return 邮件配置
     */
        AppConfMail selectById(Long id);

    /**
     * 查询邮件配置列表
     *
     * @param appConfMail 邮件配置
     * @return 邮件配置集合
     */
    List<AppConfMail> selectList(AppConfMail appConfMail);

    /**
     * 新增邮件配置
     *
     * @param appConfMail 邮件配置
     * @return 结果
     */
    int insert(AppConfMail appConfMail);

    /**
     * 修改邮件配置
     *
     * @param appConfMail 邮件配置
     * @return 结果
     */
    int update(AppConfMail appConfMail);

    /**
     * 删除邮件配置
     *
     * @param id 邮件配置主键
     * @return 结果
     */
    int deleteById(Long id);

    /**
     * 批量删除邮件配置
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteByIds(Long[] ids);
}
