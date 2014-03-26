package avdw;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;


/**
 * Created by IntelliJ IDEA.
 * User: Andrew
 * Date: 2012/01/30
 * Time: 12:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class StartJetty {
    public static void main(String[] args) throws Exception {
        Server server = new Server(8081);

        WebAppContext context = new WebAppContext();
        context.setDescriptor("web/WEB-INF/web.xml");
        context.setResourceBase("web");
        context.setContextPath("/");
        context.setParentLoaderPriority(true);

        server.setHandler(context);

        server.start();
        server.join();
        
    }    
}
