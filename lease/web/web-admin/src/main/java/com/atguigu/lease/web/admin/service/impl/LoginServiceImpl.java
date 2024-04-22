package com.atguigu.lease.web.admin.service.impl;

import com.atguigu.lease.common.constant.RedisConstant;
import com.atguigu.lease.common.exception.LeaseException;
import com.atguigu.lease.common.result.ResultCodeEnum;
import com.atguigu.lease.common.utils.JwtUtil;
import com.atguigu.lease.model.entity.SystemUser;
import com.atguigu.lease.model.enums.BaseStatus;
import com.atguigu.lease.web.admin.mapper.SystemUserMapper;
import com.atguigu.lease.web.admin.service.LoginService;
import com.atguigu.lease.web.admin.vo.login.CaptchaVo;
import com.atguigu.lease.web.admin.vo.login.LoginVo;
import com.atguigu.lease.web.admin.vo.system.user.SystemUserInfoVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private SystemUserMapper systemUserMapper;
    @Override
    public CaptchaVo getCaptcha() {
        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 4);
        specCaptcha.setCharType(Captcha.TYPE_DEFAULT);
        //获取创建图像化验证码
        String code = specCaptcha.text().toLowerCase();
        //获取key
        String key = RedisConstant.ADMIN_LOGIN_PREFIX+ UUID.randomUUID();
        String image = specCaptcha.toBase64();
        redisTemplate.opsForValue().set(key,code,RedisConstant.ADMIN_LOGIN_CAPTCHA_TTL_SEC, TimeUnit.SECONDS);
        return new CaptchaVo(image,key);
    }

    @Override
    public String login(LoginVo loginVo) {
        // 判断验证码是否为空
        if(!StringUtils.hasText(loginVo.getCaptchaCode())){
            throw new LeaseException(ResultCodeEnum.ADMIN_CAPTCHA_CODE_NOT_FOUND);
        }
        // 判断验证码是否失效
        String code = redisTemplate.opsForValue().get(loginVo.getCaptchaKey());
        if(code==null){
            throw new LeaseException(ResultCodeEnum.ADMIN_CAPTCHA_CODE_EXPIRED);
        }
        // 判断验证码是否输入错误 用户输入全部转成小写  toLowerCase()
        if(!code.equals(loginVo.getCaptchaCode().toLowerCase())){
            throw new LeaseException(ResultCodeEnum.ADMIN_CAPTCHA_CODE_ERROR);
        }
        // 校验用户是否存在
        LambdaQueryWrapper<SystemUser> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SystemUser::getUsername,loginVo.getUsername());
        SystemUser systemUser = systemUserMapper.selectOne(lambdaQueryWrapper);
        System.out.println("systemUser = " + systemUser.getPassword());
        if(systemUser==null){
            // 用户不存在
            throw  new LeaseException(ResultCodeEnum.ADMIN_ACCOUNT_NOT_EXIST_ERROR);
        }
        // 校验用户是否被禁用
        if(systemUser.getStatus()== BaseStatus.DISABLE){
            // 用户禁用
            throw new LeaseException(ResultCodeEnum.ADMIN_ACCOUNT_DISABLED_ERROR);
        }
        // 校验密码是否正确
        if(!systemUser.getPassword().equals(DigestUtils.md5Hex(loginVo.getPassword()))){
            throw new LeaseException(ResultCodeEnum.ADMIN_ACCOUNT_ERROR);
        }

        //创建并且返回token
        return JwtUtil.createToken(systemUser.getId(),systemUser.getUsername());
    }

    /**
     * 获取个人信息
     * @param userId
     * @return
     */
    @Override
    public SystemUserInfoVo getSystemUserInfoById(Long userId) {
        SystemUser systemUser = systemUserMapper.selectById(userId);
        String name = systemUser.getName();
        String avatarUrl = systemUser.getAvatarUrl();
        SystemUserInfoVo systemUserInfoVo = new SystemUserInfoVo();
        systemUserInfoVo.setAvatarUrl(avatarUrl);
        systemUserInfoVo.setName(name);
        return systemUserInfoVo;
    }
}
