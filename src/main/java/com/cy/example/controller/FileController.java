package com.cy.example.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cy.example.config.ApplicationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.cy.example.config.WebConfig;
import com.cy.example.entity.FileEntity;
import com.cy.example.service.IFileService;

@RestController
@RequestMapping("/system/file")
public class FileController extends BaseController {

	@Autowired
	private ApplicationProperties prop;

	@Autowired
	private IFileService fileService;

	@RequestMapping("/upload")
	public Map<String, Object> upload(HttpServletRequest request)
			throws IOException {

		long startTime = System.currentTimeMillis();
		List<MultipartFile> files = ((MultipartHttpServletRequest) request)
				.getFiles("file");

		if (files.isEmpty()) {
			// return "false";
		}
		for (MultipartFile file : files) {
			String fileName = file.getOriginalFilename();
			int index = fileName.lastIndexOf(".");
			StringBuilder sb = new StringBuilder(fileName);
			String newFileName = sb.insert(index, System.currentTimeMillis())
					.toString();
			int size = (int) file.getSize() / 1024;
			// System.out.println(src+"/"+newFileName + "-->" + size);
			FileEntity entity = new FileEntity(newFileName, fileName, prop.getUploadfile().getSrc(),
					String.valueOf(size));
			WebConfig.add(entity);

			if (file.isEmpty()) {
				// return "false";
			} else {
				File dest = new File(prop.getUploadfile().getSrc() + "/" + newFileName);
				if (!dest.getParentFile().exists()) { // 判断文件父目录是否存在
					dest.getParentFile().mkdir();
				}
				try {
					file.transferTo(dest);
					fileService.insert(entity);
				} catch (Exception e) {
					e.printStackTrace();
					// return "false";
				}
			}
		}
		long endTime = System.currentTimeMillis();
		System.out
				.println("运行时间：" + String.valueOf(endTime - startTime) + "ms");
		Map<String, Object> map = new HashMap<String, Object>();
		return map;
	}

	@RequestMapping("download")
	public String downLoad() {
		String filename = "2.jpg";
		String filePath = "F:/test";
		File file = new File(filePath + "/" + filename);
		if (file.exists()) { // 判断文件父目录是否存在
			getResponse().setContentType("application/force-download");
			getResponse().setHeader("Content-Disposition",
					"attachment;fileName=" + filename);

			byte[] buffer = new byte[1024];
			FileInputStream fis = null; // 文件输入流
			BufferedInputStream bis = null;

			OutputStream os = null; // 输出流
			try {
				os = getResponse().getOutputStream();
				fis = new FileInputStream(file);
				bis = new BufferedInputStream(fis);
				int i = bis.read(buffer);
				while (i != -1) {
					os.write(buffer);
					i = bis.read(buffer);
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("----------file download" + filename);
			try {
				bis.close();
				fis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

}
