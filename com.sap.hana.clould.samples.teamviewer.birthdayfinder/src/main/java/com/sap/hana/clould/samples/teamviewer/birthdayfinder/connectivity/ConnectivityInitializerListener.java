package com.sap.hana.clould.samples.teamviewer.birthdayfinder.connectivity;

import javax.annotation.Resource;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.sap.core.connectivity.api.configuration.ConnectivityConfiguration;

/**
 * Application Lifecycle Listener implementation class AppInitializationListener
 * 
 */
public class ConnectivityInitializerListener implements ServletContextListener {

	@Resource
	private ConnectivityConfiguration connectivityConfiguration;

	/**
	 * @see ServletContextListener#contextInitialized(ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ConnectivityProvider.setConnectivityConfiguration(connectivityConfiguration);
	}

	/**
	 * @see ServletContextListener#contextDestroyed(ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}

}
