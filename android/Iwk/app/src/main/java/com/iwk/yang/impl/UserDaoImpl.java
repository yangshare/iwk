package com.iwk.yang.impl;

import android.content.Context;
import android.util.Log;

import com.iwk.yang.dao.DaoManager;
import com.user.dao.UserDao;
import com.user.entity.User;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangshare on 2016/11/30 0030.
 * 邮箱717449318@qq.com
 */

public class UserDaoImpl {
    /**
     * 完成对某一张表的具体操作
     */
    private DaoManager daoManager;

    //构造方法
    public UserDaoImpl(Context context) {
        daoManager = DaoManager.getInstance();
        daoManager.initManager(context);
    }

    /**
     * 对数据库中student表的插入操作
     *
     * @param user
     * @return
     */
    public boolean insertUser(User user) {
        boolean flag = false;
        try {
            flag = daoManager.getDaoSession().insert(user) != -1 ? true : false;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            daoManager.closeDaoSession();
        }
        return flag;
    }

    /**
     * 批量插入
     *
     * @param users
     * @return
     */
    public boolean inserMultUser(final List<User> users) {
        //标识
        boolean flag = false;
        try {
            //插入操作耗时
            daoManager.getDaoSession().runInTx(new Runnable() {
                @Override
                public void run() {
                    for (User user : users) {
                        daoManager.getDaoSession().insertOrReplace(user);
                    }
                }
            });
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            daoManager.closeDaoSession();
        }

        return flag;
    }

    /**
     * 修改
     *
     * @param user
     * @return
     */
    public boolean updateUser(User user) {
        boolean flag = false;
        try {
            daoManager.getDaoSession().update(user);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            daoManager.closeDaoSession();
        }
        return flag;
    }

    /**
     * 删除
     *
     * @param user
     * @return
     */
    public boolean deleteUser(User user) {
        boolean flag = false;
        try {
            //删除指定ID
            daoManager.getDaoSession().delete(user);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            daoManager.closeDaoSession();
        }
        return flag;
    }

    /**
     * 查询单条
     *
     * @param key
     * @return
     */
    public User listOneUser(long key) {
        User user = null;
        try {
            user = daoManager.getDaoSession().load(User.class, key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            daoManager.closeDaoSession();
        }
        return user;
    }

    /**
     * 全部查询
     *
     * @return
     */
    public List<User> listAll() {
        List<User> userlist = null;
        try {
            userlist = new ArrayList<>();
            userlist = daoManager.getDaoSession().loadAll(User.class);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            daoManager.closeDaoSession();
        }
        return userlist;
    }

    /**
     * QueryBuilder查询大于
     */
    public List<User> queryBuilder() {
        List<User> userlist = null;
        try {
            userlist = new ArrayList<>();
            //查询构建器
            QueryBuilder<User> queryBuilder = daoManager.getDaoSession().queryBuilder(User.class);
            //查询年龄大于19的北京
            userlist = queryBuilder.where(UserDao.Properties.Id.ge(19)).where(UserDao.Properties.Sname.like("北京")).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            daoManager.closeDaoSession();
        }

        Log.i("user的queryBuilder", userlist + "");
        return userlist;
    }

    /**
     * 全部查询
     *
     * @return
     */
    public int queryRecords() {
        int sum = 0;
        try {
            sum = daoManager.getDaoSession().loadAll(User.class).size();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            daoManager.closeDaoSession();
        }
        return sum;
    }
}
