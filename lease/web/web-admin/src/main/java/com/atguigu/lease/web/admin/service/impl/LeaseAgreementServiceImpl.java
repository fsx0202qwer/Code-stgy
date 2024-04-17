package com.atguigu.lease.web.admin.service.impl;

import com.atguigu.lease.model.entity.*;
import com.atguigu.lease.web.admin.mapper.*;
import com.atguigu.lease.web.admin.service.LeaseAgreementService;
import com.atguigu.lease.web.admin.vo.agreement.AgreementQueryVo;
import com.atguigu.lease.web.admin.vo.agreement.AgreementVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author liubo
 * @description 针对表【lease_agreement(租约信息表)】的数据库操作Service实现
 * @createDate 2023-07-24 15:48:00
 */
@Service
public class LeaseAgreementServiceImpl extends ServiceImpl<LeaseAgreementMapper, LeaseAgreement>
        implements LeaseAgreementService {
    /**
     * 根据条件查询租约列表
     * @param page
     * @param queryVo
     * @return
     */
    @Autowired
    private LeaseAgreementMapper leaseAgreementMapper;
    @Autowired
    private ApartmentInfoMapper apartmentInfoMapper;
    @Autowired
    private RoomInfoMapper roomInfoMapper;
    @Autowired
    private PaymentTypeMapper paymentTypeMapper;
    @Autowired
    private LeaseTermMapper leaseTermMapper;
    @Override
    public IPage<AgreementVo> pageAgreementByQuery(IPage<AgreementVo> page, AgreementQueryVo queryVo) {
        return leaseAgreementMapper.pageAgreementByQuery(page,queryVo);
    }

    /**
     * 根据id查询租约信息
     * @param id
     * @return
     */
    @Override
    public AgreementVo getAgreementById(Long id) {
        //1.查询租约信息表
        LeaseAgreement leaseAgreement = leaseAgreementMapper.selectById(id);

        //2.查询签约公寓信息
        ApartmentInfo apartmentInfo = apartmentInfoMapper.selectById(leaseAgreement.getApartmentId());

        //3.查询签约房间信息
        RoomInfo roomInfo = roomInfoMapper.selectById(leaseAgreement.getRoomId());

        //4.查询支付方式信息
        PaymentType paymentType = paymentTypeMapper.selectById(leaseAgreement.getPaymentTypeId());

        //5.查询租期信息
        LeaseTerm leaseTerm = leaseTermMapper.selectById(leaseAgreement.getLeaseTermId());

        AgreementVo agreementVo=new AgreementVo();
        BeanUtils.copyProperties(leaseAgreement,agreementVo);
        agreementVo.setApartmentInfo(apartmentInfo);
        agreementVo.setRoomInfo(roomInfo);
        agreementVo.setPaymentType(paymentType);
        agreementVo.setLeaseTerm(leaseTerm);
        return agreementVo;
    }
}




