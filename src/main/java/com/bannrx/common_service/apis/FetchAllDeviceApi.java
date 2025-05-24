package com.bannrx.common_service.apis;

import com.bannrx.common.searchCriteria.DeviceSearchCriteria;
import com.bannrx.common.service.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import rklab.utility.annotations.Loggable;
import rklab.utility.dto.ApiOutput;
import rklab.utility.expectations.ServerException;


@Loggable
@Service
@RequiredArgsConstructor
public class FetchAllDeviceApi {
    private final DeviceService deviceService;
    private static final String MESSAGE = "All Device Fetched Successfully.";
    public ApiOutput<?> process(DeviceSearchCriteria deviceSearchCriteria) throws ServerException {
        return new ApiOutput<>(HttpStatus.OK.value(), MESSAGE, deviceService.fetch(deviceSearchCriteria));
    }
}
