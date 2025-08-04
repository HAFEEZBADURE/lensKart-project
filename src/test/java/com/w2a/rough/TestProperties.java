package com.w2a.rough;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

public class TestProperties {

	public static void main(String[] args) throws Exception {

		Properties Config = new Properties();
		FileInputStream fis = new FileInputStream(
				System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\Config.properties");
		Config.load(fis);

		fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\OR.properties");
		Config.load(fis);

		System.out.println(Config.getProperty("browser"));

	}
}
