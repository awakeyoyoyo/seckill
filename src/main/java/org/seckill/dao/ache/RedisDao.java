package org.seckill.dao.ache;
import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import org.seckill.entity.Seckill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;



public class RedisDao {
    private  final Logger logger= LoggerFactory.getLogger(this.getClass());
    private JedisPool jedisPool;
    //运行期=，=  做一个模式= =，类似做一个模型把；
    private RuntimeSchema<Seckill> schema=RuntimeSchema.createFrom(Seckill.class);
    public RedisDao(String ip,int port){
        //连接redis
        jedisPool=new JedisPool(ip,port);
    }
    //缓存有的话就get一个
    public Seckill getSeckill(long seckillId){
      //redis操作逻辑
        try {
            Jedis jedis=jedisPool.getResource();
            try{
                    //redis是键值对存储，这里设置key为 seckill：id
                    String key="seckill:"+seckillId;
                    //并没有实现内部序列化操作
                    //get->byte[]->反序列化->Object(seckill)
                    //采用自定义序列化
                    //protostuff:pojo
                //从redis中获取缓存
                byte[] bytes=jedis.get(key.getBytes());
                //缓存重获取到
                if (bytes!=null){
                    //空对象
                    Seckill seckill=schema.newMessage();
                    //将序列化对象缓存反序列化成对象
                    ProtostuffIOUtil.mergeFrom(bytes,seckill,schema);
                    return seckill;
                }

            }
            catch (Exception e){
                logger.error(e.getMessage(),e);
            }
            finally {
                jedis.close();
            }

        }
        catch (Exception e)
        {
            logger.error(e.getMessage(),e);
        }
        return null;
    }
    //缓存无的时候set一个seckill进redis
    public String putSeckill(Seckill seckill){
        //set Object(seckill)->序列化->byte[]
        try{
            //获取redis里面得内容
            Jedis jedis=jedisPool.getResource();
            try{
               String key="seckill:"+seckill.getSeckillId();
               //根据键值对获取对象得字节码
               byte[] bytes=ProtostuffIOUtil.toByteArray(seckill,schema,
                       LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
               //超时缓存

                int timeout=60*60;//一小时
               String result=jedis.setex(key.getBytes(),timeout,bytes);
                //restult 成功则返回ok ，不成功则返回其他
            }
            catch (Exception e)
            {
                logger.error(e.getMessage(),e);
            }
            finally {
                jedis.close();
            }
        }catch (Exception e)
        {
            logger.error(e.getMessage(),e);
        }
        return null;
    }

}
