package com.app.bongda.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ByUtils {

	public static final String BASESERVER = "http://210.245.94.14:84/Services/wsfootball.asmx";
	public static final String wsFootBall_BangXepHang = "<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">  <soap:Body>    <wsFootBall_BangXepHang xmlns=\"http://tempuri.org/\">      <iID_MaGiai>bangxephangId</iID_MaGiai>    </wsFootBall_BangXepHang>  </soap:Body></soap:Envelope>";
	public static final String wsFootBall_FocusMatch = "<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">  <soap:Body>    <wsFootBall_Tran_Quan_Tam xmlns=\"http://tempuri.org/\">    \t<sMaTran>%d</sMaTran>    </wsFootBall_Tran_Quan_Tam>  </soap:Body></soap:Envelope>";
	public static final String wsFootBall_Giai_Theo_QuocGia = "<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">  <soap:Body>    <wsFootBall_Giai_Theo_QuocGia xmlns=\"http://tempuri.org/\">      <iID_MaQuocGia>quocgiaid</iID_MaQuocGia>    </wsFootBall_Giai_Theo_QuocGia>  </soap:Body></soap:Envelope>";
	public static final String wsFootBall_Lives = "<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">  <soap:Body>    <wsFootBall_Lives xmlns=\"http://tempuri.org/\" />  </soap:Body></soap:Envelope>";
	public static final String wsFootBall_Lives_Theo_Giai = "<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">  <soap:Body>    <wsFootBall_Lives_Theo_Giai xmlns=\"http://tempuri.org/\">      <iID_MaGiai>magiai</iID_MaGiai>    </wsFootBall_Lives_Theo_Giai>  </soap:Body></soap:Envelope>";
	public static final String wsFootBall_MatchDetail = "<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">  <soap:Body>    <wsFootBall_ChiTiet_Tran xmlns=\"http://tempuri.org/\">    \t<iID_MaTran>%d</iID_MaTran>    </wsFootBall_ChiTiet_Tran>  </soap:Body></soap:Envelope>";
	public static final String wsFootBall_Nhan_Dinh_Chuyen_Gia = "<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">  <soap:Body>    <wsFootBall_Nhan_Dinh_Chuyen_Gia xmlns=\"http://tempuri.org/\">      <iID_MaGiai>iID_MaNhanDinh_Info</iID_MaGiai>    </wsFootBall_Nhan_Dinh_Chuyen_Gia>  </soap:Body></soap:Envelope>";
	public static final String wsFootBall_Nhan_Dinh_Chuyen_Gia_Theo_Tran = "<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">  <soap:Body>    <wsFootBall_Nhan_Dinh_Chuyen_Gia_Theo_Tran xmlns=\"http://tempuri.org/\">      <sMaTran>%d</sMaTran>    </wsFootBall_Nhan_Dinh_Chuyen_Gia_Theo_Tran>  </soap:Body></soap:Envelope>";
	public static final String wsFootBall_Phong_Do = "<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">  <soap:Body>    <wsFootBall_Phong_Do xmlns=\"http://tempuri.org/\">    \t<sMaTran>%d</sMaTran>    </wsFootBall_Phong_Do>  </soap:Body></soap:Envelope>";
	public static final String wsFootBall_Quocgia = "<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">  <soap:Body>    <wsFootBall_Quocgia xmlns=\"http://tempuri.org/\" />  </soap:Body></soap:Envelope>";
	public static final String wsFootBall_Phong_Do_ChiTiet22 = "<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"> <soap:Body><wsFootBall_Phong_Do_ChiTiet xmlns=\"http://tempuri.org/\"><sMaGiai>magiai</sMaGiai><sMaDoiNha>madoinha</sMaDoiNha><sMaDoiKhach>madoikhach</sMaDoiKhach></wsFootBall_Phong_Do_ChiTiet></soap:Body></soap:Envelope>";
	public static final String wsFootBall_Phong_Do_ChiTiet = "<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">  <soap:Body>    <wsFootBall_Phong_Do_ChiTiet xmlns=\"http://tempuri.org/\">    	<sMaGiai>magiai</sMaGiai>        	<sMaDoiNha>madoinha</sMaDoiNha>        	<sMaDoiKhach>madoikhach</sMaDoiKhach>    </wsFootBall_Phong_Do_ChiTiet>  </soap:Body></soap:Envelope>";
	
	public static final String wsFootBall_Lives_dudoan = "<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">  <soap:Body>    <wsFootBall_Lives_Co_GameDuDoan xmlns=\"http://tempuri.org/\" />  </soap:Body></soap:Envelope>";
	public static final String wsFootBall_Lives_TyLeDuDoan = "<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">  <soap:Body>    <wsFootBall_Lives_TyLeDuDoan xmlns=\"http://tempuri.org/\">      <iID_MaTran>matran</iID_MaTran>    </wsFootBall_Lives_TyLeDuDoan>  </soap:Body></soap:Envelope>";
	
	public static final int REQUEST = 1000;

	public static List<String> getFilePath(Context context) {
		List<String> list = new ArrayList<String>();

		// String path = "file:///android_asset/64";
		AssetManager am = context.getResources().getAssets();
		// File file = new File(path);
		try {
			String fileList[] = am.list("64");
			// CommonAndroid.toast(context, fileList.length + "  "
			// + (getContryBitmap(context, fileList[0]) == null));
		} catch (Exception exception) {
		}

		return list;
	}

	public static Bitmap getContryBitmap(Context context, String name) {
		Bitmap bitmap = getBitmapFromAsset(context, name);

		if (bitmap == null) {
			bitmap = getBitmapFromAsset(context, "Unknown.png");
		}

		return bitmap;
	}

	private static Bitmap getBitmapFromAsset(Context context, String strName) {
		AssetManager assetManager = context.getAssets();
		InputStream istr = null;
		try {
			istr = assetManager.open("64/" + strName);
		} catch (IOException e) {
		}
		if (istr != null)
			return BitmapFactory.decodeStream(istr);
		return null;
	}

	/**
	 * 
	 */
	public static final boolean USEGROUPVIEW = false;

}