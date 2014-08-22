//package cn.precious.metal.alipay;
//
//import java.io.UnsupportedEncodingException;
//import java.net.URLEncoder;
//
//import android.app.AlertDialog;
//import android.content.DialogInterface;
//import android.content.DialogInterface.OnClickListener;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.Toast;
//
//import com.xiyou.apps.lookpan.R;
//import com.xiyou.apps.lookpan.alipay.AlixId;
//import com.xiyou.apps.lookpan.alipay.BaseHelper;
//import com.xiyou.apps.lookpan.alipay.MobileSecurePayHelper;
//import com.xiyou.apps.lookpan.alipay.MobileSecurePayer;
//import com.xiyou.apps.lookpan.alipay.PartnerConfig;
//import com.xiyou.apps.lookpan.alipay.Rsa;
//import com.xiyou.apps.lookpan.app.MyApplication;
//import com.xiyou.apps.lookpan.base.BaseActivity;
//
//public class StockPayActivity extends BaseActivity {
//
//	private static final String TAG = "StockPayActivity";
//
//	// private ProgressDialog mProgress = null;
//
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		Log.v(TAG, "onCreate");
//		// 检测安全支付服务是否被安装
//		MobileSecurePayHelper mspHelper = new MobileSecurePayHelper(this);
//		boolean detect = mspHelper.detectMobile_sp();
//		if (detect) {
//			listener = new OnClickListener() {
//
//				@Override
//				public void onClick(DialogInterface dialog, int which) {
//					setResult(RESULT_CANCELED);
//					StockPayActivity.this.finish();
//				}
//			};
//
//			// 检测配置信息
//			if (!checkInfo()) {
//				BaseHelper
//						.showDialog(
//								StockPayActivity.this,
//								"提示",
//								"缺少partner或者seller，请在src/com/alipay/android/service/PartnerConfig.java中增加。",
//								0);
//				return;
//			}
//
//			// start pay for this order.
//			// 根据订单信息开始进行支付
//			try {
//				String price = "360.00";
//				String orderInfo = getOrderInfo(price);
//				String info = orderInfo
//						+ "&sign="
//						+ "\""
//						+ URLEncoder
//								.encode(MyApplication.getInstance().orderDetatilInfo
//										.getSign()) + "\"" + "&"
//						+ getSignType();
//				Log.v("orderInfo:", info);
//				// start the pay.
//				// 调用pay方法进行支付
//				MobileSecurePayer msp = new MobileSecurePayer();
//				msp.pay(info, mHandler, AlixId.RQF_PAY, this);
//			} catch (Exception ex) {
//				Toast.makeText(StockPayActivity.this,
//						getResources().getString(R.string.pay_fail),
//						Toast.LENGTH_SHORT).show();
//				setResult(RESULT_CANCELED);
//				finish();
//			}
//		}
//	}
//
//	@Override
//	public void onBackPressed() {
//		finish();
//	}
//
//	public void showAlipayResult() {
//		View view = this.getLayoutInflater().inflate(
//				R.layout.dialog_order_result, null);
//		AlertDialog.Builder alBuilder = new AlertDialog.Builder(this);
//		alBuilder.setView(view);
//		final AlertDialog dialog = alBuilder.create();
//		Button close = (Button) view.findViewById(R.id.closeBtn);
//		close.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				dialog.dismiss();
//				setResult(RESULT_OK);
//				StockPayActivity.this.finish();
//			}
//		});
//		dialog.show();
//
//	}
//
//	private boolean checkInfo() {
//		String partner = PartnerConfig.PARTNER;
//		String seller = PartnerConfig.SELLER;
//		if (partner == null || partner.length() <= 0 || seller == null
//				|| seller.length() <= 0)
//			return false;
//
//		return true;
//	}
//
//	String getOrderInfo(String price) throws UnsupportedEncodingException {
//		// try {
//		AlipayOrderDetailInfo orderInfo = MyApplication.getInstance().orderDetatilInfo;
//		String strOrderInfo = "partner=" + "\"" + orderInfo.getPartner() + "\"";
//		strOrderInfo += "&";
//		strOrderInfo += "seller=" + "\"" + orderInfo.getSeller() + "\"";
//		strOrderInfo += "&";
//		strOrderInfo += "out_trade_no=" + "\"" + orderInfo.getOut_trade_no()
//				+ "\"";
//		strOrderInfo += "&";
//
//		strOrderInfo += "subject=" + "\"" + orderInfo.getProductName() + "\"";
//
//		strOrderInfo += "&";
//		strOrderInfo += "body=" + "\"" + orderInfo.getProductDesc() + "\"";
//		strOrderInfo += "&";
//		strOrderInfo += "total_fee=" + "\"" + price + "\"";
//		strOrderInfo += "&";
//		strOrderInfo += "notify_url=" + "\"" + PartnerConfig.NOTIFYURL + "\"";
//		return strOrderInfo;
//		// } catch (UnsupportedEncodingException e) {
//		// return null;
//		// }
//	}
//
//	/**
//	 * get the sign type we use. 获取签名方式
//	 * 
//	 * @return
//	 */
//	String getSignType() {
//		String getSignType = "sign_type=" + "\"" + "RSA" + "\"";
//		return getSignType;
//	}
//
//	/**
//	 * get the char set we use. 获取字符集
//	 * 
//	 * @return
//	 */
//	String getCharset() {
//		String charset = "charset=" + "\"" + "utf-8" + "\"";
//		return charset;
//	}
//
//	/**
//	 * sign the order info. 对订单信息进行签名
//	 * 
//	 * @param signType
//	 *            签名方式
//	 * @param content
//	 *            待签名订单信息
//	 * @return
//	 */
//	String sign(String signType, String content) {
//		return Rsa.sign(content, PartnerConfig.RSA_PRIVATE);
//	}
//
//	private Handler mHandler = new Handler() {
//		public void handleMessage(Message msg) {
//			try {
//				String strRet = (String) msg.obj;
//				Log.e(TAG, strRet); // strRet范例：resultStatus={9000};memo={};result={partner="2088201564809153"&seller="2088201564809153"&out_trade_no="050917083121576"&subject="123456"&body="2010新款NIKE 耐克902第三代板鞋 耐克男女鞋 386201 白红"&total_fee="0.01"&notify_url="http://notify.java.jpxx.org/index.jsp"&success="true"&sign_type="RSA"&sign="d9pdkfy75G997NiPS1yZoYNCmtRbdOP0usZIMmKCCMVqbSG1P44ohvqMYRztrB6ErgEecIiPj9UldV5nSy9CrBVjV54rBGoT6VSUF/ufjJeCSuL510JwaRpHtRPeURS1LXnSrbwtdkDOktXubQKnIMg2W0PreT1mRXDSaeEECzc="}
//				switch (msg.what) {
//				case AlixId.RQF_PAY: {
//					// 处理交易结果
//					try {
//						// 获取交易状态码，具体状态代码请参看文档
//						String tradeStatus = "resultStatus={";
//						int imemoStart = strRet.indexOf("resultStatus=");
//						imemoStart += tradeStatus.length();
//						int imemoEnd = strRet.indexOf("};memo=");
//						tradeStatus = strRet.substring(imemoStart, imemoEnd);
//						if (tradeStatus.equals("9000")) {
//							showCusToast("支付成功");
//						} else {
//							BaseHelper.showDialog(StockPayActivity.this, "提示",
//									"支付失败。交易状态码:" + tradeStatus,
//									R.drawable.info, listener);
//						}
//
//					} catch (Exception e) {
//						e.printStackTrace();
//						BaseHelper.showDialog(StockPayActivity.this, "提示",
//								strRet, R.drawable.info, listener);
//					}
//				}
//					break;
//				case AlixId.RQF_INSTALL_CHECK:
//					break;
//				}
//				super.handleMessage(msg);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//	};
//
//	private OnClickListener listener;
//
//}
