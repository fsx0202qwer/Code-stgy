package com.atguigu.lease.web.admin.schedule;

import com.atguigu.lease.model.entity.LeaseAgreement;
import com.atguigu.lease.model.enums.LeaseStatus;
import com.atguigu.lease.web.admin.service.LeaseAgreementService;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * ClassName:ScheduledTasks
 * Package: IntelliJ IDEA
 * Description:
 *
 * @Author fsx
 * @Create 2024/4/17 20:07
 * @Version 1.0
 */
@Component
public class ScheduledTasks {

   @Autowired
   private LeaseAgreementService leaseAgreementService;

   @Scheduled(cron = "0 0 0 * * *")
   public void checkLeaseStatus() {

      LambdaUpdateWrapper<LeaseAgreement> updateWrapper = new LambdaUpdateWrapper<>();
      Date now = new Date();
      updateWrapper.le(LeaseAgreement::getLeaseEndDate, now);
      updateWrapper.in(LeaseAgreement::getStatus, LeaseStatus.SIGNED, LeaseStatus.WITHDRAWING);
      updateWrapper.set(LeaseAgreement::getStatus, LeaseStatus.EXPIRED);

      leaseAgreementService.update(updateWrapper);
   }
}
