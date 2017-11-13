package com.yixin.service.impl;

import com.yixin.entity.Jars;
import com.yixin.service.DaoService;
import org.nutz.dao.Dao;
import org.nutz.dao.impl.NutDao;
import org.nutz.dao.impl.SimpleDataSource;


public class NutzDaoService implements DaoService {
    private SimpleDataSource dataSource ;

    private Dao dao;

    public  NutzDaoService(){
        dataSource = new SimpleDataSource();
        dataSource.setJdbcUrl("jdbc:mysql://127.0.0.1/mvncrawler");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
        dao = new NutDao(dataSource);
        dao.create(Jars.class,false);
    }

    public Dao getDao() {
        if(dao == null){
            dao = new NutDao(dataSource);
        }
        return dao;
    }

    public <T> T insert(T t) {
        T t2 = dao.insert(t);
        return t2;
    }

    public int delete(Object t) {
     return dao.delete(t);
    }


    public int delete(int id, Class clases) {
        int delete = dao.delete(clases, id);
        return delete;
    }

    public Object find(int id, Class classes) {
        return dao.fetch(classes,id);
    }
}
