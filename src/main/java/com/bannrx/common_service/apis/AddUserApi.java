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
import rklab.utility.expectations.ServerException;


@Service
@Loggable
@RequiredArgsConstructor
public class AddUserApi {

    private final UserService userService;

    private static final String SUCCESS = "User %s signed up successfully";

    public ApiOutput<?> process(SignUpRequest request) throws InvalidInputException, ServerException {
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

        if (StringUtil.isNullOrEmpty(request.getEmail())) {
            throw new InvalidInputException("Email of the user is mandatory.");
        }

        if (StringUtil.isNullOrEmpty(request.getPassword())) {
            throw new InvalidInputException("Password of the user is mandatory.");
        }

        if (request.getRole() == null) {
            throw new InvalidInputException("Role of the user is mandatory.");
        }

        if (request.getBankDetailsDtoList() == null || request.getBankDetailsDtoList().isEmpty()) {
            throw new InvalidInputException("At least one bank detail is required.");
        }

        if (request.getAddressDtoList() == null || request.getAddressDtoList().isEmpty()) {
            throw new InvalidInputException("At least one address is required.");
        }

        if (request.getBusinessDto() == null) {
            throw new InvalidInputException("Business details are mandatory.");
        }

        if (userService.isAlreadyRegister(request)){
            throw new InvalidInputException("User already exists with same phone.");
        }

    }
}
