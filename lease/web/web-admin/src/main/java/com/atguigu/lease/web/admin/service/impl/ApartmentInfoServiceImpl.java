package com.atguigu.lease.web.admin.service.impl;

import com.atguigu.lease.common.exception.LeaseException;
import com.atguigu.lease.common.result.ResultCodeEnum;
import com.atguigu.lease.model.entity.*;
import com.atguigu.lease.model.enums.ItemType;
import com.atguigu.lease.web.admin.mapper.*;
import com.atguigu.lease.web.admin.service.*;
import com.atguigu.lease.web.admin.vo.apartment.ApartmentDetailVo;
import com.atguigu.lease.web.admin.vo.apartment.ApartmentItemVo;
import com.atguigu.lease.web.admin.vo.apartment.ApartmentQueryVo;
import com.atguigu.lease.web.admin.vo.apartment.ApartmentSubmitVo;
import com.atguigu.lease.web.admin.vo.fee.FeeValueVo;
import com.atguigu.lease.web.admin.vo.graph.GraphVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
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
    @Autowired
    private ApartmentInfoMapper apartmentInfoMapper;
    @Autowired
    private GraphInfoMapper graphInfoMapper;
    @Autowired
    private LabelInfoMapper labelInfoMapper;
    @Autowired
    private FacilityInfoMapper facilityInfoMapper;
    @Autowired
    private FeeKeyMapper feeKeyMapper;
    @Autowired RoomInfoMapper roomInfoMapper;
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

    /**
     * 按条件分页查询
     * @param page
     * @param queryVo
     * @return
     */
    @Override
    public IPage<ApartmentItemVo> pageApartmentItemByQuery(IPage<ApartmentItemVo> page, ApartmentQueryVo queryVo) {

        return apartmentInfoMapper.pageApartmentItemByQuery(page, queryVo);
    }

    @Override
    public ApartmentDetailVo getApartmentDetailById(Long id) {
        // 根据id查询 apartmentInfo
        ApartmentInfo apartmentInfo = this.getById(id);
        if(apartmentInfo==null){
           return null;
        }
        //查询graphInfo
        List<GraphVo> graphVolist = graphInfoMapper.selectListByItemTypeAndId(ItemType.APARTMENT,id);
        //查询LabelInfo
        List<LabelInfo> labelInfoList=labelInfoMapper.selectListByApartmentId(id);
        //查询FacilityInfo
        List<FacilityInfo> facilityInfoList=facilityInfoMapper.selectListByApartmentId(id);
        //查询杂费信息列表
        List<FeeValueVo> feeValueVoList=feeKeyMapper.selectListByApartmentId(id);
        ApartmentDetailVo apartmentDetailVo=new ApartmentDetailVo();
        BeanUtils.copyProperties(apartmentInfo,apartmentDetailVo);
        apartmentDetailVo.setGraphVoList(graphVolist);
        apartmentDetailVo.setLabelInfoList(labelInfoList);
        apartmentDetailVo.setFacilityInfoList(facilityInfoList);
        apartmentDetailVo.setFeeValueVoList(feeValueVoList);
        return apartmentDetailVo;
    }

    /**
     * 根据id删除公寓信息
     * @param id
     */
    @Override
    public void removeApartmentById(Long id) {
        LambdaQueryWrapper<RoomInfo> roomQueryWrapper = new LambdaQueryWrapper<>();
        roomQueryWrapper.eq(RoomInfo::getApartmentId, id);
        Long count = roomInfoMapper.selectCount(roomQueryWrapper);
        if (count > 0) {
            throw new LeaseException(ResultCodeEnum.DELETE_ERROR);
        }
        //1.删除GraphInfo
        LambdaQueryWrapper<GraphInfo> graphQueryWrapper = new LambdaQueryWrapper<>();
        graphQueryWrapper.eq(GraphInfo::getItemType, ItemType.APARTMENT);
        graphQueryWrapper.eq(GraphInfo::getItemId, id);
        graphInfoService.remove(graphQueryWrapper);

        //2.删除ApartmentLabel
        LambdaQueryWrapper<ApartmentLabel> labelQueryWrapper = new LambdaQueryWrapper<>();
        labelQueryWrapper.eq(ApartmentLabel::getApartmentId, id);
        apartmentLabelService.remove(labelQueryWrapper);

        //3.删除ApartmentFacility
        LambdaQueryWrapper<ApartmentFacility> facilityQueryWrapper = new LambdaQueryWrapper<>();
        facilityQueryWrapper.eq(ApartmentFacility::getApartmentId, id);
        apartmentFacilityService.remove(facilityQueryWrapper);

        //4.删除ApartmentFeeValue
        LambdaQueryWrapper<ApartmentFeeValue> feeQueryWrapper = new LambdaQueryWrapper<>();
        feeQueryWrapper.eq(ApartmentFeeValue::getApartmentId, id);
        apartmentFeeValueService.remove(feeQueryWrapper);
    }
}




