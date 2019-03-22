package aplicacion;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import jp.co.kyoceramita.log.Logger;
import jsp.urlmap.MapService;
 
/**
 * The activator class controls the plug-in life cycle
 */
public class HyPASActivator implements BundleActivator {

	// The plug-in ID
	public static final String PLUGIN_ID = "Aplicacion";

	// The shared instance
	private static HyPASActivator plugin;
	
	private BundleContext context;
	
	private Logger logger;
	
	/**
	 * The constructor
	 */
	public HyPASActivator()	{
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		plugin = this;
		this.context = context;
		(new MapService()).addUrl(context);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {

		plugin = null;
		(new MapService()).removeUrl(context);

	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static HyPASActivator getDefault() {
		return plugin;
	}
	
	public Logger getLogger(){
//		KSFUtility util = KSFUtility.getInstance();
//		AppContext appContext = util.getApplicationContext(context);
		if(logger == null){
			logger = Logger.getInstance();
		}
		return logger;
	}

}
