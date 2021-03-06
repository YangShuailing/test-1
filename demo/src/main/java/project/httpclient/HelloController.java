package project.httpclient;

import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import project.httpclient.dto.UserDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: Johnny
 * Date: 2016/10/29
 * Time: 0:27
 */
@Controller
public class HelloController {
    private static Logger logger = LoggerFactory.getLogger(HelloController.class);

    @Autowired
    private HelloService helloService;

    @RequestMapping("/greeting")
    public ModelAndView greeting(@RequestParam(value = "name", defaultValue = "World") String name) {

        Map<String, Object> map = new HashMap<>();
        try {
            //由于浏览器会把中文直接换成ISO-8859-1编码格式,如果用户在地址打入中文,需要进行如下转换处理
            String tempName = new String(name.getBytes("ISO-8859-1"), "utf-8");

            logger.trace("tempName:" + tempName);
            logger.info(tempName);

            String userName = helloService.processService(tempName);

            map.put("userName", userName);

            logger.trace("运行结果:" + map);
        } catch (UnsupportedEncodingException e) {
            logger.error("HelloController greeting方法发生UnsupportedEncodingException异常:" + e);
        } catch (Exception e) {
            logger.error("HelloController greeting方法发生Exception异常:" + e);
        }
        return new ModelAndView("/hello", map);
    }

    @RequestMapping(value = "/processing", method = RequestMethod.POST)
    public ModelAndView processing(HttpServletRequest request, HttpServletResponse response) {

        Enumeration en = request.getParameterNames();
        while (en.hasMoreElements()) {
            String paramName = (String) en.nextElement();
            String paramValue = request.getParameter(paramName);
        }

        String name = request.getParameter("name");
        String age = request.getParameter("age");

        UserDTO userDTO = new UserDTO();
        userDTO.setName(name);
        userDTO.setAge(Integer.valueOf(age));

        logger.info("process param is :{}" + userDTO);

        Map<String, Object> map = new HashMap<>();
        try {
            userDTO = helloService.processService(userDTO);
            //返回请求结果
            map.put("name", userDTO.getName());
            map.put("age", userDTO.getAge());
        } catch (Exception e) {
            logger.info("请求处理异常:" + e);
        }
        return new ModelAndView("/user", map);
    }

    /**
     * @responseBody表示该方法的返回结果直接写入HTTP response body中
     * 一般在异步获取数据时使用，在使用@RequestMapping后，返回值通常解析为跳转路径
     * 加上@responseBody后返回结果不会被解析为跳转路径，而是直接写入HTTP response body中。
     * 比如异步获取json数据，加上@responsebody后，会直接返回json数据。
     */
    @ResponseBody
    @RequestMapping(value = "/greet", method = RequestMethod.GET)
    public Map<String, Object> greet(HttpServletRequest request, HttpServletResponse response,
                                     @RequestParam(value = "name", defaultValue = "World") String name) {

        Map<String, Object> map = null;
        try {
            //由于浏览器会把中文直接换成ISO-8859-1编码格式,如果用户在地址打入中文,需要进行如下转换处理
            String tempName = new String(name.getBytes("ISO-8859-1"), "utf-8");

            logger.trace("tempName:" + tempName);
            logger.info(tempName);

            String userName = helloService.processService(tempName);

            map = new HashMap<>();
            map.put("userName", userName);

            logger.trace("运行结果:" + map);
        } catch (UnsupportedEncodingException e) {
            logger.error("HelloController greet方法发生UnsupportedEncodingException异常:" + e);
        } catch (Exception e) {
            logger.error("HelloController greet方法发生Exception异常:" + e);
        }
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/process", method = RequestMethod.POST)
    public String process(HttpServletRequest request, @RequestBody String requestBody) {

        logger.info("process param is :{}" + requestBody);
        JSONObject result = new JSONObject();
        try {
            JSONObject jsonObject = JSONObject.fromObject(requestBody);
            UserDTO userDTO = (UserDTO) JSONObject.toBean(jsonObject, UserDTO.class);

            userDTO = helloService.processService(userDTO);

            //返回请求结果
            result.put("status", "SUCCESS");
            result.put("userDTO", userDTO);
        } catch (Exception e) {
            logger.info("请求处理异常！ params is:{}", requestBody);
            result.put("status", "FAIL");
        }
        return result.toString();
    }

}
