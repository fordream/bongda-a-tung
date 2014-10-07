/* Copyright (c) 2010 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.acv.bynal.camera;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;
import java.util.TimeZone;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.provider.MediaStore.Video;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import app.bynal.woman.R;

import org.acv.bynal.activity.ProjectmanagerActivity;
import org.acv.bynal.camera.Authorizer.AuthorizationListener;
//import com.google.ytd.db.DbHelper;

import com.acv.libs.base.BaseItem;
import com.acv.libs.base.CommonAndroid;

public class VideoActivity extends Activity {
  private static final String LOG_TAG = VideoActivity.class.getSimpleName();

  private static final String INITIAL_UPLOAD_URL =
      "http://uploads.gdata.youtube.com/resumable/feeds/api/users/default/uploads";
  private static final String DEFAULT_VIDEO_CATEGORY = "News";
  private static final String DEFAULT_VIDEO_TAGS = "mobile";

  private static final int DIALOG_LEGAL = 0;

  private static final int MAX_RETRIES = 5;
  private static final int BACKOFF = 4; // base of exponential backoff

  private ProgressDialog dialog = null;
  private String ytdDomain = null;
  private String assignmentId = null;
  private Uri videoUri = null;
  private String clientLoginToken = null;
  private String youTubeName = null;
  private Date dateTaken = null;
  private Authorizer authorizer = null;
  private Location videoLocation = null;
  private String tags = null;
  private LocationListener locationListener = null;
  private LocationManager locationManager = null;
  private SharedPreferences preferences = null;
  private TextView domainHeader = null;
  // TODO - replace these counters with a state variable
  private double currentFileSize = 0;
  private double totalBytesUploaded = 0;
  private int numberOfRetries = 0;
  private boolean submitYoutubeSuccess = false;
  private boolean checkPermission = false;
  VideoUtil util;

  static class YouTubeAccountException extends Exception {
    public YouTubeAccountException(String msg) {
      super(msg);
    }
  }
  private ProgressDialog progressdialog;
  
  @SuppressLint("NewApi")
@Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.setContentView(R.layout.project_post_video);
    Log.i(LOG_TAG, "start SubmitActivity");
    
    preferences = getSharedPreferences(VideoUtil.SHARE_DATA_PREF, Context.MODE_PRIVATE);
    this.authorizer = new ClientLoginAuthorizer(this);

    Intent intent = this.getIntent();
    this.videoUri = intent.getData();
    this.ytdDomain = getString(R.string.default_ytd_domain);

    this.youTubeName = "dodanhhieu@gmail.com";
    Log.i(LOG_TAG, "acc = " + this.youTubeName);

    final TextView submitButton = (TextView) findViewById(R.id.submitButton);
    progressdialog = new ProgressDialog(this);

	getAuthTokenWithPermission(youTubeName);
	util = new VideoUtil(VideoActivity.this);
    submitButton.setOnClickListener(new OnClickListener() {
      @SuppressWarnings("deprecation")
	@Override
      public void onClick(View v) {
//        showDialog(DIALOG_LEGAL);
    	if (util.isNetworkAvaible()) {
    		if(checkPermission){
    			upload(VideoActivity.this.videoUri);
    		}else{
    			getAuthTokenWithPermission2(youTubeName);
//    			findViewById(R.id.submitButton).setVisibility(View.GONE);
//    			util.showAlertError("permission denied!");
    		}
		}else{
			util.showAlertError(getResources().getString(
					R.string.video_internet_err), getResources().getString(
					R.string.video_title_err));
		}
      }
    });
    
   /* Button cancelButton = (Button) findViewById(R.id.cancelButton);
    cancelButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        setResult(RESULT_CANCELED);
        finish();
      }
    });
*/
    findViewById(R.id.headerbutton1).setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			finish();
		}
	});
    
    findViewById(R.id.headerbutton2).setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if(submitYoutubeSuccess){
				String response = VideoUtil.jsonData;
				String youtubeID = "";
				try {
					JSONObject jsonObject;
					jsonObject = new JSONObject(response);
					if (jsonObject != null) {
						String temp = jsonObject.getString("params");
						JSONObject jsonObject2;
						jsonObject2 = new JSONObject(temp);
						if (jsonObject2 != null) {
							youtubeID = jsonObject2.getString("videoId");
							ProjectmanagerActivity.uploadAvatar(youtubeID);
							setResult(RESULT_CANCELED);
							finish();
						}
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
//				Toast.makeText(VideoActivity.this, "upload 001:" + youtubeID, Toast.LENGTH_LONG).show();
			}else{
				util.showAlertError(getResources().getString(
						R.string.video_up_server_err), null);
//				Toast.makeText(VideoActivity.this, "not upload", Toast.LENGTH_LONG).show();
			}
		}
	});
    
    

    Cursor cursor = this.managedQuery(this.videoUri, null, null, null, null);

    if (cursor.getCount() == 0) {
      Log.e(LOG_TAG, "not a valid video uri");
      Toast.makeText(VideoActivity.this, "not a valid video uri", Toast.LENGTH_LONG).show();
    } else {
    	Log.e(LOG_TAG, "valid video uri");
      getVideoLocation();

      if (cursor.moveToFirst()) {

        long id = cursor.getLong(cursor.getColumnIndex(Video.VideoColumns._ID));
        this.dateTaken = new Date(cursor.getLong(cursor
            .getColumnIndex(Video.VideoColumns.DATE_TAKEN)));

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM d, yyyy hh:mm aaa");
        Configuration userConfig = new Configuration();
        Settings.System.getConfiguration(getContentResolver(), userConfig);
        Calendar cal = Calendar.getInstance(userConfig.locale);
        TimeZone tz = cal.getTimeZone();

        dateFormat.setTimeZone(tz);

//        TextView dateTakenView = (TextView) findViewById(R.id.dateCaptured);
//        dateTakenView.setText("Date captured: " + dateFormat.format(dateTaken));

        ImageView thumbnail = (ImageView) findViewById(R.id.thumbnail);
        ContentResolver crThumb = getContentResolver();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 1;
        Bitmap curThumb = MediaStore.Video.Thumbnails.getThumbnail(crThumb, id, MediaStore.Video.Thumbnails.MICRO_KIND, options);
        if(curThumb != null){
        	thumbnail.setImageBitmap(curThumb);
        }else{
        	 Toast.makeText(VideoActivity.this, "not load video", Toast.LENGTH_LONG).show();
        	 finish();
        }
        
      }
    }
  }

  @Override
  protected Dialog onCreateDialog(int id) {
    final Dialog dialog = new Dialog(VideoActivity.this);
    dialog.setTitle("Terms of Service");
    switch(id) {
    case DIALOG_LEGAL:
      dialog.setContentView(R.layout.project_post_videolegal);

      TextView legalText = (TextView) dialog.findViewById(R.id.legal);

      legalText.setText(VideoUtil.readFile(this, R.raw.legal).toString());

      dialog.findViewById(R.id.agree).setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
          dialog.cancel();
          getAuthTokenWithPermission(youTubeName);
          Log.i(LOG_TAG, "youTube name : " + youTubeName);
        }
      });
      dialog.findViewById(R.id.notagree).setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
          dialog.cancel();
        }
      });

      break;
    }

    return dialog;
  }

  @Override
  public void onRestart() {
    super.onRestart();
    hideKeyboard(this.getCurrentFocus());
  }

  private void requestDummyFocus() {
//    this.findViewById(R.id.dummy).requestFocus();
  }

  @SuppressLint("NewApi")
private void hideKeyboard(View currentFocusView) {
    if (currentFocusView instanceof EditText) {
      InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
      imm.hideSoftInputFromWindow(currentFocusView.getWindowToken(), 0);
      requestDummyFocus();
    }
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    if (this.locationListener != null) {
      this.locationManager.removeUpdates(locationListener);
    }
  }

  //TODO: upload video to youtube
  public void upload(Uri videoUri) {
    this.dialog = new ProgressDialog(this);
    dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
    dialog.setMessage("uploading ...");
    dialog.setCancelable(false);
    dialog.show();
    progressdialog.dismiss();  
	
    Handler handler = new Handler() {
      @Override
      public void handleMessage(Message msg) {
        dialog.dismiss();
        String videoId = msg.getData().getString("videoId");

        if (!VideoUtil.isNullOrEmpty(videoId)) {
          currentFileSize = 0;
          totalBytesUploaded = 0;
//          Intent result = new Intent();
//          result.putExtra("videoId", videoId);
//          setResult(RESULT_OK, result);
//          finish();
          submitYoutubeSuccess = true;
          findViewById(R.id.submitButton).setVisibility(View.GONE);
          util.showAlertError(getResources().getString(
					R.string.video_up_youtube_done), null );
        } else {
          String error = msg.getData().getString("error");
          if (!VideoUtil.isNullOrEmpty(error)) {
//            Toast.makeText(VideoActivity.this, error, Toast.LENGTH_LONG).show();
        	  util.showAlertError(error , getResources().getString(
					R.string.video_title_err));
          }else{
        	  util.showAlertError(getResources().getString(
    					R.string.video_up_youtube_err) , getResources().getString(
  					R.string.video_title_err));
          }
        }
      }
    };

    asyncUpload(videoUri, handler);
  }

  public void asyncUpload(final Uri uri, final Handler handler) {
    new Thread(new Runnable() {
      @Override
      public void run() {
        Message msg = new Message();
        Bundle bundle = new Bundle();
        msg.setData(bundle);

        String videoId = null;
        int submitCount=0;
        try {
          while (submitCount<=MAX_RETRIES && videoId == null) {
            try {
              submitCount++;
              videoId = startUpload(uri);
              assert videoId!=null;
            } catch (Internal500ResumeException e500) { // TODO - this should not really happen
              if (submitCount<MAX_RETRIES) {
                Log.w(LOG_TAG, e500.getMessage());
                Log.d(LOG_TAG, String.format("Upload retry :%d.",submitCount));
              } else {
                Log.d(LOG_TAG, "Giving up");
                Log.e(LOG_TAG, e500.getMessage());
                throw new IOException(e500.getMessage());
              }
            }
          }
        } catch (IOException e) {
          e.printStackTrace();
          bundle.putString("error", e.getMessage());
          handler.sendMessage(msg);
          return;
        } catch (YouTubeAccountException e) {
          e.printStackTrace();
          bundle.putString("error", e.getMessage());
          handler.sendMessage(msg);
          return;
        } catch (SAXException e) {
          e.printStackTrace();
          bundle.putString("error", e.getMessage());
          handler.sendMessage(msg);
        } catch (ParserConfigurationException e) {
          e.printStackTrace();  
          bundle.putString("error", e.getMessage());
          handler.sendMessage(msg);
        }

        bundle.putString("videoId", videoId);
        handler.sendMessage(msg);
      }
    }).start();
  }

  private File getFileFromUri(Uri uri) throws IOException {
    Cursor cursor = managedQuery(uri, null, null, null, null);
    if (cursor.getCount() == 0) {
      throw new IOException(String.format("cannot find data from %s", uri.toString()));
    } else {
      cursor.moveToFirst();
    }

    String filePath = cursor.getString(cursor.getColumnIndex(Video.VideoColumns.DATA));

    File file = new File(filePath);
    cursor.close();
    return file;
  }

  private String startUpload(Uri uri) throws IOException, YouTubeAccountException, SAXException, ParserConfigurationException, Internal500ResumeException {
    File file = getFileFromUri(uri);

    if (this.clientLoginToken == null) {
      // The stored gmail account is not linked to YouTube
      throw new YouTubeAccountException(this.youTubeName + " is not linked to a YouTube account.");
    }

    String uploadUrl = uploadMetaData(file.getAbsolutePath(), true);

    Log.d(LOG_TAG, "uploadUrl=" + uploadUrl);
    Log.d(LOG_TAG, String.format("Client token : %s ",this.clientLoginToken));
      

    this.currentFileSize = file.length();
    this.totalBytesUploaded = 0;
    this.numberOfRetries = 0;

    int uploadChunk = 1024 * 1024 * 3; // 3MB

    int start = 0;
    int end = -1;

    String videoId = null;
    double fileSize = this.currentFileSize;
    while (fileSize > 0) {
      if (fileSize - uploadChunk > 0) {
        end = start + uploadChunk - 1;
      } else {
        end = start + (int) fileSize - 1;
      }
      Log.d(LOG_TAG, String.format("start=%s end=%s total=%s", start, end, file.length()));
      try {
        videoId = gdataUpload(file, uploadUrl, start, end);
        fileSize -= uploadChunk;
        start = end + 1;
        this.numberOfRetries = 0; // clear this counter as we had a succesfull upload
      } catch (IOException e) {
        Log.d(LOG_TAG,"Error during upload : " + e.getMessage());
        ResumeInfo resumeInfo = null;
        do {
          if (!shouldResume()) {
            Log.d(LOG_TAG, String.format("Giving up uploading '%s'.", uploadUrl));
            throw e;
          }
          try {
            resumeInfo = resumeFileUpload(uploadUrl);
          } catch (IOException re) {
            // ignore
            Log.d(LOG_TAG, String.format("Failed retry attempt of : %s due to: '%s'.", uploadUrl, re.getMessage()));
          }
        } while (resumeInfo == null);
        Log.d(LOG_TAG, String.format("Resuming stalled upload to: %s.", uploadUrl));
        if (resumeInfo.videoId != null) { // upload actually complted despite the exception
          videoId = resumeInfo.videoId;
          Log.d(LOG_TAG, String.format("No need to resume video ID '%s'.", videoId));          
          break;
        } else {
          int nextByteToUpload = resumeInfo.nextByteToUpload;
          Log.d(LOG_TAG, String.format("Next byte to upload is '%d'.", nextByteToUpload));
          this.totalBytesUploaded = nextByteToUpload; // possibly rolling back the previosuly saved value
          fileSize = this.currentFileSize - nextByteToUpload;
          start = nextByteToUpload;
        }
      }
    }

    if (videoId != null) {
      return videoId;
    }

    return null;
  }

  public String title = "Bynal-title";
  public String description = "Bynal-desc";
  private String uploadMetaData(String filePath, boolean retry) throws IOException {
    String uploadUrl = INITIAL_UPLOAD_URL;

    HttpURLConnection urlConnection = getGDataUrlConnection(uploadUrl);
    urlConnection.setRequestMethod("POST");
    urlConnection.setDoOutput(true);
    urlConnection.setRequestProperty("Content-Type", "application/atom+xml");
    urlConnection.setRequestProperty("Slug", filePath);
    String atomData;

    String category = DEFAULT_VIDEO_CATEGORY;
    this.tags = DEFAULT_VIDEO_TAGS;

    if (this.videoLocation == null) {
      String template = VideoUtil.readFile(this, R.raw.gdata).toString();
      atomData = String.format(template, title, description, category, this.tags);
    } else {
      String template = VideoUtil.readFile(this, R.raw.gdata_geo).toString();
      atomData = String.format(template, title, description, category, this.tags,
          videoLocation.getLatitude(), videoLocation.getLongitude());
    }

    OutputStreamWriter outStreamWriter = new OutputStreamWriter(urlConnection.getOutputStream());
    outStreamWriter.write(atomData);
    outStreamWriter.close();

    int responseCode = urlConnection.getResponseCode();
    if (responseCode < 200 || responseCode >= 300) {
      // The response code is 40X
      if ((responseCode + "").startsWith("4") && retry) {
        Log.d(LOG_TAG, "retrying to fetch auth token for " + youTubeName);
        this.clientLoginToken = authorizer.getFreshAuthToken(youTubeName, clientLoginToken);
        // Try again with fresh token
        return uploadMetaData(filePath, false);
      } else {
        throw new IOException(String.format("response code='%s' (code %d)" + " for %s",
            urlConnection.getResponseMessage(), responseCode, urlConnection.getURL()));
      }
    }

    return urlConnection.getHeaderField("Location");
  }

  private String gdataUpload(File file, String uploadUrl, int start, int end) throws IOException {
    int chunk = end - start + 1;
    int bufferSize = 1024;
    byte[] buffer = new byte[bufferSize];
    FileInputStream fileStream = new FileInputStream(file);

    HttpURLConnection urlConnection = getGDataUrlConnection(uploadUrl);
    // some mobile proxies do not support PUT, using X-HTTP-Method-Override to get around this problem
    if (isFirstRequest()) {
      Log.d(LOG_TAG, String.format("Uploaded %d bytes so far, using POST method.", (int)totalBytesUploaded));
      urlConnection.setRequestMethod("POST");
    } else {
      urlConnection.setRequestMethod("POST");
      urlConnection.setRequestProperty("X-HTTP-Method-Override", "PUT");
      Log.d(LOG_TAG, String.format("Uploaded %d bytes so far, using POST with X-HTTP-Method-Override PUT method.",
        (int)totalBytesUploaded));
    }
    urlConnection.setDoOutput(true);
    urlConnection.setFixedLengthStreamingMode(chunk);
    urlConnection.setRequestProperty("Content-Type", "video/3gpp");
    urlConnection.setRequestProperty("Content-Range", String.format("bytes %d-%d/%d", start, end,
        file.length()));
    Log.d(LOG_TAG, urlConnection.getRequestProperty("Content-Range"));

    OutputStream outStreamWriter = urlConnection.getOutputStream();

    fileStream.skip(start);

    int bytesRead;
    int totalRead = 0;
    while ((bytesRead = fileStream.read(buffer, 0, bufferSize)) != -1) {
      outStreamWriter.write(buffer, 0, bytesRead);
      totalRead += bytesRead;
      this.totalBytesUploaded += bytesRead;

      double percent = (totalBytesUploaded / currentFileSize) * 99;

      dialog.setProgress((int) percent);

      if (totalRead == (end - start + 1)) {
        break;
      }
    }

    outStreamWriter.close();

    int responseCode = urlConnection.getResponseCode();

    Log.d(LOG_TAG, "responseCode=" + responseCode);
    Log.d(LOG_TAG, "responseMessage=" + urlConnection.getResponseMessage());

    try {
      if (responseCode == 201) {
        String videoId = parseVideoId(urlConnection.getInputStream());
        preferences.edit().putString(VideoUtil.VIDEO_ID, videoId).commit();
        String latLng = null;
        if (this.videoLocation != null) {
          latLng = String.format("lat=%f lng=%f", this.videoLocation.getLatitude(),
              this.videoLocation.getLongitude());
        }

        submitToYtdDomain(this.ytdDomain, this.assignmentId, videoId,
            this.youTubeName, VideoActivity.this.clientLoginToken, title,description, this.dateTaken, latLng, this.tags);
        dialog.setProgress(100);
        return videoId;
      } else if (responseCode == 200) {
        Set<String> keySet = urlConnection.getHeaderFields().keySet();
        String keys = urlConnection.getHeaderFields().keySet().toString();
        Log.d(LOG_TAG, String.format("Headers keys %s.", keys));
        for (String key : keySet) {
          Log.d(LOG_TAG, String.format("Header key %s value %s.", key, urlConnection.getHeaderField(key)));          
        }
        Log.w(LOG_TAG, "Received 200 response during resumable uploading");
        throw new IOException(String.format("Unexpected response code : responseCode=%d responseMessage=%s", responseCode,
              urlConnection.getResponseMessage()));
      } else {
        if ((responseCode + "").startsWith("5")) {
          String error = String.format("responseCode=%d responseMessage=%s", responseCode,
              urlConnection.getResponseMessage());
          Log.w(LOG_TAG, error);
          // TODO - this exception will trigger retry mechanism to kick in
          // TODO - even though it should not, consider introducing a new type so
          // TODO - resume does not kick in upon 5xx
          throw new IOException(error);
        } else if (responseCode == 308) {
          // OK, the chunk completed succesfully 
          Log.d(LOG_TAG, String.format("responseCode=%d responseMessage=%s", responseCode,
              urlConnection.getResponseMessage()));
        } else {
          // TODO - this case is not handled properly yet
          Log.w(LOG_TAG, String.format("Unexpected return code : %d %s while uploading :%s", responseCode,
            urlConnection.getResponseMessage(), uploadUrl));
        }
      }
    } catch (ParserConfigurationException e) {
      e.printStackTrace();
    } catch (SAXException e) {
      e.printStackTrace();
    }

    return null;
  }

  public boolean isFirstRequest() {
    return totalBytesUploaded==0;
  }

  private ResumeInfo resumeFileUpload(String uploadUrl) throws IOException, ParserConfigurationException, SAXException, Internal500ResumeException {
    HttpURLConnection urlConnection = getGDataUrlConnection(uploadUrl);
    urlConnection.setRequestProperty("Content-Range", "bytes */*");
    urlConnection.setRequestMethod("POST");
    urlConnection.setRequestProperty("X-HTTP-Method-Override", "PUT");
    urlConnection.setFixedLengthStreamingMode(0);

    HttpURLConnection.setFollowRedirects(false);

    urlConnection.connect();
    int responseCode = urlConnection.getResponseCode();

    if (responseCode >= 300 && responseCode < 400) {
      int nextByteToUpload;
      String range = urlConnection.getHeaderField("Range");
      if (range == null) {
        Log.d(LOG_TAG, String.format("PUT to %s did not return 'Range' header.", uploadUrl));
        nextByteToUpload = 0;
      } else {
        Log.d(LOG_TAG, String.format("Range header is '%s'.", range));
        String[] parts = range.split("-");
        if (parts.length > 1) {
          nextByteToUpload = Integer.parseInt(parts[1]) + 1;
        } else {
          nextByteToUpload = 0;
        }
      }
      return new ResumeInfo(nextByteToUpload);
    } else if (responseCode >= 200 && responseCode < 300) {
      return new ResumeInfo(parseVideoId(urlConnection.getInputStream()));
    } else if (responseCode == 500) {
      // TODO this is a workaround for current problems with resuming uploads while switching transport (Wifi->EDGE)
      throw new Internal500ResumeException(String.format("Unexpected response for PUT to %s: %s " +
      		"(code %d)", uploadUrl, urlConnection.getResponseMessage(), responseCode));
    } else {
      throw new IOException(String.format("Unexpected response for PUT to %s: %s " +
      		"(code %d)", uploadUrl, urlConnection.getResponseMessage(), responseCode));
    }
  }


  private boolean shouldResume() {
    this.numberOfRetries++;
    if (this.numberOfRetries>MAX_RETRIES) {
      return false;
    }
    try {
      int sleepSeconds = (int) Math.pow(BACKOFF, this.numberOfRetries);
      Log.d(LOG_TAG,String.format("Zzzzz for : %d sec.", sleepSeconds));
      Thread.currentThread().sleep(sleepSeconds * 1000);
      Log.d(LOG_TAG,String.format("Zzzzz for : %d sec done.", sleepSeconds));      
    } catch (InterruptedException se) {
      se.printStackTrace();
      return false;
    }
    return true;
  }

  private String parseVideoId(InputStream atomDataStream) throws ParserConfigurationException,
      SAXException, IOException {
    DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
    Document doc = docBuilder.parse(atomDataStream);

    NodeList nodes = doc.getElementsByTagNameNS("*", "*");
    for (int i = 0; i < nodes.getLength(); i++) {
      Node node = nodes.item(i);
      String nodeName = node.getNodeName();
      if (nodeName != null && nodeName.equals("yt:videoid")) {
        return node.getFirstChild().getNodeValue();
      }
    }
    return null;
  }

  private HttpURLConnection getGDataUrlConnection(String urlString) throws IOException {
    URL url = new URL(urlString);
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    connection.setRequestProperty("Authorization", String.format("GoogleLogin auth=\"%s\"",clientLoginToken));
    connection.setRequestProperty("GData-Version", "2");
    connection.setRequestProperty("X-GData-Client", this.getString(R.string.client_id));
    connection.setRequestProperty("X-GData-Key", String.format("key=%s", this.getString(R.string.dev_key)));
    return connection;
  }

  private void getAuthTokenWithPermission(String accountName) {
	progressdialog.show();   
    this.authorizer.fetchAuthToken(accountName, this, new AuthorizationListener<String>() {
      @Override
      public void onCanceled() {
      }

      @Override
      public void onError(Exception e) {
    	  progressdialog.dismiss();
    	  util.showAlertError(e.getMessage(), getResources().getString(
					R.string.video_title_err));
      }

      @Override
      public void onSuccess(String result) {
    	Log.i(LOG_TAG, "clientLoginToken = " + result);  
        VideoActivity.this.clientLoginToken = result;
        progressdialog.dismiss();
        checkPermission = true;
//        upload(VideoActivity.this.videoUri);
      }});
  }
  
  private void getAuthTokenWithPermission2(String accountName) {  
	    this.authorizer.fetchAuthToken(accountName, this, new AuthorizationListener<String>() {
	      @Override
	      public void onCanceled() {
	      }

	      @Override
	      public void onError(Exception e) {
	    	  util.showAlertError(e.getMessage(), getResources().getString(
						R.string.video_title_err));
	      }

	      @Override
	      public void onSuccess(String result) {
//	    	Log.i(LOG_TAG, "clientLoginToken = " + result);  
	        VideoActivity.this.clientLoginToken = result;
	        checkPermission = true;
	        upload(VideoActivity.this.videoUri);
	      }});
	  }


  private void getVideoLocation() {
    this.locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
    Criteria criteria = new Criteria();
    criteria.setAccuracy(Criteria.ACCURACY_FINE);
    criteria.setPowerRequirement(Criteria.POWER_HIGH);
    criteria.setAltitudeRequired(false);
    criteria.setBearingRequired(false);
    criteria.setSpeedRequired(false);
    criteria.setCostAllowed(true);

    String provider = locationManager.getBestProvider(criteria, true);

    this.locationListener = new LocationListener() {
      @Override
      public void onLocationChanged(Location location) {
        if (location != null) {
          VideoActivity.this.videoLocation = location;
          double lat = location.getLatitude();
          double lng = location.getLongitude();
          Log.d(LOG_TAG, "lat=" + lat);
          Log.d(LOG_TAG, "lng=" + lng);

          locationManager.removeUpdates(this);
        } else {
          Log.d(LOG_TAG, "location is null");
        }
      }

      @Override
      public void onProviderDisabled(String provider) {
      }

      @Override
      public void onProviderEnabled(String provider) {
      }

      @Override
      public void onStatusChanged(String provider, int status, Bundle extras) {
      }

    };
    
    if (provider != null) {
      locationManager.requestLocationUpdates(provider, 2000, 10, locationListener);
    }
  }

  public void submitToYtdDomain(String ytdDomain, String assignmentId, String videoId,
      String youTubeName, String clientLoginToken, String title, String description,
      Date dateTaken, String videoLocation, String tags) {

    JSONObject payload = new JSONObject();
    try {
      payload.put("method", "NEW_MOBILE_VIDEO_SUBMISSION");
      JSONObject params = new JSONObject();

      params.put("videoId", videoId);
      params.put("youTubeName", youTubeName);
      params.put("clientLoginToken", clientLoginToken);
      params.put("title", title);
      params.put("description", description);
      params.put("videoDate", dateTaken.toString());
      params.put("tags", tags);

      if (videoLocation != null) {
        params.put("videoLocation", videoLocation);
      }

      if (assignmentId != null) {
        params.put("assignmentId", assignmentId);
      }

      payload.put("params", params);
    } catch (JSONException e) {
      e.printStackTrace();
    }

    String jsonRpcUrl = "http://" + ytdDomain + "/jsonrpc";
    String json = VideoUtil.makeJsonRpcCall(jsonRpcUrl, payload);
    
    Log.i("JSON-Upload", payload.toString());
    
    if (json != null) {
      try {
        JSONObject jsonObj = new JSONObject(json);
      } catch (JSONException e) {
        e.printStackTrace();
      }
    }
  }

  class ResumeInfo {
    int nextByteToUpload;
    String videoId;
    ResumeInfo(int nextByteToUpload) {
      this.nextByteToUpload = nextByteToUpload;
    }
    ResumeInfo(String videoId) {
      this.videoId = videoId;
    }
  }

  /**
   * Need this for now to trigger entire upload transaction retry
   */
  class Internal500ResumeException extends Exception {
    Internal500ResumeException(String message) {
      super(message);
    }
  }
}