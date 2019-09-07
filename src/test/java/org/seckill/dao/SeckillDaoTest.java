package org.seckill.dao;

import ch.qos.logback.core.net.SyslogOutputStream;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.Seckill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;



import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
/*
* 配置spring和junit整合为了
* junit启动时加载springIOC容器
* spring-test，junit
* */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {
        //注入Dao实现类依赖

    @Autowired
    private  SeckillDao seckillDao;
    @Test
    public void queryById() {
        long id=1001;
        Seckill seckill=null;
        seckill=seckillDao.queryById(id);
        System.out.println(seckill.getName());
        System.out.println(seckill);
//     200元秒杀小米5
//     Seckill{
//     seckillId=1001,
//     name='200元秒杀小米5',
//     number=100,
//     startTime=Sun Nov 01 00:00:00 CST 2015,
//     endTime=Mon Nov 02 00:00:00 CST 2015,
//     createTime=Fri Sep 14 18:43:17 CST 2018
// }
    }
    @Test
    public void queryAll() {
        /*org.mybatis.spring.MyBatisSystemException: nested exception is
         org.apache.ibatis.binding.BindingException: Parameter 'offest' not found.
         Available parameters are [0, 1, param1, param2]
        * */
        //List<Seckill> seckills=seckillDao.queryAll(0,100);
        //java没有保存形参的记录：i
        // queryAll(int offet,int limit)-->queryAll(arg1,arg2)
        //需要告诉mybatis 哪个位置的参数是什么名字，单个参数的时候随意
        //在接口里注解@Param("") 绑定名字
        List<Seckill> seckills=seckillDao.queryAll(0,100);
        for (Seckill s:seckills)
        {
            System.out.println(s);
        }
//        Seckill{seckillId=1000, name='100元秒杀ipad', number=100, startTime=Sun Nov 01 00:00:00 CST 2015, endTime=Mon Nov 02 00:00:00 CST 2015, createTime=Fri Sep 14 18:43:17 CST 2018}
//        Seckill{seckillId=1001, name='200元秒杀小米5', number=100, startTime=Sun Nov 01 00:00:00 CST 2015, endTime=Mon Nov 02 00:00:00 CST 2015, createTime=Fri Sep 14 18:43:17 CST 2018}
//        Seckill{seckillId=1002, name='300元秒杀iphone6s', number=100, startTime=Sun Nov 01 00:00:00 CST 2015, endTime=Mon Nov 02 00:00:00 CST 2015, createTime=Fri Sep 14 18:43:17 CST 2018}
    }
    @Test
    public void reduceNumber() {
        Date killtime=new Date();
        int updateCount = seckillDao.reduceNumber(1000L,killtime);
        System.out.println("updateCount="+updateCount);
    }

}
