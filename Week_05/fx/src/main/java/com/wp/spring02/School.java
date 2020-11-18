package com.wp.spring02;

import com.wp.aop.ISchool;
import com.wp.spring01.Student;
import lombok.Data;

import javax.annotation.Resource;

@Data
public class School implements ISchool {

    @Resource(name = "student222")
    Student wp;
    
    @Override
    public void ding() {
        System.out.println("school ding : " + this.wp);
    }
}
