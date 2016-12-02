package com.yang.iwk;

import org.greenrobot.greendao.generator.DaoGenerator;
import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Schema;

public class DkDaoGenerator {
    public static void main(String[] args) {
        //生成数据库的实体类,还有版本号
        Schema schema = new Schema(3, "com.user.entity");
        addStudent(schema);
        //指定dao
        schema.setDefaultJavaPackageDao("com.user.dao");
        try {
            //指定路径
            new DaoGenerator().generateAll(schema, "./app/src/main/java-gen");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建数据库的表
     *
     * @param schema
     */
    public static void addStudent(Schema schema) {
        //创建数据库的表
        Entity entity = schema.addEntity("User");
        //主键 是int类型
        entity.addIdProperty().primaryKey().autoincrement();
        entity.addStringProperty("icons");// 头像
        entity.addStringProperty("name");// 登陆名（昵称）
        entity.addStringProperty("pwd");// 登陆密码
        entity.addStringProperty("question");// 找回密码问题
        entity.addStringProperty("answer");// 找回密码答案
        entity.addStringProperty("school");// 学校
        entity.addStringProperty("college");// 学院
        entity.addStringProperty("professional");// 专业
        entity.addStringProperty("clazz");// 班级
        entity.addStringProperty("sno");// 学号
        entity.addStringProperty("sname");// 学生姓名
        entity.addStringProperty("ssex");// 学生性别
        entity.addStringProperty("email");// 邮箱
        entity.addStringProperty("introduce");// 自我介绍
    }
}
