package mybatis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import mybatis.model.User;
import mybatis.server.UserServer;

@Api(value = "user related controller")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServer server;

    //TODO: post a json string
    @RequestMapping(value = "/add", method= RequestMethod.POST, produces = "application/json")
    @ApiOperation(notes = "add user", value = "add a user")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", paramType = "query", required = true, dataType = "string"),
            @ApiImplicitParam(name = "psw", paramType = "query", required = true, dataType = "string")
    })
//    @ApiImplicitParam(name = "user", value = "user", required = true, dataType = "User")
    @ResponseStatus(HttpStatus.OK)
    public String add(@RequestBody User user) {
        return "hello " + server.add(user);
    }

    @RequestMapping(value ="/all", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation(notes = "find all user", value = "get the size of all users")
    @ResponseStatus(HttpStatus.OK)
    public String find() {
        return "" + server.findAll().size();
    }
}
