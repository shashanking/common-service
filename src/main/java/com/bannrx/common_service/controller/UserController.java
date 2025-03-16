package com.bannrx.common_service.controller;
import com.bannrx.common.dtos.GenerateTokenRequest;
import com.bannrx.common_service.apis.AddUserApi;
import com.bannrx.common_service.apis.DeleteUserApi;
import com.bannrx.common_service.apis.GenerateTokenApi;
import com.bannrx.common_service.apis.UpdateUserApi;
import com.bannrx.common.dtos.RegisterUser;
import com.bannrx.common.dtos.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import rklab.utility.annotations.Loggable;
import rklab.utility.dto.ApiOutput;
import rklab.utility.expectations.InvalidInputException;
import rklab.utility.expectations.ServerException;


@Loggable
@RestController
@RequestMapping("/v1/api/user")
@AllArgsConstructor
public class UserController {

    private final AddUserApi addUserApi;
    private final UpdateUserApi updateUserApi;
    private final DeleteUserApi deleteUserApi;
    private final GenerateTokenApi generateTokenApi;

    @PostMapping("/add")
    public ApiOutput<?> addUser(@RequestBody RegisterUser request) throws InvalidInputException, ServerException {
        return addUserApi.process(request);
    }

    @PutMapping("/update")
    public ApiOutput<?> updateUser(@RequestBody UserDto userDto){
        return updateUserApi.update(userDto);
    }

    @DeleteMapping("/delete/{phoneNo}/{addressId}")
    public ApiOutput<?> deleteUser(@PathVariable String phoneNo, @PathVariable String addressId) throws InvalidInputException, ServerException {
        return deleteUserApi.delete(addressId, phoneNo);
    }

    @GetMapping("/token")
    public ApiOutput<?> generateToken(
            @RequestHeader("username") String username,
            @RequestHeader("password") String password
    ) throws InvalidInputException {
        return generateTokenApi.process(new GenerateTokenRequest(username, password));
    }

}

