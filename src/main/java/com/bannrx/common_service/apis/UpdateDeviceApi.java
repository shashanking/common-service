package com.bannrx.common_service.apis;

import com.bannrx.common.dtos.device.DeviceDto;
import com.bannrx.common.dtos.device.DimensionUpdateDto;
import com.bannrx.common.service.DeviceService;
import com.bannrx.common.validationGroups.UpdateValidationGroup;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import rklab.utility.annotations.Loggable;
import rklab.utility.dto.ApiOutput;
import rklab.utility.expectations.InvalidInputException;
import rklab.utility.expectations.ServerException;
import rklab.utility.utilities.ValidationUtils;

import java.util.Optional;
import java.util.regex.Pattern;


@Service
@Loggable
@RequiredArgsConstructor
public class UpdateDeviceApi {
    private final DeviceService deviceService;
    private final ValidationUtils validationUtils;
    private static final Pattern VALID_UNIT_PATTERN = Pattern.compile("mm|cm|m|in");
    private static final String Message = "Device updated Successful";

    public ApiOutput<?> process(DeviceDto request) throws InvalidInputException, ServerException {
        validate(request);
        return new ApiOutput<>(HttpStatus.OK.value(), Message, deviceService.update(request));
    }

    private void validate(DeviceDto request) throws InvalidInputException {
        validationUtils.validate(request, UpdateValidationGroup.class);

        if(!deviceService.existById(request.getId())){
            throw new InvalidInputException(String.format("Invalid device id %s.", request.getId()));
        }

        DimensionUpdateDto dimension = Optional.ofNullable(request).map(DeviceDto::getDimension).orElse(null);

        if(dimension != null){
            deviceService.validatePositiveNumber(dimension.getLength(), "Length");
            deviceService.validatePositiveNumber(dimension.getBreadth(),"Breadth");
            String unit = dimension.getUnit();
            if (unit != null && !VALID_UNIT_PATTERN.matcher(unit).matches()) {
                throw new InvalidInputException("Unit must be one of: mm, cm, m, in.");
            }
        }
    }
}
