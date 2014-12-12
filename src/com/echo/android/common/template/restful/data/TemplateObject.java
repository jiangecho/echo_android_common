package com.echo.android.common.template.restful.data;

import java.util.ArrayList;

/**
 * This is the template for your own data object
 * Please change to satisfy your requirement
 * @author jiangecho
 *
 */
public class TemplateObject extends BaseObject {

	// TODO define your own fields here
	// the following is just for demonstration
	private String title;
	private String uri;
	
	// getters here

	// end etc.

	// TODO will be used with GsonRequest
	// the fields is based on your on-line json
	public static class TemplateTypeRequestData {
		// TODO define fields here
		private int page;
		private ArrayList<TemplateObject> templateObjects;

		// TODO define getters here
	}
}
