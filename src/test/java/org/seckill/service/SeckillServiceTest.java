package org.seckill.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import org.slf4j.Logger;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath:spring/spring-dao.xml",
        "classpath:spring/spring-service.xml"})
public class SeckillServiceTest {
    private  final Logger logger= LoggerFactory.getLogger(this.getClass());

    @Autowired
    private  SeckillService seckillService;
    @Test
    public void getSeckillList() {
        List<Seckill> list=seckillService.getSeckillList();
        logger.info("list={}",list);
        //Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@2e3cdec2]
    }

    @Test
    public void getById() {
        long id=1000;
        Seckill seckill=seckillService.getById(id);
        logger.info("seckill={}",seckill);
    }
//测试代码完整逻辑，注意可重复执行
    @Test
    public void tetsSeckillLogic() throws  Exception{
        long id=1000;
        Exposer exposer=seckillService.exporSeckillUrl(id);
        if(exposer.isExposed())
        {
            logger.info("exposer={}",exposer);
            long phone=13316311166L;
            String md5=exposer.getMd5();
            try{
                SeckillExecution execution=seckillService.executeSecill(id,phone,md5);
                logger.info("result={}",execution);
            }
            catch (RepeatKillException e1)
            {
                logger.error(e1.getMessage());
            }
            catch (SeckillCloseException e2)
            {
                logger.error(e2.getMessage());
            }
        }
        else {
            //秒杀未开启
            logger.warn("exposer={}",exposer);
        }

        //exposer=Exposer{exposed=true,
        // md5='df5f93eed3534aba5291ae9ec7700557',
        // seckillId=1000, now=0, start=0, end=0}
     }
     @Test
    public void executeSeckillProdure(){
        long seckillId=1002;
        long phone=13316311156L;
        Exposer exposer=seckillService.exporSeckillUrl(seckillId);
        if (exposer.isExposed()){
            String md5=exposer.getMd5();
            SeckillExecution execution=seckillService.executeSecillProcedure(seckillId,phone,md5);
            logger.info(execution.getStateInfo());
        }
        String md5=null;
     }
}
