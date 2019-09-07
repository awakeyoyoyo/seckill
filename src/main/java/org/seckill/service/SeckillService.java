package org.seckill.service;

import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;

import java.util.List;

/*
* 业务接口：站在‘使用者’角度设计接口
* 三个方面：方法定义力度，参数，返回类型（return 类型= =友好-，-）
* */
public interface SeckillService {
    /**
     * 查询所有的秒杀记录
     * @return
     */
    List<Seckill> getSeckillList();

    /**
     *查询单个秒杀记录
     * @param seckillId
     * @return
     */
    Seckill getById(long seckillId);

    /**
     * 秒杀开启是输出秒杀接口地址
     * 或者没有该商品 直接返回商品id
     * 否则输出系统时间和秒杀时间
     * @param seckillId
     */
    Exposer exporSeckillUrl(long seckillId);

    /**
     * 执行秒杀操作
     * @param seckillId
     * @param userPhone
     * @param md5
     */
    SeckillExecution executeSecill(long seckillId, long userPhone, String md5)
    throws SeckillCloseException,RepeatKillException,SeckillException;
    /**
     * 执行秒杀操作 by存储过程
     * @param seckillId
     * @param userPhone
     * @param md5
     */
    SeckillExecution executeSecillProcedure(long seckillId, long userPhone, String md5);


}
