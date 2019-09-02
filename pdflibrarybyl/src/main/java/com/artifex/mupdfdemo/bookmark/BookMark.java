package com.artifex.mupdfdemo.bookmark;

import android.os.Bundle;
import android.util.DisplayMetrics;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.artifex.mupdfdemo.bookmark.widget.PagerSlidingTabStrip;
import com.lrs.pdflibrarybyl.R;

public class BookMark extends FragmentActivity {
	PagerSlidingTabStrip tabs;
	ViewPager pager;
	DisplayMetrics dm;
	BFrag bfrag;
	CFrag cfrag;
	String[] titles = {"目录", "书签" };
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
	}
	
	private void initView(){
		setContentView(R.layout.pdf_book_mark);
		dm = getResources().getDisplayMetrics();
		pager = findViewById(R.id.pager);
		tabs =  findViewById(R.id.tabs);
		pager.setAdapter(new MyAdapter(getSupportFragmentManager(),titles));
		tabs.setViewPager(pager);
	}
	

	public class MyAdapter extends FragmentPagerAdapter {
		String[] _titles;
		public MyAdapter(FragmentManager fm, String[] titles) {
			super(fm);
			_titles=titles;
		}
		
		@Override
		public CharSequence getPageTitle(int position) {
			return _titles[position];
		}
		
		@Override
		public int getCount() {
			return _titles.length;
		}

		@Override
		public Fragment getItem(int position) {
			switch (position) {
			case 0:
				if (bfrag == null) {
					bfrag = new BFrag();
				}
				return bfrag;
			case 1:
				if (cfrag == null) {
					cfrag = new CFrag();
				}
				return cfrag;
			default:
				return null;
			}
		}
	}
}
