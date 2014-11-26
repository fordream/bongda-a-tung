package com.app.bongda.view;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.app.bongda.NhanDinhChuyenGiaActivity;
import com.app.bongda.PhongDoCacDoiActivity;
import com.app.bongda.fragment.BangXepHangFragment;
import com.app.bongda.fragment.CountryFragment;
import com.app.bongda.fragment.DanhSachGiaiDauFragment;
import com.app.bongda.fragment.GameDuDoanFragment;
import com.app.bongda.fragment.LiveScoreFragment;
import com.app.bongda.fragment.LiveScoreLikeFragment;
import com.app.bongda.fragment.NhanDinhChuyenGiaFragment;
import com.app.bongda.fragment.PhongDoDoiDauFragment;
import com.app.bongda.fragment.TuongThuatTranLiveScoreFragment;
import com.app.bongda.fragment.TyLeDuDoanFragment;
import com.app.bongda.inter.CallBackListenner;
import com.app.bongda.model.Country;
import com.app.bongda.model.GiaiDau;
import com.app.bongda.model.LiveScore;
import com.app.bongda.vl.X1VLayoutActivity;
import com.app.bongda.vl.X2VLayoutActivity;
import com.app.bongda.vl.X3VLayoutActivity;
import com.app.bongda.vl.X4VLayoutActivity;

public class BaseViewOfFaragmentPagerAdapter extends PagerAdapter {

	public void addFragement(Fragment fragment) {
		pager.setOnPageChangeListener(null);
		int curent = pager.getCurrentItem();
		list.add(fragment);

		pager.setAdapter(BaseViewOfFaragmentPagerAdapter.this);
		notifyDataSetChanged();

		if (curent >= 0) {
			pager.setCurrentItem(curent);
		}
		handler.sendEmptyMessageDelayed(2, 10);
	}

	private List<Fragment> list = new ArrayList<Fragment>();
	private ViewPager pager;
	private Handler handler = new Handler() {
		public void dispatchMessage(android.os.Message msg) {
			super.dispatchMessage(msg);
			if (msg.what == 0) {
				// size = 2;
				int position = Integer.parseInt(msg.obj.toString());
				notifyDataSetChanged();
				pager.setCurrentItem(getCount() - 1);
			} else if (msg.what == 1) {
				// back
				list.remove(list.size() - 1);
				pager.setAdapter(BaseViewOfFaragmentPagerAdapter.this);
				notifyDataSetChanged();
				pager.setCurrentItem(getCount() - 1);
				onResume();
			} else if (msg.what == 2) {
				// next
				pager.setCurrentItem(getCount() - 1);
				pager.setOnPageChangeListener(changeListener);
			}
		};
	};

	private OnPageChangeListener changeListener = new OnPageChangeListener() {
		@Override
		public void onPageSelected(int arg0) {
			if (arg0 < getCount() - 1) {
				Message msg = new Message();
				msg.what = 1;
				handler.sendMessageAtTime(msg, 10);
			}
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}
	};

	public BaseViewOfFaragmentPagerAdapter(ViewPager mpager) {
		this.pager = mpager;
		pager.setAdapter(this);
		pager.setOnPageChangeListener(changeListener);
		if (mpager.getContext() instanceof X4VLayoutActivity//
				|| mpager.getContext() instanceof X2VLayoutActivity //
				|| mpager.getContext() instanceof PhongDoCacDoiActivity //
				/*|| mpager.getContext() instanceof NhanDinhChuyenGiaActivity//
*/				) {
			if( mpager.getContext() instanceof X2VLayoutActivity){
				addCountry("live");
			}else{
				addCountry("new");
			}
		} else if (mpager.getContext() instanceof X1VLayoutActivity) {
			addLiveScore(null, null);
		} else if (mpager.getContext() instanceof X3VLayoutActivity) {
			addLiveScoreLiked();
		} else if (mpager.getContext() instanceof NhanDinhChuyenGiaActivity){
			showNhanDinhChuyenGia(null);
		}
	}

	/**
	 * ------------------------------------------------------------------------
	 * --------------
	 * 
	 */

	public void addTuongThuatTranLiveScoreFragment(LiveScore giaiDau) {
		OnItemClickListener liveScoreTuongThuatOnItemClickListener = new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// -> ty le du doan
				// showFragment(new TyLeDuDoanFragment(null));
				// -> du doan ket qua
				// showFragment(new DuDoanKetQuaFragment(null));
				// -> phong do doi dau
				LiveScore liveScore = (LiveScore) parent.getItemAtPosition(position);
				GiaiDau dau = new GiaiDau(liveScore.iID_MaGiai(), liveScore.sTenGiai(), liveScore.sMaGiai(), liveScore.madoinha(), liveScore.madoikhach(), liveScore.idmagiai(),
						liveScore.iID_MaTran(), liveScore.sLogoDoiNha(), liveScore.sLogoDoiKhach(), liveScore.iID_MaDoiNha(), liveScore.iID_MaDoiKhach());

				addPhongDoDoiDauFragment(dau);
			}
		};

		CallBackListenner callBackListenner = new CallBackListenner() {

			@Override
			public void onCallBackListenner(int position, Object data) {
				if (position == 1) {
					addBangXepHang((GiaiDau) data);
				} else if (position == 2) {
					addPhongDoDoiDauFragment((GiaiDau) data);
				} else if (position == 3) {
					addGameDuDoan(null);
				} else if (position == 4) {
					addTyLeDuDoanFragment((GiaiDau) data);
				}
			}
		};
		addFragement(new TuongThuatTranLiveScoreFragment(giaiDau, liveScoreTuongThuatOnItemClickListener, callBackListenner));
	}

	private void addTyLeDuDoanFragment(GiaiDau giaiDau) {
		addFragement(new TyLeDuDoanFragment(giaiDau, null));
	}

	public void addPhongDoDoiDauFragment(GiaiDau giaiDau) {
		addFragement(new PhongDoDoiDauFragment(giaiDau, null));
	}

	public void addLiveScore(GiaiDau data, String type) {
		OnItemClickListener liveScoreOnItemClickListener = new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				LiveScore liveScore = (LiveScore) parent.getItemAtPosition(position);

				// phong do doi dau

				// xem tuong thuat a
				if (!liveScore.isHeader()) {
					// GiaiDau dau = new GiaiDau(liveScore.getId(),
					// liveScore.getName());
					// GiaiDau dau = new GiaiDau(liveScore.getId(),
					// liveScore.getName(), liveScore.sMaGiai(),
					// liveScore.madoinha(), liveScore.madoikhach(),
					// liveScore.idmagiai());

					addTuongThuatTranLiveScoreFragment(liveScore);
				}
			}
		};
		CallBackListenner callBackListenner = new CallBackListenner() {
			@Override
			public void onCallBackListenner(int position, Object data) {
				LiveScore liveScore = (LiveScore) data;
				GiaiDau dau = new GiaiDau(liveScore.iID_MaGiai(), liveScore.sTenGiai(), liveScore.sMaGiai(), liveScore.madoinha(), liveScore.madoikhach(), liveScore.idmagiai(),
						liveScore.iID_MaTran(), liveScore.sLogoDoiNha(), liveScore.sLogoDoiKhach(), liveScore.iID_MaDoiNha(), liveScore.iID_MaDoiKhach());
				if (position == 0) {
					dau.sLogoGiai(liveScore.sLogoGiai());
					dau.iID_MaDoiNha(liveScore.iID_MaDoiNha());
					dau.iID_MaDoiKhach(liveScore.iID_MaDoiKhach());
					dau.sTenDoiNha(liveScore.sTenDoiNha());
					dau.sTenDoiKhach(liveScore.sTenDoiKhach());
					Log.e("livescore_phong do", "liveScore.iID_MaTran()===" + dau.iID_MaTran());
					addPhongDoDoiDauFragment(dau);
				} else if (position == 1) {
					addGameDuDoan(null);
				} else if (position == 2) {
					dau.sLogoGiai(liveScore.sLogoGiai());
					addBangXepHang(dau);
				} else if (position == 3) {
					// nhandinhchuyengia
					showNhanDinhChuyenGia(dau);
				} else {
					addTuongThuatTranLiveScoreFragment(liveScore);
				}
			}
		};

		addFragement(new LiveScoreFragment(liveScoreOnItemClickListener, callBackListenner, data, type));
	}

	public void addLiveScoreLiked() {
		OnItemClickListener liveScoreOnItemClickListener = new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Cursor arg1 = (Cursor) parent.getItemAtPosition(position);

				if (!"1".equals(arg1.getString(arg1.getColumnIndex("bdposition")))) {
					return;
				}
				final LiveScore liveScore = new LiveScore(//
						false,//
						arg1.getString(arg1.getColumnIndex("iID_MaTran"))//
						, arg1.getString(arg1.getColumnIndex("sTenGiai"))//
						, arg1.getString(arg1.getColumnIndex("sTenDoiNha"))//
						, arg1.getString(arg1.getColumnIndex("sTenDoiKhach"))//
						, null//
						, null//
						, null//
						, null//
						, null//
						, 0//
						, arg1.getString(arg1.getColumnIndex("sMaGiai"))//
						, null//
						, null//
						, arg1.getString(arg1.getColumnIndex("iID_MaGiai"))// /
						, false//
						, false//
						, false//
						, arg1.getString(arg1.getColumnIndex("sLogoQuocGia"))//
						, arg1.getString(arg1.getColumnIndex("sLogoGiai"))//
						, arg1.getString(arg1.getColumnIndex("sLogoDoiNha"))//
						, arg1.getString(arg1.getColumnIndex("sLogoDoiKhach"))//
						, arg1.getString(arg1.getColumnIndex("iID_MaDoiNha"))//
						, arg1.getString(arg1.getColumnIndex("iID_MaDoiKhach"))//
				);//
					// phong do doi dau

				// xem tuong thuat a
				if (!liveScore.isHeader()) {
					addTuongThuatTranLiveScoreFragment(liveScore);
				}
			}
		};
		CallBackListenner callBackListenner = new CallBackListenner() {
			@Override
			public void onCallBackListenner(int position, Object data) {
				LiveScore liveScore = (LiveScore) data;
				GiaiDau dau = new GiaiDau(liveScore.iID_MaGiai(), liveScore.sTenGiai(), liveScore.sMaGiai(), liveScore.madoinha(), liveScore.madoikhach(), liveScore.idmagiai(),
						liveScore.iID_MaTran(), liveScore.sLogoDoiNha(), liveScore.sLogoDoiKhach(), liveScore.iID_MaDoiNha(), liveScore.iID_MaDoiKhach());
				if (position == 0) {
					dau.sLogoGiai(liveScore.sLogoGiai());
					dau.iID_MaDoiNha(liveScore.iID_MaDoiNha());
					dau.iID_MaDoiKhach(liveScore.iID_MaDoiKhach());
					dau.sTenDoiNha(liveScore.sTenDoiNha());
					dau.sTenDoiKhach(liveScore.sTenDoiKhach());
					addPhongDoDoiDauFragment(dau);
				} else if (position == 1) {
					addGameDuDoan(null);
				} else if (position == 2) {
					dau.sLogoGiai(liveScore.sLogoGiai());
					addBangXepHang(dau);
				} else if (position == 3) {
				} else {
					addTuongThuatTranLiveScoreFragment(liveScore);
				}
			}
		};
		liveScoreLikeFragment = new LiveScoreLikeFragment(liveScoreOnItemClickListener, callBackListenner);
		addFragement(liveScoreLikeFragment);
	}

	public void addGameDuDoan(GiaiDau dau) {
		addFragement(new GameDuDoanFragment(null));
	}

	private void addCountry(String type) {
		addFragement(new CountryFragment(type,new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Cursor cursor = (Cursor) parent.getItemAtPosition(position);
				String _id = cursor.getString(cursor.getColumnIndex("iID_MaQuocGia"));
				String name = cursor.getString(cursor.getColumnIndex("sTenQuocGia"));
				String sLogoCountry = cursor.getString(cursor.getColumnIndex("sLogo"));
				Country country = new Country(_id, name, sLogoCountry);
				addDanhSachGiaiDauFragment(country);
			}
		}));
	}

	private void addDanhSachGiaiDauFragment(Country country) {
		OnItemClickListener listGiaiDau = new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Cursor cursor = (Cursor) parent.getItemAtPosition(position);
				String iID_MaGiai = cursor.getString(cursor.getColumnIndex("iID_MaGiai"));
				String sTenGiai = cursor.getString(cursor.getColumnIndex("sTenGiai"));
				String iID_MaQuocGia = cursor.getString(cursor.getColumnIndex("iID_MaQuocGia"));
				GiaiDau dau = new GiaiDau(iID_MaGiai, sTenGiai);
				dau.setiID_MaQuocGia(iID_MaQuocGia);
				if (pager.getContext() instanceof X4VLayoutActivity) {
					addBangXepHang(dau);
				} else if (pager.getContext() instanceof X2VLayoutActivity) {
					addLiveScore(dau, "theogiai");
				} else if (pager.getContext() instanceof PhongDoCacDoiActivity) {
					addLiveScore(dau, "phongdo");
				} else if (pager.getContext() instanceof NhanDinhChuyenGiaActivity) {
					addLiveScore(dau, "nhandinhchuyengia");
				}
			}
		};
		addFragement(new DanhSachGiaiDauFragment(country, listGiaiDau));
	}

	private void addBangXepHang(GiaiDau dau) {
		addFragement(new BangXepHangFragment(dau, null));
	}

	/**
	 * ------------------------------------------------------------------------
	 * ---------------
	 */

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object instantiateItem(ViewGroup collection, int position) {
		Fragment data = (Fragment) list.get(position);
		View view = data.onCreateView(null, collection, null);
		((ViewPager) collection).addView(view);
		return view;
	}

	@Override
	public void destroyItem(View collection, int position, Object view) {
		((ViewPager) collection).removeView((View) view);
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}

	public boolean onBackPressed() {
		if (pager.getCurrentItem() > 0) {
			pager.setCurrentItem(pager.getCurrentItem() - 1);
			return true;
		}
		return false;
	}

	public void onResume() {
		if (getCount() == 1) {
			if (pager.getContext() instanceof X3VLayoutActivity && liveScoreLikeFragment != null) {
				liveScoreLikeFragment.reloadData();
			}
		}
	}
	
	public void showNhanDinhChuyenGia(GiaiDau dau) {
		addFragement(new NhanDinhChuyenGiaFragment(dau, null));
	}

	LiveScoreLikeFragment liveScoreLikeFragment;
}