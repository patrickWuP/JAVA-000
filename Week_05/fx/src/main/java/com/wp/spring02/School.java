package com.wp.spring02;

import com.wp.aop.ISchool;
import com.wp.spring01.Student;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

@Data
public class School implements ISchool {

    @Autowired
    Student wp;
    
    @Override
    public void ding() {
        System.out.println("school ding : " + this.wp);
    }
}
