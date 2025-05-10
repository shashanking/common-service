package com.bannrx.common_service.controller;

import com.bannrx.common.dtos.device.DeviceDto;
import com.bannrx.common.dtos.device.DeviceRegistration;
import com.bannrx.common_service.apis.AddDeviceApi;
import com.bannrx.common_service.apis.DeleteDeviceApi;
import com.bannrx.common_service.apis.UpdateDeviceApi;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import rklab.utility.annotations.Loggable;
import rklab.utility.dto.ApiOutput;
import rklab.utility.expectations.InvalidInputException;
import rklab.utility.expectations.ServerException;


@Loggable
@RequestMapping("/v1/api/device")
@RestController
@RequiredArgsConstructor
public class DeviceController {

    private final AddDeviceApi addDeviceApi;
    private final UpdateDeviceApi updateDeviceApi;
    private final DeleteDeviceApi deleteDeviceApi;


    @PostMapping("/register")
    public ApiOutput<?> register(@RequestBody DeviceRegistration request) throws ServerException {
        return addDeviceApi.process(request);
    }

    @PutMapping("/update")
    public ApiOutput<?> update(@RequestBody DeviceDto request) throws InvalidInputException, ServerException {
        return updateDeviceApi.process(request);
    }

    @DeleteMapping("/delete")
    public ApiOutput<?> delete(@RequestParam String deviceId) throws InvalidInputException {
        return deleteDeviceApi.process(deviceId);
    }
}
