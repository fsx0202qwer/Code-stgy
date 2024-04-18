package com.atguigu.lease.web.admin.service.impl;

import com.atguigu.lease.model.entity.SystemPost;
import com.atguigu.lease.model.entity.SystemUser;
import com.atguigu.lease.web.admin.mapper.SystemUserMapper;
import com.atguigu.lease.web.admin.service.SystemPostService;
import com.atguigu.lease.web.admin.service.SystemUserService;
import com.atguigu.lease.web.admin.vo.system.user.SystemUserItemVo;
import com.atguigu.lease.web.admin.vo.system.user.SystemUserQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author liubo
 * @description 针对表【system_user(员工信息表)】的数据库操作Service实现
 * @createDate 2023-07-24 15:48:00
 */
@Service
public class SystemUserServiceImpl extends ServiceImpl<SystemUserMapper, SystemUser>
        implements SystemUserService {
    /**
     * 根据条件分页查询后台用户信息
     * @param page
     * @param queryVo
     * @return
     */
    @Autowired
    private SystemUserMapper systemUserMapper;
    @Autowired
    private SystemPostService systemPostService;
    @Override
    public IPage<SystemUserItemVo> pageSystemUserByQuery(IPage<SystemUser> page, SystemUserQueryVo queryVo) {
        return systemUserMapper.pageSystemUserByQuery(page,queryVo);
    }

    /**
     * 根据ID查询后台用户信息
     * @param id
     * @return
     */
    @Override
    public SystemUserItemVo getSystemUserById(Long id) {

        SystemUser systemUser = getById(id);
        systemUser.setPassword(null);
        //密码是会查询到的!  select = false | mapper service password = null | 自定义方法 select 不写password
        SystemPost systemPost = systemPostService.getById(systemUser.getPostId());

        SystemUserItemVo systemUserItemVo = new SystemUserItemVo();
        BeanUtils.copyProperties(systemUser,systemUserItemVo);
        systemUserItemVo.setPostName(systemPost.getName());
        return systemUserItemVo;
    }
}




