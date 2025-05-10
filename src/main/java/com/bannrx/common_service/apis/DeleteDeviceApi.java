package com.bannrx.common_service.apis;

import com.bannrx.common.service.DeviceService;
import com.bannrx.common.utilities.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import rklab.utility.annotations.Loggable;
import rklab.utility.dto.ApiOutput;
import rklab.utility.expectations.InvalidInputException;
import rklab.utility.utilities.ValidationUtils;


@Loggable
@RequiredArgsConstructor
@Service
public class DeleteDeviceApi {

    private final DeviceService deviceService;
    private final static String MESSAGE = "Device deleted successfully";

    public ApiOutput<?> process(String deviceId) throws InvalidInputException {
        validate(deviceId);
        deviceService.delete(deviceId);
        return new ApiOutput<>(HttpStatus.OK.value(), MESSAGE);
    }

    private void validate(String deviceId) throws InvalidInputException {

        if(StringUtil.isNullOrEmpty(deviceId)){
            throw new InvalidInputException("Device id must not be null or empty");
        }

        if(!deviceService.existById(deviceId)){
            throw new InvalidInputException(String.format("Invalid device id %s", deviceId));
        }
    }
}
