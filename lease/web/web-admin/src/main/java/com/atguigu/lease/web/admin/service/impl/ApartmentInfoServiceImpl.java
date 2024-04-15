package com.atguigu.lease.web.admin.service.impl;

import com.atguigu.lease.model.entity.*;
import com.atguigu.lease.model.enums.ItemType;
import com.atguigu.lease.web.admin.mapper.ApartmentInfoMapper;
import com.atguigu.lease.web.admin.service.*;
import com.atguigu.lease.web.admin.vo.apartment.ApartmentSubmitVo;
import com.atguigu.lease.web.admin.vo.graph.GraphVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liubo
 * @description 针对表【apartment_info(公寓信息表)】的数据库操作Service实现
 * @createDate 2023-07-24 15:48:00
 */
@Service
public class ApartmentInfoServiceImpl extends ServiceImpl<ApartmentInfoMapper, ApartmentInfo>
        implements ApartmentInfoService {
    @Autowired
    private GraphInfoService graphInfoService;
    @Autowired
    private ApartmentFacilityService apartmentFacilityService;
    @Autowired
    private ApartmentFeeValueService apartmentFeeValueService;
    @Autowired
    private ApartmentLabelService apartmentLabelService;

    @Override
    public void saveOrUpdateApartment(ApartmentSubmitVo apartmentSubmitVo) {
         //先判断是保存还是更新
        boolean isUpdate=apartmentSubmitVo.getId()!=null;
        //更新操作需要先做删除
        if(isUpdate){
            // 1.删除图片列表
            LambdaQueryWrapper<GraphInfo> graphInfoQueryWrapper=new LambdaQueryWrapper<>();
            graphInfoQueryWrapper.eq(GraphInfo::getItemType, ItemType.APARTMENT);
            graphInfoQueryWrapper.eq(GraphInfo::getItemId,apartmentSubmitVo.getId());
            graphInfoService.remove(graphInfoQueryWrapper);
            // 2.删除配套信息
            LambdaQueryWrapper<ApartmentFacility> apartmentFacilityLambdaQueryWrapper=new LambdaQueryWrapper<>();
            apartmentFacilityLambdaQueryWrapper.eq(ApartmentFacility::getApartmentId,apartmentSubmitVo.getId());
            apartmentFacilityService.remove(apartmentFacilityLambdaQueryWrapper);
            // 3.删除杂费信息
            LambdaQueryWrapper<ApartmentFeeValue> apartmentFeeValueLambdaQueryWrapper=new LambdaQueryWrapper<>();
            apartmentFeeValueLambdaQueryWrapper.eq(ApartmentFeeValue::getApartmentId,apartmentSubmitVo.getId());
            apartmentFeeValueService.remove(apartmentFeeValueLambdaQueryWrapper);
            // 4.删除标签信息
            LambdaQueryWrapper<ApartmentLabel> apartmentLabelLambdaQueryWrapper=new LambdaQueryWrapper<>();
            apartmentLabelLambdaQueryWrapper.eq(ApartmentLabel::getApartmentId,apartmentSubmitVo.getId());
            apartmentLabelService.remove(apartmentLabelLambdaQueryWrapper);
        }
        // 保存和更新
        //1.插入图片列表
        List<GraphVo> graphVoList = apartmentSubmitVo.getGraphVoList();
        if(!CollectionUtils.isEmpty(graphVoList)){
            ArrayList<GraphInfo> graphInfoArrayList=new ArrayList<>();
            for (GraphVo graphVo : graphVoList) {
                GraphInfo graphInfo = new GraphInfo();
                graphInfo.setItemType(ItemType.APARTMENT);
                graphInfo.setItemId(apartmentSubmitVo.getId());
                graphInfo.setName(graphVo.getName());
                graphInfo.setUrl(graphVo.getUrl());
                graphInfoArrayList.add(graphInfo);
            }
        }
        //2.插入配套列表
        List<Long> facilityInfoIdList = apartmentSubmitVo.getFacilityInfoIds();
        if (!CollectionUtils.isEmpty(facilityInfoIdList)){
            ArrayList<ApartmentFacility> facilityList = new ArrayList<>();
            for (Long facilityId : facilityInfoIdList) {
                ApartmentFacility build = ApartmentFacility.builder().facilityId(facilityId).apartmentId(apartmentSubmitVo.getId()).build();
                facilityList.add(build);
            }
            apartmentFacilityService.saveBatch(facilityList);
        }
        //3.插入标签列表
        List<Long> labelIds = apartmentSubmitVo.getLabelIds();
        if(!CollectionUtils.isEmpty(labelIds)){
            ArrayList<ApartmentLabel> apartmentLabelList=new ArrayList<>();
            for (Long labelId : labelIds) {
                ApartmentLabel build = ApartmentLabel.builder().labelId(labelId).apartmentId(apartmentSubmitVo.getId()).build();
                apartmentLabelList.add(build);
            }
        }
        //4.插入杂费列表
        List<Long> feeValueIds = apartmentSubmitVo.getFeeValueIds();
        if(!CollectionUtils.isEmpty(feeValueIds)){
            ArrayList<ApartmentFeeValue> apartmentFeeValueArrayList=new ArrayList<>();
            for (Long feeValueId : feeValueIds) {
                ApartmentFeeValue build = ApartmentFeeValue.builder().apartmentId(apartmentSubmitVo.getId()).feeValueId(feeValueId).build();
                apartmentFeeValueArrayList.add(build);
            }
        }
    }
}




