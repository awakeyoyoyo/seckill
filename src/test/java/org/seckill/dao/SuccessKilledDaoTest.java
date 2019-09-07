package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.SuccessKilled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessKilledDaoTest {
     @Autowired
     private  SuccessKilledDao successKilledDao;
    @Test
    public void insertSuccessKilled() {
        Long id=1000L;
        Long phone=13316311153L;
        int insertCount=successKilledDao.insertSuccessKilled(id,phone);
        System.out.println("insertCount="+insertCount);
        //第一次执行insertCount=1
        //第二次执行insertCount=0
    }

    @Test
    public void queryByIdWithSeckill() {
        Long id=1000L;
        Long phone=13316311153L;
        SuccessKilled successKilled=successKilledDao.queryByIdWithSeckill(id,phone);
        System.out.println(successKilled);
        System.out.println(successKilled.getSeckill());
        /*
        * SuccessKilled{seckillId=1000, userPhone=13316311153, state=0,
        * createTime=Sat Sep 15 17:13:25 CST 2018}
        * Seckill{seckillId=1000, name='100元秒杀ipad', number=100,
        * startTime=Sun Nov 01 00:00:00 CST 2015, endTime=Mon Nov 02 00:00:00 CST 2015,
         * createTime=Fri Sep 14 18:43:17 CST 2018}*/
    }
}