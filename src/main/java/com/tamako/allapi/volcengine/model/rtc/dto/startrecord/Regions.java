package com.tamako.allapi.volcengine.model.rtc.dto.startrecord;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author Tamako
 * @since 2024/11/14 16:15
 */
@Data
@Accessors(chain = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Regions {
    private Integer streamIndex;
    private Integer locationX;
    private Integer locationY;
    private Integer width;
    private Integer height;
    private Integer zOrder;
    private Float alpha;
    private Integer integer;
    private SourceCrop sourceCrop;
    private String alternateImage;
    private Float cornerRadius;
    private Integer mediaType;
    private Integer alternateImageFillMode;
}
