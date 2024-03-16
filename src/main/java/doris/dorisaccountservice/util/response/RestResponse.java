package doris.dorisaccountservice.util.response;

import doris.dorisaccountservice.enums.RestStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RestResponse implements UtilRestResponse {
    private Object data = null;
    private Integer status = 1;
    private String message = "";

    public RestResponse setStatus(Integer status)
    {
        this.status = status;
        return this;
    }

    public RestResponse setData(Object data)
    {
        this.data = data;
        return this;
    }

    public RestResponse setMessage(String message)
    {
        this.message = message;
        return this;
    }

    @Override
    public RestResponse internalServerError() {
        this.status = RestStatusEnum.INTERNAL_SERVER_ERROR.getStatus();
        this.message = RestStatusEnum.INTERNAL_SERVER_ERROR.getMessage();
        return this;
    }

    @Override
    public RestResponse success() {
        this.status = RestStatusEnum.SUCCESS.getStatus();
        this.message = RestStatusEnum.SUCCESS.getMessage();
        return this;
    }

    @Override
    public RestResponse permissionDenied() {
        this.status = RestStatusEnum.PERMISSION_DENIED.getStatus();
        this.message = RestStatusEnum.PERMISSION_DENIED.getMessage();
        return this;
    }

    @Override
    public RestResponse unauthenticated() {
        this.status = RestStatusEnum.UNAUTHENTICATED.getStatus();
        this.message = RestStatusEnum.UNAUTHENTICATED.getMessage();
        return this;
    }

    @Override
    public RestResponse throttled() {
        this.status = RestStatusEnum.THROTTLED.getStatus();
        this.message = RestStatusEnum.THROTTLED.getMessage();
        return this;
    }

    @Override
    public RestResponse validationFailed() {
        this.status = RestStatusEnum.VALIDATION_FAILED.getStatus();
        this.message = RestStatusEnum.VALIDATION_FAILED.getMessage();
        return this;
    }

    @Override
    public RestResponse definedError(String message) {
        this.status = RestStatusEnum.DEFINED_ERROR.getStatus();
        this.message = message;
        return this;
    }
}
