package com.example.springDemo;

import java.io.Serializable;

public abstract class BaseService<M extends Serializable> {
	public void service(){
		System.out.println("BASE Service");
	}
}
