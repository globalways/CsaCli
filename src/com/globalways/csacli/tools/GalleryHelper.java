package com.globalways.csacli.tools;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.MediaStore.Images.Media;
import android.provider.MediaStore.Images.Thumbnails;
import com.globalways.csacli.entity.ImageBucket;
import com.globalways.csacli.entity.ImageItem;

/**
 * 相册帮助类
 * 
 * @author James
 *
 */
public class GalleryHelper {
	private static final String TAG = GalleryHelper.class.getSimpleName();

	private static GalleryHelper mGalleryHelper = null;
	private static ContentResolver mContentResolver;

	/** 存放所有缩略图信息 **/
	@SuppressLint("UseSparseArrays")
	private static Map<Long, String> mThumnailPathMap = new HashMap<Long, String>();

	private GalleryHelper() {
		mContentResolver = MyApplication.mContext.getContentResolver();
		mThumnailPathMap = getAllThumnailList();
	}

	public synchronized static GalleryHelper getInstance() {
		if (null == mGalleryHelper) {
			mGalleryHelper = new GalleryHelper();
		}
		return mGalleryHelper;
	}

	/**
	 * 获取所有缩略图列表
	 * 
	 * @return
	 */
	@SuppressLint("UseSparseArrays")
	private synchronized Map<Long, String> getAllThumnailList() {
		Map<Long, String> thumnailPathMap = new HashMap<Long, String>();
		String projection[] = new String[] { Thumbnails.IMAGE_ID, Thumbnails.DATA };
		Cursor cursor = mContentResolver.query(Thumbnails.EXTERNAL_CONTENT_URI, projection, null, null, null);
		if (null != cursor && cursor.moveToFirst()) {
			do {
				long thumnailId = cursor.getLong(cursor.getColumnIndex(Thumbnails.IMAGE_ID));
				String thumnailPath = cursor.getString(cursor.getColumnIndex(Thumbnails.DATA));
				if (null != thumnailPath && new File(thumnailPath).exists()) {
					thumnailPathMap.put(thumnailId, thumnailPath);
				}
			} while (cursor.moveToNext());
		}
		if (null != cursor && !cursor.isClosed()) {
			cursor.close();
		}
		return thumnailPathMap;
	}

	/**
	 * 获取相册列表
	 * 
	 * @return
	 */
	public synchronized List<ImageBucket> getImageBucket() {
		List<ImageBucket> imageBucketList = null;
		String projection[] = new String[] { Media._ID, Media.BUCKET_ID, Media.BUCKET_DISPLAY_NAME, Media.DATA,
				"COUNT(*) as count" };
		String selection = "0==0) GROUP BY(" + Media.BUCKET_ID;
		Cursor cursor = mContentResolver.query(Media.EXTERNAL_CONTENT_URI, projection, selection, null, null);
		if (null != cursor && cursor.moveToFirst()) {
			imageBucketList = new ArrayList<ImageBucket>();
			do {
				ImageBucket imageBucket = new ImageBucket();
				imageBucket.setImageBucketId(cursor.getString(cursor.getColumnIndex(Media.BUCKET_ID)));
				imageBucket.setBucketName(cursor.getString(cursor.getColumnIndex(Media.BUCKET_DISPLAY_NAME)));
				long imgId = cursor.getLong(cursor.getColumnIndex(Media._ID));
				imageBucket.setCoverOriginalPath(cursor.getString(cursor.getColumnIndex(Media.DATA)));
				imageBucket.setCoverThumbnailPath(mThumnailPathMap.get(imgId));
				imageBucket.setImageCount(cursor.getInt(cursor.getColumnIndex("count")));
				imageBucketList.add(imageBucket);
				MyLog.d(TAG,
						"[imageBucketId,bucketName,count]=[" + imageBucket.getImageBucketId() + ","
								+ imageBucket.getBucketName() + "," + imageBucket.getImageCount() + "]");
				MyLog.d(TAG, "[imgId,coverThumbnailPath]=[" + imgId + "," + imageBucket.getCoverThumbnailPath() + "]");
			} while (cursor.moveToNext());
		}
		if (null != cursor && !cursor.isClosed()) {
			cursor.close();
		}
		return imageBucketList;
	}

	/**
	 * 获取指定相册中的图片列表
	 * 
	 * @param imageBucketId
	 * @return
	 */
	public synchronized List<ImageItem> getImageListByBucket(String imageBucketId) {
		List<ImageItem> list = null;
		String projection[] = new String[] { Media._ID, Media.DATA, Media.SIZE };
		String selection = Media.BUCKET_ID + "=?";
		Cursor cursor = mContentResolver.query(Media.EXTERNAL_CONTENT_URI, projection, selection,
				new String[] { imageBucketId }, null);
		if (null != cursor && cursor.getCount() > 0) {
			list = new ArrayList<ImageItem>();
			if (cursor.moveToFirst()) {
				do {
					ImageItem imageItem = new ImageItem();
					imageItem.setImageBucketId(imageBucketId);
					imageItem.setImageId(cursor.getLong(cursor.getColumnIndex(Media._ID)));
					imageItem.setImagePath(cursor.getString(cursor.getColumnIndex(Media.DATA)));
					imageItem.setThumbnailPath(mThumnailPathMap.get(imageItem.getImageId()));
					imageItem.setImageSize(cursor.getLong(cursor.getColumnIndex(Media.SIZE)));
					list.add(imageItem);
				} while (cursor.moveToNext());
			}
		}
		if (null != cursor && !cursor.isClosed()) {
			cursor.close();
		}
		return list;
	}
}
