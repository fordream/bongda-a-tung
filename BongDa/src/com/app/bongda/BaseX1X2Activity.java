package com.app.bongda;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.app.bongda.base.BaseActivtiy;
import com.app.bongda.fragment.BangXepHangFragment;
import com.app.bongda.fragment.CountryFragment;
import com.app.bongda.fragment.DanhSachGiaiDauFragment;
import com.app.bongda.fragment.DuDoanKetQuaFragment;
import com.app.bongda.fragment.LiveScoreFragment;
import com.app.bongda.fragment.NhanDinhChuyenGiaFragment;
import com.app.bongda.fragment.PhongDoDoiDauFragment;
import com.app.bongda.fragment.TuongThuatTranLiveScoreFragment;
import com.app.bongda.fragment.TyLeDuDoanFragment;
import com.app.bongda.inter.CallBackListenner;
import com.app.bongda.model.Country;
import com.app.bongda.model.GiaiDau;
import com.app.bongda.model.LiveScore;

public class BaseX1X2Activity extends BaseActivtiy {

	public void showTuongThuatTranLiveScoreFragment(GiaiDau giaiDau) {
		OnItemClickListener liveScoreTuongThuatOnItemClickListener = new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// -> ty le du doan
				// showFragment(new TyLeDuDoanFragment(null));
				// -> du doan ket qua
				// showFragment(new DuDoanKetQuaFragment(null));
				// -> phong do doi dau
				LiveScore liveScore = (LiveScore) parent
						.getItemAtPosition(position);
				GiaiDau dau = new GiaiDau(liveScore.getId(),
						liveScore.getName() , liveScore.magiai(), liveScore.madoinha(), liveScore.madoikhach(),liveScore.idmagiai());
				
				showFragment(new PhongDoDoiDauFragment(dau,null));
			}
		};

		CallBackListenner callBackListenner = new CallBackListenner() {

			@Override
			public void onCallBackListenner(int position, Object data) {
				if (position == 1) {
					showBangXemHang((GiaiDau) data);
				} else if (position == 2) {
//					Log.e("KKKKKKKKKKKKK22", "===" + ((GiaiDau) data).magiai() + "::" + ((GiaiDau) data).madoinha() + ":" + ((GiaiDau) data).madoikhach());
					showFragment(new PhongDoDoiDauFragment((GiaiDau) data,null));
				} else if (position == 3) {
					showFragment(new DuDoanKetQuaFragment(null));
				} else if (position == 4) {
					showFragment(new TyLeDuDoanFragment(null));
				}
			}
		};
		showFragment(new TuongThuatTranLiveScoreFragment(giaiDau,
				liveScoreTuongThuatOnItemClickListener, callBackListenner));
	}

	public void showDanhSachGiaiDau(Country country) {
		OnItemClickListener listGiaiDau = new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
//				LiveScore liveScore = (LiveScore) parent
//						.getItemAtPosition(position);
//				GiaiDau dau = new GiaiDau(liveScore.getId(),
//						liveScore.getName() , liveScore.magiai(), liveScore.madoinha(), liveScore.madoikhach());
				
				GiaiDau dau= (GiaiDau)parent
						.getItemAtPosition(position);
				showLiveScore(dau);
			}
		};

		showFragment(new DanhSachGiaiDauFragment(country, listGiaiDau));
	}

	public void showPhongDoDoiDauFragment(GiaiDau giaiDau) {
		showFragment(new PhongDoDoiDauFragment(giaiDau,null));
	}

	public void showBangXepHangFragment(GiaiDau giaiDau) {
		showFragment(new BangXepHangFragment(giaiDau, null));
	}

	public void showCountry() {
		OnItemClickListener clickListenerCountry = new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Country country = (Country) parent.getItemAtPosition(position);
				showDanhSachGiaiDau(country);
			}
		};
		showFragment(new CountryFragment(clickListenerCountry));
	}

	public void showLiveScore(GiaiDau data) {
		OnItemClickListener liveScoreOnItemClickListener = new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				LiveScore liveScore = (LiveScore) parent
						.getItemAtPosition(position);

				// phong do doi dau

				// xem tuong thuat a
				if (!liveScore.isHeader()) {
//					GiaiDau dau = new GiaiDau(liveScore.getId(),
//							liveScore.getName());
					GiaiDau dau = new GiaiDau(liveScore.getId(),
							liveScore.getName() , liveScore.magiai(), liveScore.madoinha(), liveScore.madoikhach(),liveScore.idmagiai());
//					Log.e("liveScore.magiai()", liveScore.magiai());
					showTuongThuatTranLiveScoreFragment(dau);
				}
			}
		};
		CallBackListenner callBackListenner = new CallBackListenner() {
			@Override
			public void onCallBackListenner(int position, Object data) {
				LiveScore liveScore = (LiveScore) data;
				if (position == 0) {
//					GiaiDau dau = new GiaiDau(liveScore.getId(),
//							liveScore.getName());
					GiaiDau dau = new GiaiDau(liveScore.getId(),
							liveScore.getName() , liveScore.magiai(), liveScore.madoinha(), liveScore.madoikhach(),liveScore.idmagiai());
					// showFragment(new BangXepHangFragment(dau, null));
					showBangXemHang(dau);
				} else if (position == 2) {
					GiaiDau dau = new GiaiDau(liveScore.getId(),
							liveScore.getName() , liveScore.magiai(), liveScore.madoinha(), liveScore.madoikhach(),liveScore.idmagiai());
					showPhongDoDoiDauFragment(dau);
				}
			}
		};

		showFragment(new LiveScoreFragment(liveScoreOnItemClickListener,
				callBackListenner, data));
	}

	public void showBangXemHang(GiaiDau dau) {
		showFragment(new BangXepHangFragment(dau, null));
	}
	
	public void showNhanDinhChuyenGia(GiaiDau dau) {
		showFragment(new NhanDinhChuyenGiaFragment(dau, null));
	}
}
