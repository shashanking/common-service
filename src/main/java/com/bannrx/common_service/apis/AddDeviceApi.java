package com.bannrx.common_service.apis;

import com.bannrx.common.dtos.device.DeviceDto;
import com.bannrx.common.dtos.device.DeviceRegistration;
import com.bannrx.common.service.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import rklab.utility.annotations.Loggable;
import rklab.utility.dto.ApiOutput;
import rklab.utility.expectations.ServerException;
import rklab.utility.utilities.ValidationUtils;


@Loggable
@Service
@RequiredArgsConstructor
public class AddDeviceApi {

    private final DeviceService deviceService;
    private final ValidationUtils validationUtils;

    private static final String SUCCESS = "Device registration successfully";

    public ApiOutput<?> process(DeviceRegistration request) throws ServerException {
        validate(request);
        return new ApiOutput<>(HttpStatus.OK.value(), SUCCESS, deviceService.register(request));
    }

    private void validate(DeviceRegistration request) {
        validationUtils.validate(request);
    }
}
