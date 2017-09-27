package com.taotao.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.taotao.common.pojo.PictureResult;
import com.taotao.common.utils.FastDFSClient;
import com.taotao.service.PictureService;

@Service
public class PictureServiceImpl implements PictureService{
	@Value("${IMG_SERVER_BASE_URL}")
	private String IMG_SERVER_BASE_URL;
	
	@Override
	public PictureResult uploadPic(MultipartFile picFile) {
		PictureResult result = new PictureResult();
		//判断图片是否为空
		if(picFile.isEmpty()){
			result.setError(PictureResult.isError);
			result.setMessage("上传文件为空.");
			return result;
		}
		//上传文件到服务器
		try {
			//取图片扩展名
			String originalFilename = picFile.getOriginalFilename();
			//取扩展名不要“.”
			String extName = originalFilename.substring(originalFilename.lastIndexOf(".")+1);
			FastDFSClient client = new FastDFSClient("classpath:properties/client.conf");
			String url = client.uploadFile(picFile.getBytes(), extName);
			result.setError(PictureResult.notError);
			result.setUrl(IMG_SERVER_BASE_URL + url);
		} catch (Exception e) {
			e.printStackTrace();
			result.setError(PictureResult.isError);
			result.setMessage("图片上传失败.");
		}
		//把url响应给客户端
		return result;
	}

}
