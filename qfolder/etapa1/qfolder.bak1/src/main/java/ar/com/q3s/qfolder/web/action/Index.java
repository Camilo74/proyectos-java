package ar.com.q3s.qfolder.web.action;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import ar.com.q3s.qfolder.backend.bo.HostBO;
import ar.com.q3s.qfolder.backend.bo.ListFileBO;
import ar.com.q3s.qfolder.backend.bo.OpenFileBO;

@Controller
public class Index {

	@Autowired
	private ListFileBO ListFileBO;
	
	@Autowired
	private HostBO hostBO;

	@Autowired
	private OpenFileBO openFileBO;
	
	@RequestMapping("/")
	public ModelAndView index() throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("hosts",hostBO.getAll());
		return new ModelAndView("index",map);
	}
	
	@ResponseBody
	@RequestMapping(value="/host/all", method=RequestMethod.GET,produces = {MediaType.APPLICATION_JSON_VALUE})
	public String[] getAll() throws Exception{
		return hostBO.getAll();
	}

	@ResponseBody
	@RequestMapping(value="/host/add", method=RequestMethod.POST)
	public String addHost(@RequestParam(value = "host", required = true)String host) throws Exception {
		try {
			hostBO.add(host);
			return host;
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	@ResponseBody
	@RequestMapping(value="/client/open", method=RequestMethod.POST)
	public String open(
		@RequestParam(value = "host", required = true)String host,
		@RequestParam(value = "filename", required = true)String filename) throws Exception {
		return openFileBO.open(host,filename);
	}
	
	//------------------------------------

	public ListFileBO getListFileBO() {
		return ListFileBO;
	}

	public void setListFileBO(ListFileBO listFileBO) {
		ListFileBO = listFileBO;
	}

	public HostBO getHostBO() {
		return hostBO;
	}

	public void setHostBO(HostBO hostBO) {
		this.hostBO = hostBO;
	}
	    


}