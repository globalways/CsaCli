package com.globalways.csacli.entity;

/**
 * 单个图片对象
 * 
 * @author James
 */
public class ImageItem {

	/** 相册ID **/
	private String imageBucketId;
	/** 图片ID **/
	private long imageId;
	/** 缩略图路径 **/
	private String thumbnailPath;
	/** 原图路径 **/
	private String imagePath;
	/** 文件大小(byte) **/
	private long imageSize = 0;

	public ImageItem() {
		super();
	}

	public ImageItem(String imageBucketId, long imageId, String thumbnailPath, String imagePath, long imageSize) {
		super();
		this.imageBucketId = imageBucketId;
		this.imageId = imageId;
		this.thumbnailPath = thumbnailPath;
		this.imagePath = imagePath;
		this.imageSize = imageSize;
	}

	@Override
	public String toString() {
		return "ImageItem [imageBucketId=" + imageBucketId + ", imageId=" + imageId + ", thumbnailPath="
				+ thumbnailPath + ", imagePath=" + imagePath + ", imageSize=" + imageSize + "]";
	}

	public String getImageBucketId() {
		return imageBucketId;
	}

	public void setImageBucketId(String imageBucketId) {
		this.imageBucketId = imageBucketId;
	}

	public long getImageId() {
		return imageId;
	}

	public void setImageId(long imageId) {
		this.imageId = imageId;
	}

	public String getThumbnailPath() {
		return thumbnailPath;
	}

	public void setThumbnailPath(String thumbnailPath) {
		this.thumbnailPath = thumbnailPath;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public long getImageSize() {
		return imageSize;
	}

	public void setImageSize(long imageSize) {
		this.imageSize = imageSize;
	}
}
