package co.com.fomag.lambda.system.application.ports.out.secretmanager;

public interface SecretManagerPort {

	String getParameter(String name);
	
	String getParameter(String name, boolean decrypt);
}
