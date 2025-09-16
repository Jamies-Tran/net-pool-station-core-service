package net.pool.station.core.bootstrap.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum EMediaType {
    JPEG("image/jpeg", "IMAGE","JPEG"),
    PNG("image/png", "IMAGE", "PNG"),
    GIF("image/gif", "IMAGE", "GIF"),
    WEBP("image/webp", "IMAGE", "WEBP"),
    BMP("image/bmp", "IMAGE", "BMP"),
    TIFF("image/tiff", "IMAGE", "TIFF"),
    ICO("image/x-icon", "IMAGE", "ICO(ICON)"),
    SVG("image/svg+xml", "IMAGE", "SVG"),
    HEIF("image/heif", "IMAGE", "HEIF (High Efficiency Image Format)"),
    HEIC("image/heic", "IMAGE", "HEIC (HEIF của Apple)"),

    MP4("video/mp4", "VIDEO", "MP4"),
    AVI("video/x-msvideo", "VIDEO", "AVI"),
    MKV("video/x-matroska", "VIDEO", "MKV"),
    WEBM("video/webm", "VIDEO", "WEBM"),
    MOV("video/quicktime", "VIDEO", "MOV"),
    THREEGP("video/3gpp", "VIDEO", "3GP"),
    OGV("video/ogg", "VIDEO", "OGV"),
    FLV("video/x-flv", "VIDEO", "FLV"),

    UNDEFINED("unknown", "UNDEFINED", "Undefined");

    String code;
    String type;
    String name;

}
