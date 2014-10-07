package org.acv.bynal.camera;

import java.io.ByteArrayOutputStream;
import java.io.File;

import org.acv.bynal.activity.ProfileActivity;

import com.acv.libs.base.db.AccountDB;
import com.acv.libs.base.util.ByUtils;
import com.edmodo.cropper.CropImageView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import app.bynal.woman.R;


public class CropActivity extends Activity {
//	private Handler handler = new Handler();
	// Static final constants
	Context contex;
	private static final int DEFAULT_ASPECT_RATIO_VALUES = 10;
	private static final int ROTATE_NINETY_DEGREES = 90;
	private static final String ASPECT_RATIO_X = "ASPECT_RATIO_X";
	private static final String ASPECT_RATIO_Y = "ASPECT_RATIO_Y";
	private int mAspectRatioX = DEFAULT_ASPECT_RATIO_VALUES;
	private int mAspectRatioY = DEFAULT_ASPECT_RATIO_VALUES;
	private Bitmap croppedImage;

	@Override
	protected void onSaveInstanceState(Bundle bundle) {
		super.onSaveInstanceState(bundle);
		bundle.putInt(ASPECT_RATIO_X, mAspectRatioX);
		bundle.putInt(ASPECT_RATIO_Y, mAspectRatioY);
	}

	@Override
	protected void onRestoreInstanceState(Bundle bundle) {
		super.onRestoreInstanceState(bundle);
		mAspectRatioX = bundle.getInt(ASPECT_RATIO_X);
		mAspectRatioY = bundle.getInt(ASPECT_RATIO_Y);
	}
 
	private Bitmap getPreview(String path) {
		String absPath = path.substring("file://".length());
		Log.e("CropActivity", "get image from uri =" + absPath);
		return BitmapFactory.decodeFile(absPath);
	}
	


	public Bitmap createBitMap(String path){
	    File file = new File(path);
	    Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
	    return bitmap;
	}



	String path;
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		try {
			sendBroadcast(new Intent(
			           Intent.ACTION_MEDIA_MOUNTED,
			           Uri.parse("file://" +  Environment.getExternalStorageDirectory())));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		contex = this;
		setContentView(R.layout.activity_crop_new);
//		Log.e("aaaaaaaaaaaaaaa","===================CropActivity");

		try {
			final CropImageView cropImageView = (CropImageView) findViewById(R.id.CropImageView);
			
			Bundle extras = getIntent().getExtras();
			if (extras != null) {
//				Log.e("Crop","===================CropActivity1");
				path = extras.getString("profileImgUri");
			}
			
			if (path == null)
				finish();
			Bitmap bitmap = getPreview(path);
			if (bitmap == null)
				finish();

			
			Matrix mat = new Matrix();
//			mat.postRotate(90);
//			mat.postScale(1f, 1f);
			Bitmap bMapRotate = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), mat, true);
			    
			    
			cropImageView.setImageBitmap(bMapRotate);

			final Button backButton = (Button) findViewById(R.id.headerbutton1);
			backButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					finish();
				}
			});

			cropImageView.setAspectRatio(DEFAULT_ASPECT_RATIO_VALUES,DEFAULT_ASPECT_RATIO_VALUES);

			final Button cropButton = (Button) findViewById(R.id.headerbutton2);
			cropButton.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					croppedImage = cropImageView.getCroppedImage();
					
					if(croppedImage != null){
						startUploadActivity(croppedImage);
						finish();
						
					}
					
				}
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
	
	private void startUploadActivity(Bitmap bmp){
		ProfileActivity.uploadAvatar(bmp);
	}
	


}