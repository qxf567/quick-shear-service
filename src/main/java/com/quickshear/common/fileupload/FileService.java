package com.quickshear.common.fileupload;

import java.io.File;

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
	public String uploadSinglePic(MultipartFile mf, boolean watermark) {
		String msg = "{\"success\":%b, \"filePath\":\"%s\", \"message\":\"%s\"}";
		String fileName = mf.getOriginalFilename().replace(",", "_")
				.replace("&", "_");
		try {
			// 自定义的文件名称
			String trueFileName = String.valueOf(System.currentTimeMillis())
					+ fileName;
			// 设置存放图片文件的路径
			String path = config.getFastdfsServerHost()+trueFileName;
			System.out.println("存放图片文件的路径:" + path);
			// 转存文件到指定的路径
			mf.transferTo(new File(path));
			return String.format(msg, true, trueFileName, "上传成功");
		} catch (Exception e) {
			logger.error("Fail to upload file, filename is " + fileName, e);
			return String.format(msg, null, null, "上传失败，请重试或与管理员联系");
		}
	}

}
