package com.quickshear.common.fileupload;

import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.quickshear.common.config.ShearConfig;

/**
 * @since shear
 */
@Service
public class FileService {

	private Logger logger = LoggerFactory.getLogger(FileService.class);

	/** 图片加水印后的质量设置 */
	private static final float COMPRESSED_QUALITY = 0.85F;

	@Autowired
	protected ShearConfig config;

	/**
	 * 上传单张图片
	 * 
	 * @param mf
	 *            文件
	 * @param watermark
	 *            是否加水印
	 * @return
	 */
	public String uploadSinglePic(MultipartFile mf, boolean watermark,
			String folderName) {
		String msg = "{\"success\":%b, \"filePath\":\"%s\", \"message\":\"%s\"}";
		String fileExtName = getFileExtName(mf);
		try {
			// 自定义的文件名称
			String trueFileName = String.valueOf(System.currentTimeMillis())
					+ "."+fileExtName;
			// 设置存放图片文件的路径
			String path = config.getFastdfsServerHost()
					+ System.getProperty("file.separator") + folderName
					+ System.getProperty("file.separator") + trueFileName;

			FileOutputStream fs = new FileOutputStream(path);
			InputStream inputStream = mf.getInputStream();
			byte[] buffer = new byte[1024 * 1024];
			int byteread = 0;
			while ((byteread = inputStream.read(buffer)) != -1) {
				fs.write(buffer, 0, byteread);
				fs.flush();
			}
			fs.close();
			inputStream.close();
			return String.format(msg, true, trueFileName, "上传成功");
		} catch (Exception e) {
			logger.error("Fail to upload file", e);
			return String.format(msg, null, null, "上传失败，请重试或与管理员联系");
		}
	}
	
	private String getFileExtName(MultipartFile mf) {
		String filename = mf.getOriginalFilename();
		if (StringUtils.isNotBlank(filename) && filename.contains(".") && !filename.endsWith("."))
		{
			return filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
		}
		return null;
	}

}
