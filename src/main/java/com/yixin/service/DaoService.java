package com.yixin.service;

import org.nutz.dao.Dao;

public interface DaoService {

    Dao getDao();

    <T> T insert(T t);

    int delete(Object t);

    int delete(int id, Class clases);

    Object find(int id, Class classes);

}
