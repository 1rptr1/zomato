package saurav_practice_zomato;

import org.openqa.selenium.By;

public class zomato_elements {
	public static By skip = By.id("com.application.zomato:id/skip_button");
	public static By gps_permission_while_using = By.id("com.android.permissioncontroller:id/permission_allow_foreground_only_button");
	public static By gps_permission_allow_this_time = By.id("com.android.permissioncontroller:id/permission_allow_one_time_button");
	public static By gps_permission_deny = By.id("com.android.permissioncontroller:id/permission_deny_button");
	public static By send_otp_home = By.id("com.application.zomato:id/send_otp_button");
	public static By terms_policy_home = By.id("com.application.zomato:id/text_main_terms_container");
	public static By mobile_number = By.xpath("//android.widget.RelativeLayout[2]/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.EditText");
	public static By login_email = By.id("/android.widget.FrameLayout[1]/android.widget.FrameLayout");
	public static By login_facebook = By.id("/android.widget.FrameLayout[2]/android.widget.FrameLayout");
	public static By login_google = By.id("/android.widget.FrameLayout[3]/android.widget.FrameLayout");
	public static By title = By.id("com.application.zomato:id/titleTv");
	
}
