package vms.backend.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ImagesResponse {
    private String id;
    private byte[] image;
}
