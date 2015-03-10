package com.globalways.csacli.entity;

/**
 * 相册对象
 * 
 * @author James
 */
public class ImageBucket {

	/** 相册ID **/
	private String imageBucketId;
	/** 当前相册图片数量 **/
	private int imageCount = 0;
	/** 相册名称 **/
	private String bucketName;
	/** 封面原图 **/
	private String coverOriginalPath;
	/** 封面缩略图路径 **/
	private String coverThumbnailPath;

	public ImageBucket() {
		super();
	}

	public ImageBucket(String imageBucketId, int imageCount, String bucketName, String coverOriginalPath,
			String coverThumbnailPath) {
		super();
		this.imageBucketId = imageBucketId;
		this.imageCount = imageCount;
		this.bucketName = bucketName;
		this.coverOriginalPath = coverOriginalPath;
		this.coverThumbnailPath = coverThumbnailPath;
	}

	@Override
	public String toString() {
		return "ImageBucket [imageBucketId=" + imageBucketId + ", imageCount=" + imageCount + ", bucketName="
				+ bucketName + ", coverOriginalPath=" + coverOriginalPath + ", coverThumbnailPath="
				+ coverThumbnailPath + "]";
	}

	public String getImageBucketId() {
		return imageBucketId;
	}

	public void setImageBucketId(String imageBucketId) {
		this.imageBucketId = imageBucketId;
	}

	public int getImageCount() {
		return imageCount;
	}

	public void setImageCount(int imageCount) {
		this.imageCount = imageCount;
	}

	public String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	public String getCoverOriginalPath() {
		return coverOriginalPath;
	}

	public void setCoverOriginalPath(String coverOriginalPath) {
		this.coverOriginalPath = coverOriginalPath;
	}

	public String getCoverThumbnailPath() {
		return coverThumbnailPath;
	}

	public void setCoverThumbnailPath(String coverThumbnailPath) {
		this.coverThumbnailPath = coverThumbnailPath;
	}

}
