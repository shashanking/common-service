package com.bannrx.common_service.apis;

import com.bannrx.common.dtos.requests.SignUpRequest;
import com.bannrx.common.service.BankDetailsService;
import com.bannrx.common.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import rklab.utility.annotations.Loggable;
import rklab.utility.dto.ApiOutput;
import rklab.utility.expectations.InvalidInputException;
import rklab.utility.expectations.ServerException;
import rklab.utility.utilities.ValidationUtils;


@Service
@Loggable
@RequiredArgsConstructor
public class AddUserApi {

    private final UserService userService;
    private final ValidationUtils validationUtils;
    private final BankDetailsService bankDetailsService;

    private static final String SUCCESS = "User %s signed up successfully";

    public ApiOutput<?> process(SignUpRequest request) throws InvalidInputException, ServerException{
        validate(request);
        return new ApiOutput<>(HttpStatus.OK.value(),
                String.format(SUCCESS, request.getPhoneNo()),
                userService.signUp(request));
    }


    private void validate(SignUpRequest request) throws InvalidInputException {
        validationUtils.validate(request);
        if (userService.isAlreadyRegister(request)){
            throw new InvalidInputException("User already exists.");
        }

        if(CollectionUtils.isNotEmpty(request.getBankDetailsDtoSet()) && request.getBankDetailsDtoSet().size() > 1){
            throw new InvalidInputException("Only one Bank details will be accepted");
        }

        if(request.getAddressDtoSet() != null && request.getAddressDtoSet().size() > 1){
            throw new InvalidInputException("Only one Address details will be accepted");
        }

        if (CollectionUtils.isNotEmpty(request.getBankDetailsDtoSet())) {
            bankDetailsService.validateAdd(request.getBankDetailsDtoSet().iterator().next());
        }

    }

}
