package gsc.DigiPal.app;

public class AppConfig {

    public static String IP = "192.168.1.15:1337";
    public static String PROJECT_NAME = "profiler_authentication";
    public static String PROJECT_ROOT = "http://" + IP + "/" + PROJECT_NAME + "/";


	public static String URL_LOGIN = PROJECT_ROOT  + "login.php";
	public static String URL_REGISTER = PROJECT_ROOT + "register.php";
	public static String URL_FETCHIMAGEPATH = PROJECT_ROOT + "fetchImagePath.php";
	public static String URL_REQUESTPERIODOFF = PROJECT_ROOT + "requestPeriodOff.php";

    public static String POST_key = "yoghurt";
}
