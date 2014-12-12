package com.echo.android.common.template.restful.data;

import com.google.gson.Gson;

public class BaseObject {

	public String toJson() {
		return new Gson().toJson(this);
	}
}
