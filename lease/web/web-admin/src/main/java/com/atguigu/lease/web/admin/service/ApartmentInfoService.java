package com.atguigu.lease.web.admin.service;

import com.atguigu.lease.model.entity.ApartmentInfo;
import com.atguigu.lease.web.admin.vo.apartment.ApartmentDetailVo;
import com.atguigu.lease.web.admin.vo.apartment.ApartmentItemVo;
import com.atguigu.lease.web.admin.vo.apartment.ApartmentQueryVo;
import com.atguigu.lease.web.admin.vo.apartment.ApartmentSubmitVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author liubo
* @description 针对表【apartment_info(公寓信息表)】的数据库操作Service
* @createDate 2023-07-24 15:48:00
*/
public interface ApartmentInfoService extends IService<ApartmentInfo> {
    /**
     * 保存或更新公寓信息
     * @param apartmentSubmitVo
     */
    void saveOrUpdateApartment(ApartmentSubmitVo apartmentSubmitVo);

    /**
     * 根据条件分页查询公寓列表
     * @param page
     * @param queryVo
     */
    IPage<ApartmentItemVo> pageApartmentItemByQuery(IPage<ApartmentItemVo> page, ApartmentQueryVo queryVo);

    /**
     * 根据id获取公寓详细信息
     * @param id
     * @return
     */
    ApartmentDetailVo getApartmentDetailById(Long id);

    /**
     * 根据id删除公寓信息
     * @param id
     */
    void removeApartmentById(Long id);
}
