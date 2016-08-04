package com.example.pc.vita.Util;

public class CommonUrl {

	private static final String netUrl="http://203.195.182.52:8078/";
	private static final String netImgUrl="http://203.195.182.52:8082/";
	public static final String getYuepaiNavigateurl = netUrl+"xxxxxxxxxx/xxxxxxxx/";
	public static final String url = netUrl+"tongwan-gosu-service/json/";
	public static final String imageUrl =netImgUrl+"gosu-pic/";
	public static final String loginAccount= url + "accountLogin/loginAccountNew";//登录接口，登陆之后才能更换头像、发表主题
	public static final String registerAccountA= url + "accountLogin/loginAccountNew";//注册申请验证码接口
	public static final String registerAccountB= url + "accountLogin/loginAccountNew";//注册验证验证码接口
	public static final String registerAccountC= url + "accountLogin/loginAccountNew";//注册提交信息接口（最终注册接口）
	public static final String uploadUserPhotoNew  = url + "account/uploadUserPhotoNew";//上传头像接口
	public static final String getThemeList    = url + "community/getThemeList";//获取发布的主题接口(第二次握手)A
	public static final String saveThemeInfo    = url + "account/saveThemeInfo";//将主题上传到这个路径（上传图片第一次握手）
	public static final String saveThemeImgNew    = url + "account/saveThemeImgNew";//将图片上传到这个路径（上传图片第三次握手）

}
