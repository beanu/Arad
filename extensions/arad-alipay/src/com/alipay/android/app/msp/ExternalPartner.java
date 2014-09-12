//package com.alipay.android.app.msp;
//
//import java.io.IOException;
//import java.io.UnsupportedEncodingException;
//import java.net.URLEncoder;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//
//import org.xmlpull.v1.XmlPullParser;
//import org.xmlpull.v1.XmlPullParserException;
//
//import android.app.Activity;
//import android.content.res.XmlResourceParser;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.BaseAdapter;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ListView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.alipay.android.app.sdk.AliPay;
//import com.alipay.android.app.sdk.R;
//
//public class ExternalPartner extends Activity implements OnItemClickListener,
//		OnClickListener {
//	public static final String TAG = "alipay-sdk";
//
//	private static final int RQF_PAY = 1;
//
//	private static final int RQF_LOGIN = 2;
//
//	private EditText mUserId;
//	private Button mLogon;
//
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.external_partner);
//
//		initProducts();
//		initListView();
//	}
//
//	/*
//	 * (non-Javadoc)
//	 *
//	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
//	 */
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		menu.add(Menu.NONE, Menu.FIRST, 1, "快速登录");
//		return true;
//	}
//
//	/*
//	 * (non-Javadoc)
//	 *
//	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
//	 */
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		switch (item.getItemId()) {
//		case Menu.FIRST:
//			setContentView(R.layout.trustlogin);
//			mUserId = (EditText) findViewById(R.id.user_id);
//			mLogon = (Button) findViewById(R.id.get_token);
//			mLogon.setOnClickListener(this);
//			break;
//		}
//		return false;
//	}
//
//	private void initProducts() {
//		if (sProducts != null)
//			return;
//
//		XmlResourceParser parser = getResources().getXml(R.xml.products);
//		ArrayList<Product> products = new ArrayList<Product>();
//		Product product = null;
//
//		try {
//			int eventType = parser.getEventType();
//
//			while (eventType != XmlPullParser.END_DOCUMENT) {
//				if (eventType == XmlPullParser.START_TAG
//						&& parser.getName().equalsIgnoreCase("product")) {
//					product = new Product();
//					product.subject = parser.getAttributeValue(0);
//					product.body = parser.getAttributeValue(1);
//					product.price = parser.getAttributeValue(2);
//					products.add(product);
//				}
//				eventType = parser.next();
//			}
//
//			sProducts = new Product[products.size()];
//			products.toArray(sProducts);
//
//		} catch (XmlPullParserException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Override
//	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
//			long arg3) {
//		try {
//			Log.i("ExternalPartner", "onItemClick");
//			String info = getNewOrderInfo(position);
//			String sign = Rsa.sign(info, Keys.PRIVATE);
//			sign = URLEncoder.encode(sign);
//			info += "&sign=\"" + sign + "\"&" + getSignType();
//			Log.i("ExternalPartner", "start pay");
//			// start the pay.
//			Log.i(TAG, "info = " + info);
//
//			final String orderInfo = info;
//			new Thread() {
//				public void run() {
//					AliPay alipay = new AliPay(ExternalPartner.this, mHandler);
//
//					//设置为沙箱模式，不设置默认为线上环境
//					//alipay.setSandBox(true);
//
//					String result = alipay.pay(orderInfo);
//
//					Log.i(TAG, "result = " + result);
//					Message msg = new Message();
//					msg.what = RQF_PAY;
//					msg.obj = result;
//					mHandler.sendMessage(msg);
//				}
//			}.start();
//
//		} catch (Exception ex) {
//			ex.printStackTrace();
//			Toast.makeText(ExternalPartner.this, R.string.remote_call_failed,
//					Toast.LENGTH_SHORT).show();
//		}
//	}
//
//	private String getNewOrderInfo(int position) {
//		StringBuilder sb = new StringBuilder();
//		sb.append("partner=\"");
//		sb.append(Keys.DEFAULT_PARTNER);
//		sb.append("\"&out_trade_no=\"");
//		sb.append(getOutTradeNo());
//		sb.append("\"&subject=\"");
//		sb.append(sProducts[position].subject);
//		sb.append("\"&body=\"");
//		sb.append(sProducts[position].body);
//		sb.append("\"&total_fee=\"");
//		sb.append(sProducts[position].price.replace("一口价:", ""));
//		sb.append("\"&notify_url=\"");
//
//		// 网址需要做URL编码
//		sb.append(URLEncoder.encode("http://notify.java.jpxx.org/index.jsp"));
//		sb.append("\"&service=\"mobile.securitypay.pay");
//		sb.append("\"&_input_charset=\"UTF-8");
//		sb.append("\"&return_url=\"");
//		sb.append(URLEncoder.encode("http://m.alipay.com"));
//		sb.append("\"&payment_type=\"1");
//		sb.append("\"&seller_id=\"");
//		sb.append(Keys.DEFAULT_SELLER);
//
//		// 如果show_url值为空，可不传
//		// sb.append("\"&show_url=\"");
//		sb.append("\"&it_b_pay=\"1m");
//		sb.append("\"");
//
//		return new String(sb);
//	}
//
//	private String getOutTradeNo() {
//		SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss");
//		Date date = new Date();
//		String key = format.format(date);
//
//		java.util.Random r = new java.util.Random();
//		key += r.nextInt();
//		key = key.substring(0, 15);
//		Log.d(TAG, "outTradeNo: " + key);
//		return key;
//	}
//
//	private String getSignType() {
//		return "sign_type=\"RSA\"";
//	}
//
//	private void initListView() {
//		ListView lv = (ListView) findViewById(R.id.list_view);
//		lv.setAdapter(new ExternalPartnerAdapter());
//		lv.setOnItemClickListener(this);
//	}
//
//	private void doLogin() {
//		final String orderInfo = getUserInfo();
//		new Thread() {
//			public void run() {
//				String result = new AliPay(ExternalPartner.this, mHandler)
//						.pay(orderInfo);
//
//				Log.i(TAG, "result = " + result);
//				Message msg = new Message();
//				msg.what = RQF_LOGIN;
//				msg.obj = result;
//				mHandler.sendMessage(msg);
//			}
//		}.start();
//	}
//
//	private String getUserInfo() {
//		String userId = mUserId.getText().toString();
//		return trustLogin(Keys.DEFAULT_PARTNER, userId);
//
//	}
//
//	private String trustLogin(String partnerId, String appUserId) {
//		StringBuilder sb = new StringBuilder();
//		sb.append("app_name=\"mc\"&biz_type=\"trust_login\"&partner=\"");
//		sb.append(partnerId);
//		Log.d("TAG", "UserID = " + appUserId);
//		if (!TextUtils.isEmpty(appUserId)) {
//			appUserId = appUserId.replace("\"", "");
//			sb.append("\"&app_id=\"");
//			sb.append(appUserId);
//		}
//		sb.append("\"");
//
//		String info = sb.toString();
//
//		// 请求信息签名
//		String sign = Rsa.sign(info, Keys.PRIVATE);
//		try {
//			sign = URLEncoder.encode(sign, "UTF-8");
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
//		info += "&sign=\"" + sign + "\"&" + getSignType();
//
//		return info;
//	}
//
//	@Override
//	public void onClick(View v) {
//		if (v instanceof Button) {
//			switch (v.getId()) {
//			case R.id.get_token:
//				doLogin();
//				break;
//
//			}
//		}
//
//	}
//
//	private class ExternalPartnerAdapter extends BaseAdapter {
//
//		@Override
//		public int getCount() {
//			return sProducts.length;
//		}
//
//		@Override
//		public Object getItem(int arg0) {
//			return sProducts[arg0];
//		}
//
//		@Override
//		public long getItemId(int position) {
//			return position;
//		}
//
//		@Override
//		public View getView(int position, View convertView, ViewGroup parent) {
//			if (convertView == null) {
//				LayoutInflater factory = LayoutInflater
//						.from(ExternalPartner.this);
//				convertView = factory.inflate(R.layout.product_item, null);
//			}
//
//			Product product = (Product) getItem(position);
//			TextView tv = (TextView) convertView.findViewById(R.id.subject);
//			tv.setText(product.subject);
//
//			tv = (TextView) convertView.findViewById(R.id.body);
//			tv.setText(product.body);
//
//			tv = (TextView) convertView.findViewById(R.id.price);
//			tv.setText(product.price);
//
//			return convertView;
//		}
//
//	}
//
//	Handler mHandler = new Handler() {
//		public void handleMessage(Message msg) {
//			Result result = new Result((String) msg.obj);
//
//			switch (msg.what) {
//			case RQF_PAY:
//			case RQF_LOGIN: {
//				Toast.makeText(ExternalPartner.this, result.getResult(),
//						Toast.LENGTH_SHORT).show();
//
//			}
//				break;
//			default:
//				break;
//			}
//		};
//	};
//
//	public static class Product {
//		public String subject;
//		public String body;
//		public String price;
//	}
//
//	public static Product[] sProducts;
//}