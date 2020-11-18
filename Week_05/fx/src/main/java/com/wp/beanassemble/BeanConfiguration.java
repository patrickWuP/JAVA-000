package com.wp.beanassemble;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {
    
    @Bean
    public Family getFamily() {
        Family family = new Family();
        Cat cat = new Cat();
        cat.setId(2);
        cat.setName("configuration nao nao");
        family.setCat(cat);
        return family;
    }
}
