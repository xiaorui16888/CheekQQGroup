package com.joinqqgroup;

/**
 * Created by 郭小睿同学 on 2018/8/7.
 * 欢迎加入编程技术交流群，群聊号码：574762369
 * 非商业转载，请注明原作者和链接；但如果是商业转载，对不起，请先拿钱来。
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import com.joinqqgroup.MainActivity;
import com.joinqqgroup.utils.ToastUtils;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.sql.Array;
import java.util.ArrayList;
import com.joinqqgroup.utils.ShowDialogUtils;

public class MainActivity extends Activity 
{
	private WebView webView1;
	private StringBuilder sb;
	private List<String> qqGroup =new ArrayList<String>();
	private String qqGroupId ="574762369";
	private int result = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		initView();
    }
	private void initView()
	{
		webView1 = (WebView)findViewById(R.id.webView1);
		webView1.loadUrl("https://ui.ptlogin2.qq.com/cgi-bin/login?pt_hide_ad=1&style=9&pt_ttype=1&appid=549000929&pt_no_auth=1&pt_wxtest=1&daid=5&s_url=https%3A%2F%2Fh5.qzone.qq.com%2Fmqzone%2Findex");
		WebSettings webSettings= webView1.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webView1.setWebViewClient(new WebViewClient(){	
				@Override
				public boolean shouldOverrideUrlLoading(WebView view, String url)
				{
					ToastUtils.showToast(MainActivity.this, "登录成功");
					webView1.setVisibility(View.INVISIBLE);
					webView1.addJavascriptInterface(new InJavaScriptLocalObj(), "java_obj"); 
					webView1.setWebViewClient(new WebViewClient() {
							@Override
							public void onPageFinished(WebView view, String url)
							{
								//网页加载成功后回调
								view.loadUrl("javascript:window.java_obj.getSource(document.documentElement.outerHTML);void(0)");
								super.onPageFinished(view, url);
							}
						});
					webView1.loadUrl("http://qun.qzone.qq.com/group");
					return false;
				}
			});
    }
	//自己定义的类
	public final class InJavaScriptLocalObj
	{
//一定也要加上这个注解,否则没有用
        @JavascriptInterface
        public void getSource(String html)
		{
			sb = new StringBuilder();
			//取出HTML中群号信息
            Document document = Jsoup.parse(html);
			Elements elements =document.select("ul.groups_list").select("li").select("a");
			for (Element element : elements)
			{
				String element2=element.attr("data-groupid").toString();
				qqGroup.add(element2);//存储获取的群号

			}
			for (String a : qqGroup)//遍历
			{	
				if (a.equals(qqGroupId))
				{
					result = 1;//如果跟群号一致，赋值为1
				}
			}
            checkResult();
		}
    }
	private void checkResult()
	{
		if (result == 0)
		{
			ShowDialogUtils.showDialog(MainActivity.this, "未加入本群");
		}
		else
		{
            //这里，可以自定义一些事件，比如，加入群后，就可以使用软件（部分功能）
			ShowDialogUtils.showDialog(MainActivity.this, "已经加入本群");
		}
	}
}
