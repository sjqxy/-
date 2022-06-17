package com.itheima.test;

import com.itheima.dao.StudentMapper;
import com.itheima.domain.Student;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

public class StudentManager1 {
    private static SqlSessionFactory sqlSessionFactory;

    static {
        String resource = "mybatis-config.xml";//指定核心配置文件名称
        InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream(resource);
         sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    public static void main(String[] args) throws IOException {

        Scanner sc = new Scanner(System.in);
        lo:
        while (true) {
            // 1. 搭建主界面菜单
            System.out.println("--------欢迎来到学生管理系统--------");
            System.out.println("1 添加学生");
            System.out.println("2 删除学生");
            System.out.println("3 修改学生");
            System.out.println("4 查看学生");
            System.out.println("5 退出");
            System.out.println("请输入您的选择:");

            String choice = sc.next();

            switch (choice) {
                case "1":
                    //System.out.println("添加学生");
                    addStudent();
                    break;
                case "2":
                    //System.out.println("删除学生");
                    deleteStudent();
                    break;
                case "3":
                    //System.out.println("修改学生");
                    updateStudent();
                    break;
                case "4":
                    // System.out.println("查看学生");
                    queryStudents();
                    break;
                case "5":
                    System.out.println("感谢您的使用");
                    break lo;
                default:
                    System.out.println("您的输入有误");
                    break;
            }
        }


    }

    // 修改学生的方法
    public static void updateStudent() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            // 1. 给出提示信息 (请输入您要删除的学号)
            System.out.println("请输入您要修改的学生学号:");
            // 2. 键盘接收要删除的学号
            String updateSid = sc.next();
            if ("0".equals(updateSid)) {
                break;
            }
            // TODO 等待你完成删除功能
            boolean flag = getIndex(updateSid);
            if (flag) {
                System.out.println("请输入新的学生姓名:");
                String name = sc.nextLine();
                sc = new Scanner(System.in);
                System.out.println("请输入新的学生年龄:");
                sc = new Scanner(System.in);
                int age = sc.nextInt();
                System.out.println("请输入新的学生生日:");
                String birthday = sc.nextLine();
                // 封装为新的学生对象
                Student stu = new Student(updateSid, name, age, birthday);
                SqlSession sqlSession = sqlSessionFactory.openSession();
                StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
                mapper.updateStudent(stu);
                sqlSession.commit();
                sqlSession.close();
                System.out.println("修改成功");
                break;

            } else
                //不存在
                System.out.println(updateSid + "该学生不存在,");


        }



        // 调用集合的set方法, 完成修改
        // TODO 需要你完成数据库的修改功能
        System.out.println("修改成功!");

    }

    // 删除学生的方法
    public static void deleteStudent() {
        Scanner sc = new Scanner(System.in);
        while (true){
            // 1. 给出提示信息 (请输入您要删除的学号)
            System.out.println("请输入您要删除的学生学号:输入0表示结束该功能");
            // 2. 键盘接收要删除的学号
            String deleteSid = sc.next();
            if("0".equals(deleteSid)){
                break;
            }
            // TODO 等待你完成删除功能
            boolean flag = getIndex(deleteSid);
            if (flag){
                //删除
                SqlSession sqlSession = sqlSessionFactory.openSession();
                StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
                mapper.deleteById(deleteSid);
                sqlSession.commit();
                sqlSession.close();
                System.out.println("删除成功");
                break;

            }else
                //不存在
                System.out.println(deleteSid+"该学生不存在,");


        }
    }

    // 查看学生的方法
    public static void queryStudents() {
        // TODO 等待你完成从数据库查询学生信息并封装成list集合
        SqlSession sqlSession = sqlSessionFactory.openSession();
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        List<Student> list = mapper.selectAll();

        // 1. 判断集合中是否存在数据, 如果不存在直接给出提示
        if (list.size() == 0) {
            System.out.println("无信息, 请添加后重新查询");
            return;
        }
        // 2. 存在: 展示表头数据
        System.out.println("学号\t\t姓名\t年龄\t生日");
        // 3. 遍历集合, 获取每一个学生对象的信息, 打印在控制台
        for (int i = 0; i < list.size(); i++) {
            Student stu = list.get(i);
            System.out.println(stu.getSid() + "\t" + stu.getName() + "\t" + stu.getAge() + "\t\t" + stu.getBirthday());
        }
    }

    // 添加学生的方法
    public static void addStudent() {
        Scanner sc = new Scanner(System.in);
        // 1. 给出录入的提示信息

        String sid;

        while (true) {
            System.out.println("请输入学号:");
            sid = sc.next();

            boolean flag = getIndex(sid);
            // flag 为false表示id不存在,可以添加,为true则存在,不能添加
            if (!flag) {
                // sid不存在, 学号可以使用
                break;
            } else {
                System.out.println(sid + "学生已经存在了,请更换!");
            }
        }

        System.out.println("请输入姓名:");
        String name = sc.next();
        System.out.println("请输入年龄:");
        int age = sc.nextInt();
        System.out.println("请输入生日:");
        String birthday = sc.next();
        // 2. 将键盘录入的信息封装为学生对象
        Student stu = new Student(sid, name, age, birthday);
        // 3. 将封装好的学生对象, 添加到集合容器当中
        // TODO 将stu中的信息保存到数据库中即可
        SqlSession sqlSession = sqlSessionFactory.openSession();
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        mapper.insertStudent(stu);
        // 4. 给出添加成功的提示信息
        sqlSession.commit();
        sqlSession.close();
        System.out.println("添加成功!");
    }

    /*
        getIndex : 接收一个集合对象, 接收一个学生学号

        查找这个学号, 在集合中出现的索引位置
     */
    public static boolean getIndex(String sid) {
        // TODO 等待完成根据id查询数据库中学生是否存在,存在返回true,不存在返回false


        //2. 获取SqlSession对象，用它来执行sql

        SqlSession sqlSession = sqlSessionFactory.openSession();
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        Student s = mapper.selectById(sid);

        sqlSession.close();
        return s != null;

    }


}
