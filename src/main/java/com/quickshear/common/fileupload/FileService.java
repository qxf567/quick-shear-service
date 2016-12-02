package com.quickshear.common.fileupload;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
/**
 * @since shear
 */
@Service
public class FileService {

	private Logger logger = LoggerFactory.getLogger(FileService.class);

	/** 单次上传图片不能超过50张 */
	public static final int MAX_COUNT = 50;
	/** 允许上传的文件类型 */
	private static final String CMS_ACCETP_TYPES = "jpg,jpeg";
	private static final String CRM_ACCETP_TYPES = "png,gif,jpg,jpeg,rar,zip,7z,doc,docx";
	private static final String CRM_ACCETP_TYPES_1 = "png,gif,jpg,jpeg,doc,docx";
	private static final String CRM_ACCETP_TYPES_NOTICE = "png,gif,jpg,jpeg,rar,zip,7z,doc,docx,xls,xlsx";
	/** 图片加水印后的质量设置 */
	private static final float COMPRESSED_QUALITY = 0.85F;

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
		String msg = "{\"url\": \"%s\", \"fileType\":\"%s\", \"state\":\"%s\",\"original\":\"%s\"}";
		String filename = mf.getOriginalFilename().replace(",", "_").replace("&", "_");
		try
		{
//			if (!validateFileExtName(mf, CMS_ACCETP_TYPES, true, fileExtName))
//			{
//				return String.format(msg, null, null, "文件格式错误，只支持" + CMS_ACCETP_TYPES, null);
//			}
//			String file = uploadSinglePicToCms(mf, watermark, false, trackerGroup, fileExtName);
//			if (StringUtils.isNotBlank(file))
//			{
//				return String.format(msg, file, fileExtName, "SUCCESS", filename);
//			}
//			else
//			{
				return String.format(msg, null, null, "文件上传失败，请重试或与管理员联系", null);
//			}
		}
		catch (Exception e)
		{
			logger.error("Fail to upload file, filename is " + filename,e);
			return String.format(msg, null, null, "文件上传失败，请重试或与管理员联系", null);
		}
	}

}
