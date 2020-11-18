package com.wp.jdkproxy;

public class YoungMan implements Study {

    @Override
    public void learn(String course) {
        System.out.println("study hard " + course);
    }

    @Override
    public void sleep() {
        System.out.println("need sleep");
    }
}
