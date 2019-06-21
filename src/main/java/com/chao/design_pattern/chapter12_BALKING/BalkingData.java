package com.chao.design_pattern.chapter12_BALKING;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class BalkingData {
	private String fileName;
	private String content;
	private boolean changed;

	public BalkingData(String fileName, String content) {
		this.fileName = fileName;
		this.content = content;
		this.changed = true;
	}

	public void change(String content){
		this.content = content;
		this.changed = true;
	}

	public void save(){
		if(!changed){
			return;
		}
		doSave();
	}

	private void doSave() {
		try (PrintWriter printWriter = new PrintWriter(fileName)) {
			printWriter.print(content);
			printWriter.flush();
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("The file has saved.");
	}
}
