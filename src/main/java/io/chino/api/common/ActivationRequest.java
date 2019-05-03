package io.chino.api.common;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;

public abstract class ActivationRequest {
    private boolean mustActivate = false;

    /**
     * This method must be invoked during an update() API call.<br>
     * When this class is serialized with an {@link com.fasterxml.jackson.databind.ObjectMapper},
     * this class will contain an additional field<br>
     * <br>
     * {@code "is_active": true}
     */
    public void activateResource() {
        mustActivate = true;
    }

    @JsonGetter("is_active")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Boolean isActive() {
        return mustActivate ? true : null;
    }

    public void resetActivationStatus() {
        mustActivate = false;
    }
}
