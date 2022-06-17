package com.itheima.dao;
import java.util.List;
import com.itheima.domain.Student;

/*
专门操作数据库的接口
增删改查,根据id查
 */
public interface StudentMapper {
    //增加
    void insertStudent(Student s);

    //删
    void deleteById(String id);

    //改
    void updateStudent(Student s) ;
    //查
    List<Student> selectAll();

    //根据id查对象
    Student selectById(String id);
}
