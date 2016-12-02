package com.iwk.yang.tool;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by yangshare on 2016/11/29 0029.
 * 邮箱717449318@qq.com
 */

public class DBOpenHelper extends SQLiteOpenHelper {
    /**
     * 创建OpenHelper
     *
     * @param context 上下文
     * @param name    数据库名
     * @param factory 游标工厂
     * @param version 数据库版本, 不要设置为0, 如果为0则会每次都创建数据库
     */
    public DBOpenHelper(Context context, String name, CursorFactory factory,
                        int version) {
        super(context, name, factory, version);
    }

    /**
     * 当数据库第一次创建的时候被调用
     */
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE user(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "icons" +
                "name," +
                "pwd, " +
                "question, " +
                "answer, " +
                "school, " +
                "college, " +
                "professional, " +
                "clazz, " +
                "sno, " +
                "sname, " +
                "ssex, " +
                "email, " +
                "introduce)");
    }

    /**
     * 当数据库版本发生改变的时候被调用
     */
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("ALTER TABLE user ADD balance");
    }
}
