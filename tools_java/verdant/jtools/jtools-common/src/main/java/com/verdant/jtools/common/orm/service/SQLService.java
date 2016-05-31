package com.verdant.jtools.common.orm.service;


import com.verdant.jtools.metadata.code.ResultCode;
import com.verdant.jtools.metadata.exception.ServiceException;
import com.verdant.jtools.metadata.orm.IRepository;
import com.verdant.jtools.metadata.orm.ISQLService;
import com.verdant.jtools.metadata.model.Page;
import com.verdant.jtools.metadata.model.PageParam;
import com.verdant.jtools.metadata.model.PageWrapper;

import java.util.List;

public abstract class SQLService<T> implements ISQLService<T> {

    public abstract IRepository<T> getRepository();

    @Override
    public T insert(T model) throws ServiceException {
        try {
            int result = getRepository().insert(model);
            if (result <= 0)
                return model;
        } catch (Exception e) {
            throw new ServiceException(ResultCode.ERROR_SAVE, e);
        }
        return model;
    }

    @Override
    public List<T> inserts(List<T> models) throws ServiceException {
        for (T model : models) {
            this.insert(model);
        }
        return models;
    }

    @Override
    public boolean update(T model) throws ServiceException {
        try {
            int result = getRepository().update(model);
            if (result <= 0)
                return false;
        } catch (Exception e) {
            throw new ServiceException(ResultCode.ERROR_UPDATE, e);
        }
        return true;
    }

    @Override
    public boolean delete(T model) throws ServiceException {
        try {
            int result = getRepository().delete(model);
            if (result <= 0)
                return false;
        } catch (Exception e) {
            throw new ServiceException(ResultCode.ERROR_DELETE, e);
        }
        return true;
    }

    @Override
    public boolean deletes(List<T> models) throws ServiceException {
        for (T model : models) {
            this.delete(model);
        }
        return true;
    }

    @Override
    public T get(T model) throws ServiceException {
        try {
            return getRepository().get(model);
        } catch (Exception e) {
            throw new ServiceException(ResultCode.ERROR_SEARCH, e);
        }
    }

    @Override
    public Page<T> page(PageParam page, T model) throws ServiceException {
        try {
            return ((PageWrapper<T>)getRepository().page(page, model)).toPage();
        } catch (Exception e) {
            throw new ServiceException(ResultCode.ERROR_SEARCH, e);
        }
    }
}
