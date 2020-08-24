package com.adidev.bakersbiz;

import android.app.Application;

import com.adidev.bakersbiz.repository.Repository;

public class GlobalClass extends Application {
    private Repository repository;

    @Override
    public void onCreate (){
        super.onCreate();
        repository = new Repository();
    }

    public Repository getRepository() {
        return repository;
    }
}
