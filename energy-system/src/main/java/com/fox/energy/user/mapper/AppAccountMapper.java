package com.fox.energy.user.mapper;

import java.util.List;

import com.fox.energy.user.domain.AppAccount;
import org.apache.ibatis.annotations.Param;

/**
 * 客户端账户体系Mapper接口
 *
 * @author IEME
 * @date 2024-04-23
 */
public interface AppAccountMapper {
    /**
     * 查询客户端账户体系
     *
     * @param id 客户端账户体系主键
     * @return 客户端账户体系
     */
    AppAccount selectById(Integer id);

    AppAccount selectByEmail(String email);

    /**
     * 查询客户端账户体系列表
     *
     * @param appAccount 客户端账户体系
     * @return 客户端账户体系集合
     */
    List<AppAccount> selectList(AppAccount appAccount);

    /**
     * 新增客户端账户体系
     *
     * @param appAccount 客户端账户体系
     * @return 结果
     */
    int insert(AppAccount appAccount);

    /**
     * 修改客户端账户体系
     *
     * @param appAccount 客户端账户体系
     * @return 结果
     */
    int update(AppAccount appAccount);

    /**
     * 删除客户端账户体系
     *
     * @param id 客户端账户体系主键
     * @return 结果
     */
    int deleteById(Long id);

    /**
     * 批量删除客户端账户体系
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteByIds(Long[] ids);

    int resetPwd(@Param("id") Integer id, @Param("password") String password);
}
