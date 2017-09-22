package com.taotao.fastdfs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;

public class FastdfsTest {
	@Test
	public void testUpload() throws Exception {
		// 1、把FastDFS提供的jar包添加到工程中
		// 2、初始化全局配置。加载一个配置文件。
		// 3、创建一个TrackerClient对象。
		ClientGlobal.init("E:\\learningsoft\\eclipseWorkspaces\\personalWorkspace\\taotao\\taotao-manager\\taotao-manager-web\\src\\main\\resources\\resource\\client.conf");
		TrackerClient trackerClient  =  new  TrackerClient();
		// 4、创建一个TrackerServer对象。
		TrackerServer trackerServer = trackerClient.getConnection();
		// 5、声明一个StorageServer对象，null。
		StorageServer storageServer = null;
		// 6、获得StorageClient对象。
		StorageClient storageClient = new StorageClient(trackerServer, storageServer);
		// 7、直接调用StorageClient对象方法上传文件即可。
		String[] strings = storageClient.upload_file("E:\\dest.jpg","jpg", null);
		for(String item : strings){
			System.out.println(item);
		}
	}
	@Test
	public void testLoadFile() throws Exception{
		String item = new String("‪E:\\opt\\1.jpg");//在E之前有一个Unicode的字符202A 是空现在我们看不到
		String item2 = new String("E:\\opt\\1.jpg");
		String item3 = new String("E:\\opt\\1.jpg");
		System.out.println(item);
		System.out.println(item2);
		File file = new File(item);
		File file1 = new File(item2);
		
		/*FileInputStream fInputStream  = new FileInputStream(file1);
		fInputStream.close();*/
	}
	@Test
	public void testStringDiff(){
		String item = new String("‪E:\\opt\\1.jpg");
		String item2 = new String("E:\\opt\\1.jpg");
		System.out.println(item.toCharArray());
		System.out.println(item2.toCharArray());
	}
	@Test
	public void testchartsDiff(){
		char[] item ="E:\\opt\\1.jpg".toCharArray();
		char[] item2 = "E:\\opt\\1.jpg".toCharArray();
		for(int i = 0;i<item.length;i++){
			if(item[i] != item2[i]){
				System.out.println(item[i] + " " + item2[i] );
			}else{
				System.out.println(item[i] + " " + item2[i] +"true" );
			}
		}
	}
}
