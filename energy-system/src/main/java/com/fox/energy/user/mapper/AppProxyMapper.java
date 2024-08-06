package com.fox.energy.user.mapper;

import java.util.List;

import com.fox.energy.user.domain.AppProxy;

/**
 * 代理利率Mapper接口
 *
 * @author IEME
 * @date 2024-07-15
 */
public interface AppProxyMapper {
    /**
     * 查询代理利率
     *
     * @param id 代理利率主键
     * @return 代理利率
     */
    AppProxy selectById(Integer id);

    AppProxy selectByUserId(Integer userId);

    /**
     * 查询代理利率列表
     *
     * @param appProxy 代理利率
     * @return 代理利率集合
     */
    List<AppProxy> selectList(AppProxy appProxy);

    /**
     * 新增代理利率
     *
     * @param appProxy 代理利率
     * @return 结果
     */
    int insert(AppProxy appProxy);

    /**
     * 修改代理利率
     *
     * @param appProxy 代理利率
     * @return 结果
     */
    int update(AppProxy appProxy);

    /**
     * 删除代理利率
     *
     * @param id 代理利率主键
     * @return 结果
     */
    int deleteById(Integer id);

    /**
     * 批量删除代理利率
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteByIds(Integer[] ids);
}
