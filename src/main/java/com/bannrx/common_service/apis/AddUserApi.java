package com.bannrx.common_service.apis;

import com.bannrx.common.dtos.SignUpRequest;
import com.bannrx.common.service.UserService;
import com.bannrx.common.utilities.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import rklab.utility.annotations.Loggable;
import rklab.utility.dto.ApiOutput;
import rklab.utility.expectations.InvalidInputException;



@Service
@Loggable
@RequiredArgsConstructor
public class AddUserApi {

    private final UserService userService;

    private static final String SUCCESS = "User %s signed up successfully";

    public ApiOutput<?> process(SignUpRequest request) throws InvalidInputException {
        validate(request);
        return new ApiOutput<>(HttpStatus.OK.value(),
                String.format(SUCCESS, request.getPhoneNo()),
                userService.createUser(request));
    }

    private void validate(SignUpRequest request) throws InvalidInputException {
        if (StringUtil.isNullOrEmpty(request.getName())){
            throw new InvalidInputException("Name of the user is mandatory.");
        }

        if (StringUtil.isNullOrEmpty(request.getPhoneNo())){
            throw new InvalidInputException("Phone number of the user is mandatory.");
        }
        if (userService.isAlreadyRegister(request)){
            throw new InvalidInputException("User already exists with same phone.");
        }

    }
}
