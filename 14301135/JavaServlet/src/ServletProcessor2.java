
import java.io.IOException;
import java.net.URLClassLoader;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.sun.xml.internal.ws.util.StringUtils;


public class ServletProcessor2  {

    public void process(Request request, Response response) throws ParserConfigurationException, SAXException, IOException{

        String uri = request.getUri();
        String servletName = null;
        if(uri.indexOf("?")!= -1){
        	servletName=uri.substring(uri.indexOf("/")+1, uri.indexOf("?"));
        }else{
        	servletName = uri.substring(uri.lastIndexOf("/")+1);
        }
        String servletName1=XMLParser.getServlet(servletName);
        Object myClass = null;
        // ������������ڴ�ָ��JAR�ļ���Ŀ¼������
        URLClassLoader loader = null;
        try {
            myClass = Class.forName(servletName1).newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }catch (InstantiationException e) {
			
			e.printStackTrace();
			
		} catch (IllegalAccessException e) {

			e.printStackTrace();
			
		}

        Servlet servlet = null;
        //��request��response��������࣬��ȫ�Կ��ǣ���ֹ�û���servlet��ֱ�ӽ�ServletRequest��ServletResponse����ת��ΪRequest��Response���ͣ�
        //��ֱ�ӵ������ڲ���public��������ΪRequestFacade��ResponseFacade�ﲻ����parse��sendStaticResource�ȷ�����
        RequestFacade requestFacade = new RequestFacade(request);
        ResponseFacade responseFacade = new ResponseFacade(response);
        try {
            servlet = (Servlet) myClass;
            System.out.println(servlet);
            servlet.service((ServletRequest) requestFacade, (ServletResponse) responseFacade);
        } catch (Exception e) {
            System.out.println(e.toString());
        } catch (Throwable e) {
            System.out.println(e.toString());
        }

    }
}