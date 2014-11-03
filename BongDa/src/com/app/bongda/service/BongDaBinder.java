package com.app.bongda.service;

import android.os.Binder;

public class BongDaBinder extends Binder {
	private BongDaService bongDaService;

	public BongDaBinder(BongDaService bongDaService) {
		this.bongDaService = bongDaService;
	}

	public BongDaService getBongDaService() {
		return bongDaService;
	}
}