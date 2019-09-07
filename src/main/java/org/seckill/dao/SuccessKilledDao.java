package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.SuccessKilled;

public interface SuccessKilledDao {
    /*
     *插入购物明细，可以过滤重复
     *  @param seckillId
     *  @param userPhone
     *  @return 如果影响行数>1,表示插入的结果的行数  0，则表示插入失败
     */
    int insertSuccessKilled(@Param("seckillId") long seckillId,@Param("userPhone") long userPhone);

    /*
    * 根据id查询SuccessKilled并携带秒杀产品对象实体
    * @param seckillId
    * @return
    */
    SuccessKilled queryByIdWithSeckill(@Param("seckillId") long seckillId,@Param("userPhone") long userPhone);
}
