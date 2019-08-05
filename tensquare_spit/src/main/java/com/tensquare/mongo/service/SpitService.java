package com.tensquare.mongo.service;

import com.tensquare.mongo.dao.SpitDao;
import com.tensquare.mongo.pojo.Spit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import util.IdWorker;

import java.util.Date;
import java.util.List;

@Service
public class SpitService {
    @Autowired
    private SpitDao spitDao;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Spit> findAll(){
        return spitDao.findAll();
    }

    public Spit findById(String id){
        return spitDao.findById(id).get();
    }

    public void save(Spit spit){
        spit.set_id(idWorker.nextId()+"");
        //初始化数据完善
        spit.setPublishtime(new Date());//发布日期
        spit.setVisits(0);//浏览量
        spit.setShare(0);//分享数
        spit.setThumbup(0);//点赞数
        spit.setComment(0);//回复数
        spit.setState("1");//状态
        //判断当前吐槽是否有父节点
        if(spit.getParentid()!=null && !"".equals(spit.getParentid())){
            //给父节点吐槽的回复数加一
            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(spit.getParentid()));
            Update update = new Update();
            update.inc("comment", 1);
            mongoTemplate.updateFirst(query, update, "spit");
        }
        spitDao.save(spit);
    }

    public void update(Spit spit){
        spitDao.save(spit);
    }

    public void deleteById(String id){
        spitDao.deleteById(id);
    }


    public Page<Spit> pageQuery(String parentid, int page, int size){
        Pageable pageable = PageRequest.of(page-1, size);
        return spitDao.findByParentid(parentid, pageable);
    }

    //db.spit.update({_id:"2"},{$inc:{visits:NumberInt(1)}} )
    public void addthumbup(String id){
        //方式一
//        Spit spit = spitDao.findById(id).get();
//        spit.setThumbup(spit.getThumbup()+1);
//        spitDao.save(spit);

        //存储过程和存储函数的优势？
        //存储过程相当于把业务逻辑写到数据库端。
        //加入java端有一个业务逻辑需要十次数据库操作，
        //那么我们正常来说就需要链接数据库十次
        //链接数据库频繁就意味要牺牲效率。
        //如果用存储过程把业务逻辑写到数据库端
        //只需要链接一次数据库就可以完成十步操作
        //默认情况下存储过程无法并发，但是可以优化。
        //而且存储过程和存储函数使用的编程语言是pl/sql是面向过程的。维护起来特别麻烦。
        //方式二
        //封装的是查询条件
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        //封装修改的数据内容
        Update update = new Update();
        update.inc("thumbup", 1);
        mongoTemplate.updateFirst(query, update, "spit");




    }





}
