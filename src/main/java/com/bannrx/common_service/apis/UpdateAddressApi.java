package com.bannrx.common_service.apis;

import com.bannrx.common.dtos.AddressDto;
import com.bannrx.common.service.AddressService;
import com.bannrx.common.service.UserService;
import com.bannrx.common.validationGroups.UpdateValidationGroup;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import rklab.utility.annotations.Loggable;
import rklab.utility.dto.ApiOutput;
import rklab.utility.expectations.InvalidInputException;
import rklab.utility.expectations.ServerException;
import rklab.utility.utilities.ValidationUtils;



@Loggable
@Service
@RequiredArgsConstructor
public class UpdateAddressApi {

    private static final String UPDATE_MSG = "The address has been updated successfully";
    private final AddressService addressService;
    private final ValidationUtils validationUtils;
    private final UserService userService;

    public ApiOutput<AddressDto> update(AddressDto addressDto) throws InvalidInputException, ServerException {
        validate(addressDto);
        return new ApiOutput<>(HttpStatus.OK.value(), UPDATE_MSG, addressService.update(addressDto) );
    }

    private void validate(AddressDto addressDto)throws InvalidInputException {
        validationUtils.validate(addressDto, UpdateValidationGroup.class);
        var loggedInUserId = userService.fetchLoggedInUser().getId();
        addressService.validate(addressDto, loggedInUserId);
    }

}
