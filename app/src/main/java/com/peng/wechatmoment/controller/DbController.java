package com.peng.wechatmoment.controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.peng.wechatmoment.bean.dao.DaoMaster;
import com.peng.wechatmoment.bean.dao.DaoSession;
import com.peng.wechatmoment.bean.dao.MomentsDAOBean;
import com.peng.wechatmoment.bean.dao.MomentsDAOBeanDao;

import java.util.List;

/**
 * author : Mr.Q
 * e-mail : m1838044925_1@163.com
 * date   : 2019/11/30 18:08
 * desc   : 数据库访问控制器
 */
public class DbController {

    private DaoMaster.DevOpenHelper mHelper;//获取Helper对象

    private DaoMaster mDaoMaster;

    private DaoSession mDaoSession;

    private Context context;

    private MomentsDAOBeanDao momentsDAOBeanDao;

    private static DbController mDbController;

    /**
     * 获取单例
     */
    public static DbController getInstance(Context context) {
        if (mDbController == null) {
            synchronized (DbController.class) {
                if (mDbController == null) {
                    mDbController = new DbController(context);
                }
            }
        }
        return mDbController;
    }

    /**
     * 初始化
     *
     * @param context
     */
    public DbController(Context context) {
        this.context = context;
        mHelper = new DaoMaster.DevOpenHelper(context, "moments.db", null);
        mDaoMaster = new DaoMaster(getWritableDatabase());
        mDaoSession = mDaoMaster.newSession();
        momentsDAOBeanDao = mDaoSession.getMomentsDAOBeanDao();
    }

    /**
     * 获取可读数据库
     */
    private SQLiteDatabase getReadableDatabase() {
        if (mHelper == null) {
            mHelper = new DaoMaster.DevOpenHelper(context, "moments.db", null);
        }
        SQLiteDatabase db = mHelper.getReadableDatabase();
        return db;
    }

    /**
     * 获取可写数据库
     *
     * @return
     */
    private SQLiteDatabase getWritableDatabase() {
        if (mHelper == null) {
            mHelper = new DaoMaster.DevOpenHelper(context, "moments.db", null);
        }
        SQLiteDatabase db = mHelper.getWritableDatabase();
        return db;
    }

    /**
     * 会自动判定是插入还是替换
     *
     * @param momentsDAOBean
     */
    public void insertOrReplace(MomentsDAOBean momentsDAOBean) {
        momentsDAOBeanDao.insertOrReplace(momentsDAOBean);
    }

    /**
     * 插入一条记录，表里面要没有与之相同的记录
     *
     * @param momentsDAOBean
     */
    public long insert(MomentsDAOBean momentsDAOBean) {
        return momentsDAOBeanDao.insert(momentsDAOBean);
    }

    /**
     * 更新数据
     *
     * @param momentsDAOBean
     */
    public void update(MomentsDAOBean momentsDAOBean) {
        MomentsDAOBean mOldMomentsDAOBean = momentsDAOBeanDao.queryBuilder().where(MomentsDAOBeanDao.Properties.Id.eq(momentsDAOBean.getId())).build().unique();//拿到之前的记录
        if (mOldMomentsDAOBean != null) {
            mOldMomentsDAOBean.setUrl(momentsDAOBean.getUrl());
            mOldMomentsDAOBean.setJson(momentsDAOBean.getJson());
            momentsDAOBeanDao.update(mOldMomentsDAOBean);
        }
    }

    /**
     * 按条件查询数据
     */
    public MomentsDAOBean searchByWhere(String wherecluse) {
        MomentsDAOBean momentsDAOBean = momentsDAOBeanDao.queryBuilder().where(MomentsDAOBeanDao.Properties.Url.eq(wherecluse)).build().unique();
        return momentsDAOBean;
    }

    /**
     * 查询所有数据
     */
    public List<MomentsDAOBean> searchAll() {
        List<MomentsDAOBean> personInfors = momentsDAOBeanDao.queryBuilder().list();
        return personInfors;
    }

    /**
     * 删除数据
     */
    public void delete(String wherecluse) {
        momentsDAOBeanDao.queryBuilder().where(MomentsDAOBeanDao.Properties.Url.eq(wherecluse)).buildDelete().executeDeleteWithoutDetachingEntities();
    }
}
