package doris.dorisaccountservice.util.response;

public interface UtilRestResponse {
    public UtilRestResponse definedError(String message);
    public UtilRestResponse definedError();
    public UtilRestResponse success();
    public UtilRestResponse internalServerError();
    public UtilRestResponse permissionDenied();
    public UtilRestResponse unauthenticated();
    public UtilRestResponse throttled();
    public UtilRestResponse validationFailed();
}
